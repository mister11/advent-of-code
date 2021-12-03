pub fn solve(input: &Vec<String>) {
    println!("Part 1: {}", part_1(input));
    println!("Part 2: {}", part_2(input));
}

fn part_1(input: &Vec<String>) -> i32 {
    let counters = get_counters(input);

    let gamma_string = counters
        .iter()
        .map(|counts| if counts.0 > counts.1 { '0' } else { '1' })
        .collect::<String>();

    let epsilon_string = counters
        .iter()
        .map(|counts| if counts.0 > counts.1 { '1' } else { '0' })
        .collect::<String>();

    let gamma = isize::from_str_radix(&gamma_string, 2).unwrap() as i32;
    let epsilon = isize::from_str_radix(&epsilon_string, 2).unwrap() as i32;

    gamma * epsilon
}

fn part_2(input: &Vec<String>) -> i32 {
    let mut counters = get_counters(input);

    let mut oxy_ratings = input.clone();
    let mut index = 0;
    while oxy_ratings.len() != 1 {
        let count = counters[index];
        let comparison = if count.0 > count.1 { '0' } else { '1' };
        oxy_ratings.retain(|line| line.chars().nth(index) == Some(comparison));
        counters = get_counters(&oxy_ratings);
        index += 1;
    }

    let mut co2_scrubber = input.clone();
    index = 0;
    while co2_scrubber.len() != 1 {
        let count = counters[index];
        let comparison = if count.0 <= count.1 { '0' } else { '1' };
        co2_scrubber.retain(|line| line.chars().nth(index) == Some(comparison));
        counters = get_counters(&co2_scrubber);
        index += 1;
    }

    let oxy_value = isize::from_str_radix(&oxy_ratings.first().unwrap(), 2).unwrap() as i32;
    let co2_value = isize::from_str_radix(&co2_scrubber.first().unwrap(), 2).unwrap() as i32;

    oxy_value * co2_value
}

fn get_counters(input: &Vec<String>) -> Vec<(i32, i32)> {
    let input_chars: Vec<Vec<char>> = input.iter().map(|x| x.chars().collect()).collect();

    // not sure how to do zip(input_chars) trick in Rust so we do transpose operations manually
    (0..input[0].len())
        .map(|index| input_chars.iter().map(move |item| item[index]).collect())
        .map(|x: Vec<char>| {
            x.iter().fold((0, 0), |acc, item| {
                if '0' == *item {
                    (acc.0 + 1, acc.1)
                } else {
                    (acc.0, acc.1 + 1)
                }
            })
        })
        .collect()
}