use std::collections::HashMap;

use itertools::Itertools;

pub fn solve(lines: &[String]) {
    println!("Part 1: {}", part_1(&lines));
    println!("Part 2: {}", part_2(&lines));
}

fn part_1(lines: &[String]) -> i32 {
    let points_mapping: HashMap<char, i32> =
        HashMap::from([(')', 3), (']', 57), ('}', 1197), ('>', 25137)]);
    return lines
        .into_iter()
        .filter_map(|line| evaluate_corrupted(line))
        .map(|corrupted_mark| points_mapping.get(&corrupted_mark).unwrap())
        .sum();
}

fn part_2(lines: &[String]) -> i64 {
    let points_mapping = HashMap::from([(')', 1), (']', 2), ('}', 3), ('>', 4)]);
    let sorted_scores: Vec<i64> = lines
        .into_iter()
        .filter_map(|line| evaluate_non_corrupted(line))
        .map(|completion_chars| {
            completion_chars
                .into_iter()
                .fold(0 as i64, |acc, c| 5 * acc + points_mapping.get(&c).unwrap())
        })
        .sorted()
        .collect();

    return sorted_scores[sorted_scores.len() / 2];
}

fn evaluate_corrupted(line: &str) -> Option<char> {
    let mut opening_brackets: Vec<char> = Vec::new();
    for c in line.chars() {
        if c == '[' || c == '{' || c == '(' || c == '<' {
            opening_brackets.push(c);
        } else {
            let last_opening_bracket = opening_brackets.last().copied().unwrap();
            if last_opening_bracket == '(' && c != ')' {
                return Some(c);
            } else if last_opening_bracket == '[' && c != ']' {
                return Some(c);
            } else if last_opening_bracket == '{' && c != '}' {
                return Some(c);
            } else if last_opening_bracket == '<' && c != '>' {
                return Some(c);
            } else {
                opening_brackets.pop();
            }
        }
    }
    return None;
}

fn evaluate_non_corrupted(line: &str) -> Option<Vec<char>> {
    let mut opening_brackets: Vec<char> = Vec::new();
    for c in line.chars() {
        if c == '[' || c == '{' || c == '(' || c == '<' {
            opening_brackets.push(c);
        } else {
            let last_opening_bracket = opening_brackets.last().copied().unwrap();
            if last_opening_bracket == '(' && c != ')' {
                return None;
            } else if last_opening_bracket == '[' && c != ']' {
                return None;
            } else if last_opening_bracket == '{' && c != '}' {
                return None;
            } else if last_opening_bracket == '<' && c != '>' {
                return None;
            } else {
                opening_brackets.pop();
            }
        }
    }
    return Some(
        opening_brackets
        .into_iter()
        .rev()
        .map(|c| match c {
            '[' => ']',
            '{' => '}',
            '(' => ')',
            '<' => '>',
            _ => panic!("Invalid opening bracket"),
        })
        .collect()
    );
}
