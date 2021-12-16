use std::collections::{BinaryHeap, HashMap};

pub fn solve(input: &[String]) {
    println!("Part 1: {}", part_1(&input));
    println!("Part 2: {}", part_2(&input));
}

fn part_1(input: &[String]) -> u32 {
    let graph = parse_input(input);
    let (max_x, max_y) = graph.keys().max().unwrap();
    return shortest_path(&graph, &(0, 0), &(*max_x, *max_y));
}

fn part_2(input: &[String]) -> u32 {
    let graph = parse_input(input);
    let (max_x, max_y) = graph.keys().max().unwrap();

    let expanded_graph = expand_graph(&graph, max_x, max_y);
    let (max_x, max_y) = expanded_graph.keys().max().unwrap();
    return shortest_path(&expanded_graph, &(0, 0), &(*max_x, *max_y));
}

fn shortest_path(graph: &HashMap<(u32, u32), u32>, start: &(u32, u32), end: &(u32, u32)) -> u32 {
    let mut distances: HashMap<(u32, u32), u32> = graph
        .keys()
        .into_iter()
        .map(|position| (*position, u32::MAX))
        .collect();

    distances.insert(*start, 0);

    let mut queue = BinaryHeap::new();
    queue.push(Node {
        position: *start,
        cost: 0,
    });

    while let Some(Node { position, cost }) = queue.pop() {
        if position == *end {
            return cost;
        }

        if cost > *distances.get(&position).unwrap() {
            continue;
        }

        for neighbor in neighbors(&position) {
            let next_cost = match graph.get(&neighbor) {
                Some(cost) => cost,
                None => continue,
            };

            let next = Node {
                position: neighbor,
                cost: cost + next_cost,
            };

            if next.cost < *distances.get(&next.position).unwrap() {
                queue.push(next);
                distances.insert(next.position, next.cost);
            }
        }
    }

    return u32::MAX;
}

fn neighbors(position: &(u32, u32)) -> Vec<(u32, u32)> {
    let (x, y) = *position;

    let mut positions = vec![];
    if x >= 1 {
        positions.push((x - 1, y));
    }
    if y >= 1 {
        positions.push((x, y - 1));
    }

    positions.push((x + 1, y));
    positions.push((x, y + 1));
    return positions;
}

fn expand_graph(
    graph: &HashMap<(u32, u32), u32>,
    max_x: &u32,
    max_y: &u32,
) -> HashMap<(u32, u32), u32> {
    let mut expanded_graph: HashMap<(u32, u32), u32> = HashMap::new();
    for i in 0..5 {
        for j in 0..5 {
            graph.iter().for_each(|((x, y), value)| {
                let new_position = (x + i * (max_x + 1), y + j * (max_y + 1));

                let new_value = if value + i + j > 9 {
                    value + i + j - 9
                } else {
                    value + i + j
                };
                expanded_graph.insert(new_position, new_value);
            });
        }
    }
    return expanded_graph;
}

fn parse_input(lines: &[String]) -> HashMap<(u32, u32), u32> {
    return lines
        .iter()
        .enumerate()
        .flat_map(|(row, line)| {
            line.chars().enumerate().map(move |(col, c)| {
                let position = (row as u32, col as u32);
                (position, c.to_digit(10).unwrap())
            })
        })
        .collect();
}

#[derive(Clone, Copy, PartialEq, Eq)]
struct Node {
    position: (u32, u32),
    cost: u32,
}

impl Ord for Node {
    fn cmp(&self, other: &Self) -> std::cmp::Ordering {
        return other
            .cost
            .cmp(&self.cost)
            .then_with(|| self.position.cmp(&other.position));
    }
}

impl PartialOrd for Node {
    fn partial_cmp(&self, other: &Self) -> Option<std::cmp::Ordering> {
        return Some(self.cmp(other));
    }
}
