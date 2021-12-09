use std::{
    collections::{HashMap, HashSet},
    iter::FromIterator,
};

use itertools::Itertools;

pub fn solve(lines: &[String]) {
    let entries = parse_entries(lines);
    println!("Part 1: {}", part_1(&entries));
    println!("Part 2: {}", part_2(&entries));
}

fn part_1(entries: &[Entry]) -> usize {
    let unique_lengths = HashSet::<usize>::from_iter(vec![2, 3, 4, 7]);

    return entries
        .into_iter()
        .flat_map(|entry| entry.output_values.clone())
        .map(|value| value.len())
        .filter(|len| unique_lengths.contains(len))
        .count();
}

fn part_2(entries: &[Entry]) -> i32 {
    return entries.into_iter().map(|entry| decode_entry(entry)).sum();
}

fn decode_entry(entry: &Entry) -> i32 {
    let unique_lengths = HashSet::<usize>::from_iter(vec![2, 3, 4, 7]);
    let signal_patterns = entry.signal_patterns.clone();

    let mut numbers_map: HashMap<String, char> = HashMap::new();
    let one_chars: HashSet<char> = signal_patterns
        .iter()
        .find(|pattern| pattern.len() == 2)
        .unwrap()
        .chars()
        .collect();

    numbers_map.insert(one_chars.iter().sorted().collect(), '1');

    let four_chars: HashSet<char> = signal_patterns
        .iter()
        .find(|pattern| pattern.len() == 4)
        .unwrap()
        .chars()
        .collect();

    numbers_map.insert(four_chars.iter().sorted().collect(), '4');

    let seven_chars: HashSet<char> = signal_patterns
        .iter()
        .find(|pattern| pattern.len() == 3)
        .unwrap()
        .chars()
        .collect();

    numbers_map.insert(seven_chars.iter().sorted().collect(), '7');

    let eight_chars: HashSet<char> = signal_patterns
        .iter()
        .find(|pattern| pattern.len() == 7)
        .unwrap()
        .chars()
        .collect();

    numbers_map.insert(eight_chars.iter().sorted().collect(), '8');

    signal_patterns
        .into_iter()
        .filter(|pattern| !unique_lengths.contains(&pattern.len()))
        .for_each(|pattern| {
            let one_intersection = pattern
                .chars()
                .collect::<HashSet<char>>()
                .intersection(&one_chars)
                .count();
            let four_intersection = pattern
                .chars()
                .collect::<HashSet<char>>()
                .intersection(&four_chars)
                .count();
            let seven_intersection = pattern
                .chars()
                .collect::<HashSet<char>>()
                .intersection(&seven_chars)
                .count();

            let sorted = pattern.clone().chars().sorted().collect::<String>();
            let sorted_len = sorted.len();
            match (one_intersection, four_intersection, seven_intersection) {
                (1, 2, 2) => numbers_map.insert(sorted, '2'),
                (2, 4, 3) => numbers_map.insert(sorted, '9'),
                (2, 3, 3) => numbers_map.insert(sorted, if sorted_len == 6 { '0' } else { '3' }),
                (1, 3, 2) => numbers_map.insert(sorted, if sorted_len == 6 { '6' } else { '5' }),
                k => {
                    println!("OH NO {:?} {:?}", pattern, k);
                    None
                }
            };
        });

    return entry
        .output_values
        .iter()
        .map(|value| {
            numbers_map
                .get(&value.chars().sorted().collect::<String>())
                .unwrap()
        })
        .collect::<String>()
        .parse::<i32>()
        .unwrap();
}

fn parse_entries(lines: &[String]) -> Vec<Entry> {
    return lines
        .into_iter()
        .map(|line| parse_entry_line(line))
        .collect();
}

fn parse_entry_line(line: &str) -> Entry {
    let mut tokens = line.split("|");
    return Entry {
        signal_patterns: tokens.next().unwrap().split_whitespace().collect(),
        output_values: tokens.next().unwrap().split_whitespace().collect(),
    };
}

#[derive(Debug)]
struct Entry<'a> {
    signal_patterns: Vec<&'a str>,
    output_values: Vec<&'a str>,
}
