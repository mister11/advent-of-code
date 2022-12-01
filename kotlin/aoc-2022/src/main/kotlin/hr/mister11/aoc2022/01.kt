package hr.mister11.aoc2022

fun main() {
    val input = readInput("01")
    
    println("Part 1: ${Solution01.part1(input)}")
    println("Part 2: ${Solution01.part2(input)}")
}


object Solution01 {
    fun part1(input: String): String {
        return input
            .split("\n\n")
            .maxOfOrNull { calories -> calories.split("\n").sumOf { it.toInt() } }
            .toString()
    }

    fun part2(input: String): String {
        return input
            .split("\n\n")
            .map { calories -> calories.split("\n").sumOf { it.toInt() } }
            .sortedDescending()
            .take(3)
            .sum()
            .toString()
    }
}