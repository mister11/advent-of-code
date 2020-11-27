package hr.mister11.aoc.year2019

import hr.mister11.aoc.Resources
import hr.mister11.aoc.year2019.day2.Solution

fun main() {
    val solution = Solution(Resources.readFileRaw(2))
    println("Part 1: ${solution.part1()}")
    println("Part 2: ${solution.part2()}")
}
