use std::{
    collections::HashSet,
    fmt::Debug,
    iter::{FromIterator},
};

pub fn solve(input: &String) {
    println!("Part 1: {}", part_1(&input));
    println!("Part 2: {}", part_2(&input));
}

fn part_1(input: &String) -> i32 {
    let (positions, folds) = parse_input(input);
    let new_positions = process_fold(&positions, &folds[0]);
    return new_positions.len() as i32;
}

fn part_2(input: &String) -> i32 {
    let (positions, folds) = parse_input(input);
    let new_positions = folds.iter().fold(positions, |current_positions, fold| {
        process_fold(&current_positions, fold)
    });
    print_positions(&new_positions);
    return new_positions.len() as i32;
}

fn process_fold(positions: &HashSet<Position>, fold: &Fold) -> HashSet<Position> {
    match fold.direction {
        Direction::H => process_horizontal_fold(positions, fold.index),
        Direction::V => process_vertical_fold(positions, fold.index),
    }
}

fn process_horizontal_fold(positions: &HashSet<Position>, index: usize) -> HashSet<Position> {
    let mut new_positions: HashSet<Position> = HashSet::new();
    let x_max = positions.iter().max_by_key(|p| p.x).unwrap().x;
    for j in 0..index {
        for i in 0..x_max + 1 {
            let position = Position { x: i, y: j as i32 };
            if positions.contains(&position) {
                new_positions.insert(position);
            } else {
                let mirrored_position = Position {
                    x: i,
                    y: (2 * index - j) as i32,
                };
                if positions.contains(&mirrored_position) {
                    new_positions.insert(position);
                }
            }
        }
    }

    return new_positions;
}

fn process_vertical_fold(positions: &HashSet<Position>, index: usize) -> HashSet<Position> {
    let mut new_positions: HashSet<Position> = HashSet::new();
    let y_max = positions.iter().max_by_key(|p| p.y).unwrap().y;
    for j in 0..y_max + 1 {
        for i in 0..index {
            let position = Position { x: i as i32, y: j as i32};
            if positions.contains(&position) {
                new_positions.insert(position);
            } else {
                let mirrored_position = Position {
                    x: (2 * index - i) as i32,
                    y: j,
                };
                if positions.contains(&mirrored_position) {
                    new_positions.insert(position);
                }
            }
        }
    }

    return new_positions;
}

fn parse_input(input: &String) -> (HashSet<Position>, Vec<Fold>) {
    let inputs: Vec<&str> = input.split("\n\n").collect();
    return (parse_positions(inputs[0]), parse_folds(inputs[1]));
}

fn parse_positions(inputs: &str) -> HashSet<Position> {
    return HashSet::from_iter(inputs.lines().into_iter().map(|line| {
        let tokens: Vec<&str> = line.split(",").collect();
        Position {
            x: tokens[0].parse::<i32>().unwrap(),
            y: tokens[1].parse::<i32>().unwrap(),
        }
    }));
}

fn parse_folds(inputs: &str) -> Vec<Fold> {
    return inputs
        .lines()
        .into_iter()
        .map(|line| {
            let x_index = line.chars().position(|c| c == 'x').unwrap_or(usize::MAX);
            let y_index = line.chars().position(|c| c == 'y').unwrap_or(usize::MAX);
            if x_index != usize::MAX {
                Fold {
                    direction: Direction::V,
                    index: line[x_index + 2..].parse::<usize>().unwrap(),
                }
            } else {
                // I believe in the input correctness :)
                Fold {
                    direction: Direction::H,
                    index: line[y_index + 2..].parse::<usize>().unwrap(),
                }
            }
        })
        .collect();
}

fn print_positions(positions: &HashSet<Position>) {
    let x_max = positions.iter().max_by_key(|p| p.x).unwrap().x;
    let y_max = positions.iter().max_by_key(|p| p.y).unwrap().y;
    for j in 0..y_max + 1 {
        for i in 0..x_max + 1 {
            if positions.contains(&Position { x: i, y: j }) {
                print!("#");
            } else {
                print!(".");
            }
        }
        println!();
    }
}

#[derive(Debug, PartialEq, Eq, Hash)]
struct Position {
    x: i32,
    y: i32,
}

#[derive(Debug, Clone, Copy)]
struct Fold {
    direction: Direction,
    index: usize,
}

#[derive(Debug, Clone, Copy)]
enum Direction {
    H,
    V,
}
