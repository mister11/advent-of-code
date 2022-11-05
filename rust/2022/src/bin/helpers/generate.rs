use std::{env, fs};

const DAY_TEMPLATE: &str = r###"fn part_1(input: &str) -> String {
    return "".to_string()
}

fn part_2(input: &str) -> String {
    return "".to_string()
}

fn main() {
    let input = aoc_2022::read_input(<DAY>);

    println!("Part 1: {:?}", part_1(&input));
    println!("Part 2: {:?}", part_2(&input));
}

#[cfg(test)]
mod tests {
    use super::*;
    #[test]
    fn test_part_one() {
        let input = aoc_2022::read_example(<DAY>);
        assert_eq!(part_1(&input), "");
    }
    #[test]
    fn test_part_two() {
        let input = aoc_2022::read_example(<DAY>);
        assert_eq!(part_2(&input), "");
    }
}
"###;

fn main() {
    let day = std::env::args().nth(1).expect("Cannot read first argument");

    let content = DAY_TEMPLATE.replace("<DAY>", &day);

    create_source_file(&day, &content);
    create_example_input_file(&day);
    create_task_input_file(&day);
}


fn create_source_file(day: &str, content: &str) {
    let root = env::current_dir().expect("Cannot get current directory");

    let source_path = root.join("src").join("bin").join(format!("day_{:0>2}.rs", day));

    fs::write(&source_path, &content).expect("Cannot write source file.");
}

fn create_example_input_file(day: &str) {
    let root = env::current_dir().expect("Cannot get current directory");

    let example_dir_path = root.join("data").join("example").join(format!("{:0>2}.txt", day));

    fs::write(&example_dir_path, "").expect("Cannot write example input file.")
}

fn create_task_input_file(day: &str) {
    let root = env::current_dir().expect("Cannot get current directory");

    let task_input_path = root.join("data").join("input").join(format!("{:0>2}.txt", day));

    fs::write(&task_input_path, "").expect("Cannot write task input file.")
}
