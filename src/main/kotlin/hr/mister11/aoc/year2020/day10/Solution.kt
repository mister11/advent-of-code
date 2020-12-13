package hr.mister11.aoc.year2020.day10

import hr.mister11.aoc.Resources

fun main() {
    val solution = Solution(Resources.readFileAsList(2020, 10))
    println("Part 1: ${solution.part1()}")
    println("Part 2: ${solution.part2()}")
}

class Solution(
    adapterJoltagesRaw: List<String>
) {

    private val adapterJoltages = adapterJoltagesRaw.map { it.toInt() }

    fun part1(): Int {
        val sortedJoltages = adapterJoltages.sorted()
        var oneDiffCounter = 0
        var threeDiffCounter = 1 // my device, always diff = 3
        var currentJoltage = 0
        sortedJoltages.forEach { joltage ->
            val diff = joltage - currentJoltage
            if (diff == 1) {
                oneDiffCounter++
            } else if (diff == 3) {
                threeDiffCounter++
            }
            currentJoltage += diff
        }

        return oneDiffCounter * threeDiffCounter
    }

    // thanks Reddit
    fun part2(): Long {
        val map = mutableMapOf(0 to 1L)
        val joltages = adapterJoltages.sorted()
        joltages.forEach { joltage ->
            map[joltage] = +map.getOrDefault(joltage - 1, 0) + map.getOrDefault(joltage - 2, 0) +
                map.getOrDefault(joltage - 3, 0)
        }
        return map.getOrDefault(joltages.last(), -1L)
    }

}
