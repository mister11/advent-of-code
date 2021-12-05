use std::{
    fs::File,
    io::{BufRead, BufReader}, env,
};

mod day_1;
mod day_2;
mod day_3;
mod day_4;
mod day_5;

fn main() {
    let day = env::args().last().unwrap();
    match day.as_str() {
        "1" => day_1::solve(&read_file_as_list(1)),
        "2" => day_2::solve(&read_file_as_list(2)),
        "3" => day_3::solve(&read_file_as_list(3)),
        "4" => day_4::solve(&read_file(4)),
        "5" => day_5::solve(&read_file_as_list(5)),
        _ => println!("Unknown day parameter")
    }
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
