/**
 * This is one of the worst pieces of code I've ever written.
 *
 * I still have no idea how to use this language so I'm just throwing everything together until
 * it works...
 */
use std::collections::{HashMap, HashSet};

const BINGO_CARD_SIZE: usize = 5;

pub fn solve(input: &String) {
    let lines = input
        .split("\n")
        .map(|line| line.trim())
        .filter(|line| !line.is_empty())
        .collect::<Vec<&str>>();

    let numbers = lines
        .first()
        .unwrap()
        .split(",")
        .map(|n| n.parse::<i32>().unwrap())
        .collect();

    // f*** it, just clone it as I don't have patience to fight the compiler...
    println!("Part 1: {}", part_1(&numbers, lines.clone()));
    println!("Part 2: {}", part_2(&numbers, lines.clone()));
}

fn part_1(numbers: &Vec<i32>, lines: Vec<&str>) -> i32 {
    let cards: Vec<HashMap<i32, (usize, usize)>> = parse_cards(lines.into_iter().skip(1).collect());

    let mut cards_marked_positions: Vec<HashSet<(usize, usize)>> =
        (0..cards.len()).map(|_index| HashSet::new()).collect();

    for number in numbers {
        for (index, card) in cards.iter().enumerate() {
            let marked_positions = &mut cards_marked_positions[index];
            let position_maybe = card.get(&number);
            if position_maybe.is_some() {
                let position = position_maybe.unwrap();
                marked_positions.insert(*position);
                if is_card_winning(&marked_positions) {
                    let unmarked_sum = calculated_unmarked_sum(card, marked_positions);
                    return number * unmarked_sum;
                }
            }
        }
    }
    return -1;
}

fn part_2(numbers: &Vec<i32>, lines: Vec<&str>) -> i32 {
    let cards: Vec<HashMap<i32, (usize, usize)>> = parse_cards(lines.into_iter().skip(1).collect());

    let mut cards_marked_positions: Vec<HashSet<(usize, usize)>> =
        (0..cards.len()).map(|_index| HashSet::new()).collect();

    // remember indices of won cards and skip over them
    let mut won_cards = HashSet::new();
    for number in numbers {
        for (index, card) in cards.iter().enumerate() {
            if won_cards.contains(&index) {
                continue;
            }
            let marked_positions = &mut cards_marked_positions[index];
            let position_maybe = card.get(&number);
            if position_maybe.is_some() {
                let position = position_maybe.unwrap();
                marked_positions.insert(*position);
                if is_card_winning(&marked_positions) {
                    if (cards.len() - won_cards.len()) == 1 {
                        let unmarked_sum = calculated_unmarked_sum(card, marked_positions);
                        return number * unmarked_sum;
                    }
                    won_cards.insert(index);
                }
            }
        }
    }
    return -1;
}

fn parse_cards(lines: Vec<&str>) -> Vec<HashMap<i32, (usize, usize)>> {
    lines
        .chunks(BINGO_CARD_SIZE)
        .map(|chunk| {
            chunk
                .into_iter()
                .enumerate()
                .flat_map(|(row_index, line)| {
                    line.split_whitespace()
                        .into_iter()
                        .enumerate()
                        .map(|(col_index, n)| (n.parse::<i32>().unwrap(), (row_index, col_index)))
                        .collect::<Vec<(i32, (usize, usize))>>()
                })
                .collect::<Vec<(i32, (usize, usize))>>()
        })
        .map(|card_vec| card_vec.into_iter().collect())
        .collect()
}

fn is_card_winning(marked_positions: &HashSet<(usize, usize)>) -> bool {
    let mut is_winning = true;
    // check rows
    for i in 0..BINGO_CARD_SIZE {
        for j in 0..BINGO_CARD_SIZE {
            is_winning &= marked_positions.contains(&(i, j));
            if !is_winning {
                break;
            }
        }
        if is_winning {
            return true;
        }
        is_winning = true;
    }

    // check cols
    for j in 0..BINGO_CARD_SIZE {
        for i in 0..BINGO_CARD_SIZE {
            is_winning &= marked_positions.contains(&(i, j));
            if !is_winning {
                break;
            }
        }
        if is_winning {
            return true;
        }
        is_winning = true;
    }

    // check diagonals
    is_winning = true;
    for i in 0..BINGO_CARD_SIZE {
        is_winning &= marked_positions.contains(&(i, i));
    }
    if is_winning {
        return true;
    }

    is_winning = true;
    for i in 0..BINGO_CARD_SIZE {
        is_winning &= marked_positions.contains(&(i, BINGO_CARD_SIZE - i));
    }
    if is_winning {
        return true;
    }

    is_winning
}

fn calculated_unmarked_sum(
    card: &HashMap<i32, (usize, usize)>,
    marked_positions: &HashSet<(usize, usize)>,
) -> i32 {
    card.iter()
        .map(|(value, position)| {
            if !marked_positions.contains(position) {
                value
            } else {
                &0
            }
        })
        .sum()
}
