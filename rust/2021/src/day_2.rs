pub fn solve(input: &[String]) {
    println!("Part 1: {}", part_1(&input));
    println!("Part 2: {}", part_2(&input));
}

fn part_1(lines: &[String]) -> i64 {
    let update_fn = |position: Position, command: &str, amount: i64| -> Position {
        match command {
            "forward" => position.update_horizontal(amount),
            "down" => position.update_depth(amount),
            "up" => position.update_depth(-amount),
            _ => {
                println!("Unknown command: {}", command);
                return position;
            }
        }
    };
    return calculate_result(lines, &update_fn);
}

fn part_2(lines: &[String]) -> i64 {
    let update_fn = |position: Position, command: &str, amount: i64| -> Position {
        match command {
            "forward" => {
                return position
                    .update_horizontal(amount)
                    .update_depth(position.aim * amount)
            }
            "down" => position.update_aim(amount),
            "up" => position.update_aim(-amount),
            _ => {
                println!("Unknown command: {}", command);
                return position;
            }
        }
    };
    return calculate_result(lines, &update_fn);
}

fn calculate_result(lines: &[String], update_fn: &dyn Fn(Position, &str, i64) -> Position) -> i64 {
    let position = lines.iter().fold(
        Position {
            ..Default::default()
        },
        |position, line| {
            let tokens = line.split(' ').collect::<Vec<_>>();
            let command = tokens[0];
            let amount = tokens[1].parse::<i64>().unwrap();
            return update_fn(position, command, amount);
        },
    );

    return position.horizontal * position.depth;
}

struct Position {
    horizontal: i64,
    depth: i64,
    aim: i64,
}

impl Position {
    fn update_horizontal(&self, amount: i64) -> Position {
        Position {
            horizontal: self.horizontal + amount,
            depth: self.depth,
            aim: self.aim,
        }
    }

    fn update_depth(&self, amount: i64) -> Position {
        Position {
            horizontal: self.horizontal,
            depth: self.depth + amount,
            aim: self.aim,
        }
    }

    fn update_aim(&self, amount: i64) -> Position {
        Position {
            horizontal: self.horizontal,
            depth: self.depth,
            aim: self.aim + amount,
        }
    }
}

impl Default for Position {
    fn default() -> Position {
        Position {
            horizontal: 0,
            depth: 0,
            aim: 0,
        }
    }
}
