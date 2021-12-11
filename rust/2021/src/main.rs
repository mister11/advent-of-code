use std::{
    env,
    fs::File,
    io::{BufRead, BufReader},
};

mod day_1;
mod day_10;
mod day_2;
mod day_3;
mod day_4;
mod day_5;
mod day_6;
mod day_7;
mod day_8;
mod day_9;

fn main() {
    let day = env::args().last().unwrap();
    match day.as_str() {
        "1" => day_1::solve(&read_file_as_list(1)),
        "2" => day_2::solve(&read_file_as_list(2)),
        "3" => day_3::solve(&read_file_as_list(3)),
        "4" => day_4::solve(&read_file(4)),
        "5" => day_5::solve(&read_file_as_list(5)),
        "6" => day_6::solve(&read_file(6)),
        "7" => day_7::solve(&read_file(7)),
        "8" => day_8::solve(&read_file_as_list(8)),
        "9" => day_9::solve(&read_file_as_list(9)),
        "10" => day_10::solve(&read_file_as_list(10)),
        _ => println!("Unknown day parameter"),
    }
}

fn read_file(day: i32) -> String {
    return std::fs::read_to_string(file_path(day)).expect("Input for day not readable");
}

fn read_file_as_list(day: i32) -> Vec<String> {
    let file = File::open(file_path(day)).expect("Input for day not readable");

    BufReader::new(file)
        .lines()
        .map(|l| l.expect("Cannot read line"))
        .collect()
}

fn file_path(day: i32) -> String {
    return format!("../inputs/day_{}.txt", day);
}
