pub fn solve(input: &String) {
    let numbers = input
        .split(",")
        .map(|n| n.parse::<i32>().unwrap())
        .collect();
    println!("Part 1: {}", part_1(&numbers));
    println!("Part 2: {}", part_2(&numbers));
}

fn part_1(numbers: &Vec<i32>) -> i64 {
    return calculate(numbers, 80);
}

fn part_2(numbers: &Vec<i32>) -> i64 {
    return calculate(numbers, 256);
}

fn calculate(numbers: &Vec<i32>, days: i32) -> i64 {
    let mut counts: Vec<i64> = (0..9)
        .map(|i| numbers.clone().into_iter().filter(|n| *n == i).count() as i64)
        .collect();

    (0..days).for_each(|_ignore| {
        let day0_count = counts.remove(0);
        counts[6] += day0_count;
        counts.push(day0_count);
    });
    return counts.into_iter().sum();
}
