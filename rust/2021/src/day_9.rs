use std::{collections::{HashMap, HashSet}, iter::FromIterator};

use itertools::Itertools;

pub fn solve(input: &[String]) {
    let height_map = parse_input(input);
    println!("Part 1: {}", part_1(&height_map));
    println!("Part 2: {}", part_2(&height_map));
}

fn part_1(height_map: &HeightMap) -> i32 {
    let map = height_map.map.clone();
    return map
        .into_iter()
        .filter(|(position, value)| is_low_point(height_map, position, value))
        .map(|(_position, value)| value + 1)
        .sum();
}

fn part_2(height_map: &HeightMap) -> i32 {
    let map = height_map.map.clone();
    return map
        .into_iter()
        .filter(|(position, value)| is_low_point(height_map, position, value))
        .map(|(position, value)| find_basin_size(height_map, position, value))
        .sorted()
        .rev()
        .take(3)
        .product();
}

fn find_basin_size(height_map: &HeightMap, position: (i32, i32), value: i32) -> i32 {
    let mut basin: HashSet<(i32, i32)> = HashSet::from_iter(vec![position]);
    let mut queue = vec![(position, value)];
    let mut visited = HashMap::new();
    while let Some((position, value)) = queue.pop() {
        for neighbor in neighbors(&position) {
            if visited.get(&neighbor).is_none() {
                visited.insert(position, true);
                match height_map.map.get(&neighbor) {
                    Some(height) => {
                        if *height > value && *height < 9 {
                            basin.insert(neighbor);
                            queue.push((neighbor, *height));
                        }
                    },
                    _ => {}
                }
            }
        }
    }
    return basin.len() as i32;
}


fn is_low_point(map: &HeightMap, position: &(i32, i32), current_value: &i32) -> bool {
    return neighbors(position)
    .into_iter()
    .filter_map(|position| map.map.get(&position))
    .all(|value| current_value < value);
}

fn neighbors(position: &(i32, i32)) -> Vec<(i32, i32)> {
    return vec![
        (position.0 - 1, position.1),
        (position.0 + 1, position.1),
        (position.0, position.1 - 1),
        (position.0, position.1 + 1),
    ];
}

fn parse_input(lines: &[String]) -> HeightMap {
    return HeightMap {
        map: lines
            .into_iter()
            .enumerate()
            .flat_map(|(row_index, line)| {
                line.split("")
                    .filter(|line| !line.is_empty())
                    .enumerate()
                    .map(move |(col_index, n)| {
                        (
                            (row_index as i32, col_index as i32),
                            n.parse::<i32>().unwrap(),
                        )
                    })
            })
            .collect(),
    };
}

#[derive(Debug)]
struct HeightMap {
    map: HashMap<(i32, i32), i32>,
}
