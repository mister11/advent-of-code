use std::{env, fs};

pub fn read_input(day: usize) -> String {
    return read_file(day, "input");
}

pub fn read_example(day: usize) -> String {
    return read_file(day, "example");
}

fn read_file(day: usize, folder_name: &str) -> String {
    let root = env::current_dir().expect("Cannot get current directory.");
    let input_path = root.join("data").join(folder_name).join(format!("{:0>2}.txt", day));

    return fs::read_to_string(input_path).expect(&format!("Cannot read {folder_name} for day {day}"));
}
