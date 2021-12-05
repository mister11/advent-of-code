use itertools::{GroupBy, Itertools};
use std::{io::BufRead, ops::Range, os::unix::prelude::MetadataExt};

pub fn solve(lines: &Vec<String>) {
    println!("Part 1: {}", part_1(lines));
    println!("Part 2: {}", part_2(lines));
}

fn part_1(lines: &Vec<String>) -> i32 {
    return parse_lines(lines)
        .into_iter()
        .filter(|line| line.start.x == line.end.x || line.start.y == line.end.y)
        .flat_map(|line| line.points())
        // group_by does not do what it does in other languages so data needs to be sorted
        .sorted()
        .group_by(|&x| x)
        .into_iter()
        .map(|(k, r)| (k, r.collect::<Vec<_>>()))
        .filter(|(_key, group)| group.len() >= 2)
        .count() as i32;
}

fn part_2(lines: &Vec<String>) -> i32 {
    return parse_lines(lines)
        .into_iter()
        .flat_map(|line| line.points())
        // group_by does not do what it does in other languages so data needs to be sorted
        .sorted()
        .group_by(|&x| x)
        .into_iter()
        .map(|(k, r)| (k, r.collect::<Vec<_>>()))
        .filter(|(_key, group)| group.len() >= 2)
        .count() as i32;
}

fn parse_lines(lines: &Vec<String>) -> Vec<Line> {
    lines
        .into_iter()
        .map(|line| line.split("->").collect::<Vec<&str>>())
        .map(|line_tokens| {
            let point1_tokens = line_tokens[0].trim().split(",").collect::<Vec<&str>>();
            let point2_tokens = line_tokens[1].trim().split(",").collect::<Vec<&str>>();

            Line {
                start: Point {
                    x: point1_tokens[0].parse::<i32>().unwrap(),
                    y: point1_tokens[1].parse::<i32>().unwrap(),
                },
                end: Point {
                    x: point2_tokens[0].parse::<i32>().unwrap(),
                    y: point2_tokens[1].parse::<i32>().unwrap(),
                },
            }
        })
        .collect()
}

#[derive(Debug, Clone, Copy)]
struct Line {
    start: Point,
    end: Point,
}

impl Line {
    fn points(&self) -> Vec<(i32, i32)> {
        if self.start.x == self.end.x {
            return self
                .y_range()
                .into_iter()
                .map(|y| (self.start.x, y))
                .collect::<Vec<(i32, i32)>>();
        } else if self.start.y == self.end.y {
            return self
                .x_range()
                .into_iter()
                .map(|x| (x, self.start.y))
                .collect::<Vec<(i32, i32)>>();
        } else {
            return self
                .x_range()
                .into_iter()
                .zip(self.y_range())
                .into_iter()
                .map(|(x, y)| (x, y))
                .collect::<Vec<(i32, i32)>>();
        }
    }

    fn x_range(&self) -> Vec<i32> {
        let x1 = self.start.x;
        let x2 = self.end.x;

        if x1 > x2 {
            (x2..x1 + 1).rev().collect()
        } else {
            (x1..x2 + 1).collect()
        }
    }

    fn y_range(&self) -> Vec<i32> {
        let y1 = self.start.y;
        let y2 = self.end.y;

        if y1 > y2 {
            (y2..y1 + 1).rev().collect()
        } else {
            (y1..y2 + 1).collect()
        }
    }
}

#[derive(Debug, Clone, Copy)]
struct Point {
    x: i32,
    y: i32,
}
