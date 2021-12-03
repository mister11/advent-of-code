pub fn solve(input: &Vec<String>) {
    println!("Part 1: {}", part_1(input));
    println!("Part 2: {}", part_2(input));
}

fn part_1(input: &Vec<String>) -> i32 {
    let mut horizontal = 0;
    let mut depth = 0;
    for line in input {
        let tokens: Vec<_> = line.split(' ').collect();
        let command: &str = tokens.get(0).unwrap();
        let amount: i32 = tokens.get(1).unwrap().parse().unwrap();
        match command {
            "forward" => horizontal += amount,
            "down" => depth += amount,
            "up" => depth -= amount,
            _ => println!("Unknown command")
        }
    }
    horizontal * depth
}

fn part_2(input: &Vec<String>) -> i64 {
    let mut horizontal: i64 = 0;
    let mut depth: i64 = 0;
    let mut aim: i64 = 0;
    for line in input {
        let tokens: Vec<_> = line.split(' ').collect();
        let command: &str = tokens.get(0).unwrap();
        let amount: i64 = tokens.get(1).unwrap().parse().unwrap();
        match command {
            "forward" => {
                horizontal += amount;
                depth += aim * amount;
            }
            "down" => aim += amount,
            "up" => aim -= amount,
            _ => println!("Unknown command")
        }
    }
    horizontal * depth
}
