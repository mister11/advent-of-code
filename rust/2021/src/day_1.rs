pub fn solve(input: &Vec<String>) {
    let parsed_input: Vec<i32> = input
        .into_iter()
        .map(|item| item.parse().unwrap())
        .collect();

    println!("Part 1: {}", part_1(&parsed_input));
    println!("Part 2: {}", part_2(&parsed_input));
}

fn part_1(input: &Vec<i32>) -> i32 {
    return input.windows(2).filter(|x| x[1] > x[0]).count() as i32;
}

fn part_2(input: &Vec<i32>) -> i32 {
    return input
        .windows(3)
        .map(|x| x.iter().sum())
        .collect::<Vec<i32>>()
        .windows(2)
        .filter(|x| x[1] > x[0])
        .count() as i32;
}
