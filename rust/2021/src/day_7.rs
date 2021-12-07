pub fn solve(input: &String) {
    let numbers: Vec<i32> = input
        .split(",")
        .map(|n| n.parse::<i32>().unwrap())
        .collect();
    println!("Part 1: {}", part_1(&numbers));
    println!("Part 2: {}", part_2(&numbers));
}

fn part_1(numbers: &[i32]) -> i32 {
    let diff_to_target = |numbers: &[i32], target: i32| -> i32 {
        return numbers.into_iter().map(|n| (n - target).abs()).sum();
    };
    return calculate_position(numbers, &diff_to_target);
}

fn part_2(numbers: &[i32]) -> i32 {
    let diff_to_target = |numbers: &[i32], target: i32| -> i32 {
        return numbers
            .into_iter()
            .map(|n| sum_first_n((n - target).abs()))
            .sum();
    };
    return calculate_position(numbers, &diff_to_target);
}

fn calculate_position(numbers: &[i32], diff_fn: &dyn Fn(&[i32], i32) -> i32) -> i32 {
    let min = numbers.iter().min().cloned().unwrap();
    let max = numbers.iter().max().cloned().unwrap();

    return (min..max)
        .map(|target| (target, diff_fn(numbers, target)))
        .min_by_key(|(_target, diff)| *diff)
        .unwrap()
        .1;
}

fn sum_first_n(n: i32) -> i32 {
    n * (n + 1) / 2
}
