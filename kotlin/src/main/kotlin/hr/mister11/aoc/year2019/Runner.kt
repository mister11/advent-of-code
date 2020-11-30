package hr.mister11.aoc.year2019

import hr.mister11.aoc.Resources
import hr.mister11.aoc.year2019.day3.Solution

fun main() {
    val solution = Solution(Resources.readFileAsList(3))
    println("Part 1: ${solution.part1()}")
    println("Part 2: ${solution.part2()}")
}
