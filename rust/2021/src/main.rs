use std::{
    fs::File,
    io::{BufRead, BufReader},
};

mod day_1;
mod day_2;

fn main() {
    day_2::solve(&read_file_as_list(1));
}

fn read_file(day: i32) -> String {
    std::fs::read_to_string(format!("../inputs/day_{}.txt", day))
        .expect("Input for day not readable")
}

fn read_file_as_list(day: i32) -> Vec<String> {
    let file =
        File::open(format!("../inputs/day_{}.txt", day)).expect("Input for day not readable");

    BufReader::new(file)
        .lines()
        .map(|l| l.expect("Cannot read line"))
        .collect()
}
