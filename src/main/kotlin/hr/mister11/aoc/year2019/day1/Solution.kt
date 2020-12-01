package hr.mister11.aoc.year2019.day1

import hr.mister11.aoc.Resources

fun main() {
    val solution = Solution(Resources.readFileAsList(2019, 1))
    println("Part 1: ${solution.part1()}")
    println("Part 2: ${solution.part2()}")
}

class Solution(private val massesInput: List<String>) {

    fun part1(): Int {
        return massesInput
            .map { it.toInt() }
            .fold(0) { accumulator, mass ->
                accumulator + moduleFuel(mass)
            }
    }

    fun part2(): Int {
        return massesInput
            .map { it.toInt() }
            .fold(0) { accumulator, mass ->
                accumulator + totalFuel(mass)
            }
    }

    private fun totalFuel(fuelLoad: Int): Int {
        var total = 0
        var remaining = moduleFuel(fuelLoad)
        while (remaining > 0) {
            total += remaining
            remaining = moduleFuel(remaining)
        }
        return total
    }

    private fun moduleFuel(fuel: Int): Int = fuel / 3 - 2
}
