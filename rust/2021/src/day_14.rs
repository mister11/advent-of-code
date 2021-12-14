use std::collections::HashMap;

use itertools::Itertools;

pub fn solve(input: &String) {
    println!("Part 1: {}", part_1(&input));
    println!("Part 2: {}", part_2(&input));
}

fn part_1(input: &String) -> usize {
    let (template, insertion_rules) = parse_input(input);

    let counts = (0..10)
        .fold(template, |current_template, _| {
            update_template(&current_template, &insertion_rules)
        })
        .chars()
        .into_iter()
        .counts();

    let (min, max) = counts.values().minmax().into_option().unwrap();
    return max - min;
}

// solution "borrowed" from: https://github.com/AxlLind/AdventOfCode2021/blob/main/src/bin/14.rs
fn part_2(input: &String) -> usize {
    let (template, insertion_rules) = parse_input(input);

    let initial_counts = template.chars().tuple_windows::<(char, char)>().counts();

    let all_counts = (0..40).fold(initial_counts, |aggregate_counts, _| {
        let mut updated_counts = HashMap::new();
        aggregate_counts.iter().for_each(|((char1, char2), count)| {
            let key: String = vec![*char1, *char2].iter().collect();
            let maps_to = insertion_rules[&key];
            *updated_counts.entry((*char1, maps_to)).or_insert(0) += count;
            *updated_counts.entry((maps_to, *char2)).or_insert(0) += count;
        });
        return updated_counts;
    });

    let mut final_count = HashMap::new();
    all_counts.iter().for_each(|((char1, _), count)| {
        *final_count.entry(char1).or_insert(0) += count;
    });

    let last_template_char = template.chars().last().unwrap();
    *final_count.entry(&last_template_char).or_insert(0) += 1;

    let (min, max) = final_count.values().minmax().into_option().unwrap();

    return max - min;
}

fn update_template(template: &str, insertion_rules: &HashMap<String, char>) -> String {
    return template.chars().tuple_windows::<(char, char)>().fold(
        String::with_capacity(2 * template.len() - 1),
        |mut acc, curr| {
            let code: String = vec![curr.0, curr.1].iter().collect();
            let maps_to = insertion_rules[&code];
            let result: String = if acc.is_empty() {
                vec![curr.0, maps_to, curr.1].iter().collect()
            } else {
                vec![maps_to, curr.1].iter().collect()
            };
            acc.push_str(&result);
            return acc;
        },
    );
}

fn parse_input(input: &String) -> (String, HashMap<String, char>) {
    let (template, rules) = input.split_once("\n\n").unwrap();
    return (template.to_string(), parse_insertion_rules(rules));
}

fn parse_insertion_rules(rules: &str) -> HashMap<String, char> {
    return rules
        .lines()
        .into_iter()
        .map(|line| line.split_once(" -> ").unwrap())
        .map(|(a, b)| (a.to_string(), b.chars().nth(0).unwrap()))
        .collect();
}
