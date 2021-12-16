use std::collections::HashSet;

pub fn solve(lines: &[String]) {
    println!("Part 1: {}", part_1(&lines));
    println!("Part 2: {}", part_2(&lines));
}

fn part_1(lines: &[String]) -> i32 {
    let mut octopi = parse_input(lines);
    let mut flashes_total = 0;
    (0..100).for_each(|_| {
        let step_result = execute_step(&octopi);
        octopi = step_result.0;
        flashes_total += step_result.1;
    });

    return flashes_total;
}

fn part_2(lines: &[String]) -> i64 {
    let mut octopi = parse_input(lines);
    let mut index = 0;
    loop {
        index += 1;
        octopi = octopi
            .iter()
            .map(|octopi_row| octopi_row.iter().map(|energy| energy + 1).collect())
            .collect();
        let mut already_flashed: HashSet<(usize, usize)> = HashSet::new();
        loop {
            let mut increase_positions: Vec<(usize, usize)> = vec![];
            for i in 0..octopi.len() {
                for j in 0..octopi[0].len() {
                    if octopi[i][j] > 9 && !already_flashed.contains(&(i, j)) {
                        octopi[i][j] = 0;
                        already_flashed.insert((i, j));
                        increase_positions.extend(neighbors(
                            &(i as i32, j as i32),
                            octopi.len() as i32,
                            octopi[0].len() as i32,
                        ));
                    }
                }
            }
            if increase_positions.len() == 0 {
                break;
            }
            increase_positions
                .iter()
                .filter(|position| !already_flashed.contains(position))
                .for_each(|position| octopi[position.0][position.1] += 1);
            if octopi.iter().all(|o| o.iter().all(|o2| *o2 == 0)) {
                return index;
            }
        }
    }
}

fn execute_step(octopi: &Vec<Vec<i32>>) -> (Vec<Vec<i32>>, i32) {
    let mut updated_octopi: Vec<Vec<i32>> = octopi
        .iter()
        .map(|octopi_row| octopi_row.iter().map(|energy| energy + 1).collect())
        .collect();
    let mut already_flashed: HashSet<(usize, usize)> = HashSet::new();
    let mut flash_counter = 0;
    loop {
        print(&updated_octopi);
        let mut increase_positions: Vec<(usize, usize)> = vec![];
        for i in 0..octopi.len() {
            for j in 0..octopi[0].len() {
                if updated_octopi[i][j] > 9 && !already_flashed.contains(&(i, j)) {
                    updated_octopi[i][j] = 0;
                    flash_counter += 1;
                    already_flashed.insert((i, j));
                    increase_positions.extend(neighbors(
                        &(i as i32, j as i32),
                        octopi.len() as i32,
                        octopi[0].len() as i32,
                    ));
                }
            }
        }
        if increase_positions.len() == 0 {
            break;
        }
        increase_positions
            .iter()
            .filter(|position| !already_flashed.contains(position))
            .for_each(|position| updated_octopi[position.0][position.1] += 1);
        print(&updated_octopi);
    }
    return (updated_octopi, flash_counter);
}

fn neighbors(position: &(i32, i32), x_upper: i32, y_upper: i32) -> Vec<(usize, usize)> {
    return vec![
        (position.0 - 1, position.1),
        (position.0 + 1, position.1),
        (position.0, position.1 - 1),
        (position.0, position.1 + 1),
        (position.0 - 1, position.1 - 1),
        (position.0 + 1, position.1 + 1),
        (position.0 - 1, position.1 + 1),
        (position.0 + 1, position.1 - 1),
    ]
    .iter()
    .filter(|position| {
        position.0 >= 0 && position.0 < x_upper && position.1 >= 0 && position.1 < y_upper
    })
    .map(|position| (position.0 as usize, position.1 as usize))
    .collect();
}

fn print(v: &Vec<Vec<i32>>) {
    println!("[");
    v.iter().for_each(|f| println!("{:?}", f));
    println!("]");
}

fn parse_input(lines: &[String]) -> Vec<Vec<i32>> {
    return lines
        .iter()
        .map(|line| {
            line.split("")
                .filter(|n| !n.is_empty())
                .map(|num_str| num_str.parse::<i32>().unwrap())
                .collect()
        })
        .collect();
}
