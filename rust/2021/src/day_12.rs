// This one need clean-up...
use std::{
    collections::{HashMap, HashSet},
    vec, iter::FromIterator,
};

pub fn solve(input: &[String]) {
    let graph = parse_input(input);
    println!("Part 1: {}", part_1(&graph));
    println!("Part 2: {}", part_2(&graph));
}

fn part_1(graph: &Graph) -> i32 {
    let start = Vertex {
        name: "start".to_string(),
        is_big: false,
    };
    let visited: HashSet<Vertex> = HashSet::from_iter(vec![start.clone()]);
    return find_all_paths(graph, &start, &visited, 0);
}

fn part_2(graph: &Graph) -> i32 {
    let start = Vertex {
        name: "start".to_string(),
        is_big: false,
    };
    let visited: HashSet<Vertex> = HashSet::from_iter(vec![start.clone()]);
    return find_all_paths2(graph, &start, &visited, true, 0);
}

fn find_all_paths(
    graph: &Graph,
    current: &Vertex,
    visited: &HashSet<Vertex>,
    mut num_of_paths: i32,
) -> i32 {
    if current.name == "end" {
        return 1;
    }
    graph.neighbors(&current).iter().for_each(|vertex| {
        if vertex.is_big {
            num_of_paths += find_all_paths(graph, vertex, visited, 0);
        } else if !visited.contains(vertex) {
            let mut k: HashSet<Vertex> = HashSet::new();
            visited.iter().for_each(|v| {
                k.insert(v.clone());
            });
            k.insert(vertex.clone());
            num_of_paths += find_all_paths(graph, vertex, &k, 0);
        }
    });
    return num_of_paths;
}

fn find_all_paths2(
    graph: &Graph,
    current: &Vertex,
    visited: &HashSet<Vertex>,
    is_duplicate_allowed: bool,
    mut num_of_paths: i32,
) -> i32 {
    if current.name == "end" {
        return 1;
    }

    graph.neighbors(&current).iter().for_each(|vertex| {
        if vertex.is_big {
            num_of_paths += find_all_paths2(graph, vertex, visited, is_duplicate_allowed, 0);
        } else if !visited.contains(vertex) {
            let mut k: HashSet<Vertex> = HashSet::new();
            visited.iter().for_each(|v| {
                k.insert(v.clone());
            });
            k.insert(vertex.clone());
            num_of_paths += find_all_paths2(graph, vertex, &k, is_duplicate_allowed, 0);
        } else if visited.contains(vertex)
            && is_duplicate_allowed
            && vertex.name != "start"
            && vertex.name != "end"
        {
            num_of_paths += find_all_paths2(graph, vertex, visited, false, 0);
        }
    });
    return num_of_paths;
}

fn parse_input(lines: &[String]) -> Graph {
    let mut g = Graph {
        ..Default::default()
    };
    lines.iter().for_each(|line| {
        let tokens: Vec<&str> = line.split("-").collect();
        g.add_node(tokens[0], tokens[1]);
        g.add_node(tokens[1], tokens[0]);
    });
    return g;
}

#[derive(Debug)]
struct Graph {
    graph: HashMap<Vertex, Vec<Vertex>>,
}

#[derive(Debug, Clone, PartialEq, Eq, Hash, PartialOrd, Ord)]
struct Vertex {
    name: String,
    is_big: bool,
}

impl Graph {
    fn add_node(&mut self, start: &str, end: &str) {
        self.graph
            .entry(to_vertex(start))
            .or_insert(vec![])
            .push(to_vertex(end));
    }

    fn neighbors(&self, node: &Vertex) -> &Vec<Vertex> {
        return &self.graph[&node];
    }
}

fn to_vertex(name: &str) -> Vertex {
    return Vertex {
        name: name.to_string(),
        is_big: name.to_uppercase().eq(&name),
    };
}

impl Default for Graph {
    fn default() -> Self {
        Self {
            graph: Default::default(),
        }
    }
}
