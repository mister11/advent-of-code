package hr.mister11.aoc.year2019.day5

import hr.mister11.aoc.Resources
import hr.mister11.aoc.year2019.intcode.Intcode

fun main() {
    val solution = Solution(Resources.readFileRaw(2019, 5))
    println("Part 1: ${solution.part1(1)}")
    println("Part 2: ${solution.part2(5)}")
}

class Solution(
    private val inputRaw: String
) {

    fun part1(input: Int): Int {
        val intcode = Intcode(inputRaw.split(",").map { it.toInt() }.toMutableList())
        intcode.addInput(input)
        runCatching { intcode.execute() } // nicer try-catch with empty catch clause
        return intcode.getLatestOutput()
    }

    fun part2(input: Int): Int {
        val intcode = Intcode(inputRaw.split(",").map { it.toInt() }.toMutableList())
        intcode.addInput(input)
        runCatching { intcode.execute() } // nicer try-catch with empty catch clause
        return intcode.getLatestOutput()
    }
}
