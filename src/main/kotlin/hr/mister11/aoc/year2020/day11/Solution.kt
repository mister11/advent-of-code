package hr.mister11.aoc.year2020.day11

import hr.mister11.aoc.Resources
import java.lang.Integer.max

fun main() {
    val solution = Solution(Resources.readFileAsList(2020, 11))
    println("Part 1: ${solution.part1()}")
    println("Part 2: ${solution.part2()}")
}

class Solution(
    seatLayoutRaw: List<String>
) {

    private val seatLayout: Map<Pair<Int, Int>, Char> = seatLayoutRaw.flatMapIndexed { i, row ->
        row.toCharArray().mapIndexed { j, c ->
            (i to j) to c
        }
    }.toMap()

    fun part1(): Int {
        val layoutsVisited = mutableSetOf<Map<Pair<Int, Int>, Char>>()
        var currentLayout = seatLayout.toMap()
        while (layoutsVisited.contains(currentLayout).not()) {
            layoutsVisited.add(currentLayout)
            val newLayout = currentLayout.map { (position, value) ->
                val occupied = numOfAdjacentOccupied(currentLayout, position.first, position.second)
                if (value == 'L' && occupied == 0) {
                    position to '#'
                } else if (value == '#' && occupied >= 4) {
                    position to 'L'
                } else {
                    position to currentLayout.getOrDefault(position, '.')
                }
            }.toMap()
            currentLayout = newLayout
        }
        return currentLayout.values.count { it == '#' }
    }

    fun part2(): Int {
        val rows = seatLayout.maxOf { it.key.first }
        val cols = seatLayout.maxOf { it.key.second }
        val range = IntRange(1, max(rows, cols))
        var currentLayout = seatLayout
        var isUpdated = true
        while (isUpdated) {
            isUpdated = false
            val newLayout: MutableMap<Pair<Int, Int>, Char> = mutableMapOf()
            currentLayout.forEach { (position, value) ->
                val i = position.first
                val j = position.second
                val occupied = listOfNotNull(
                    range
                        .firstOrNull { offset -> currentLayout[i - offset to j - offset] != '.' }
                        ?.let { currentLayout[i - it to j - it] },
                    range
                        .firstOrNull { offset -> currentLayout[i - offset to j] != '.' }
                        ?.let { currentLayout[i - it to j] },
                    range
                        .firstOrNull { offset -> currentLayout[i - offset to j + offset] != '.' }
                        ?.let { currentLayout[i - it to j + it] },
                    range
                        .firstOrNull { offset -> currentLayout[i to j - offset] != '.' }
                        ?.let { currentLayout[i to j - it] },
                    range
                        .firstOrNull { offset -> currentLayout[i to j + offset] != '.' }
                        ?.let { currentLayout[i to j + it] },
                    range
                        .firstOrNull { offset -> currentLayout[i + offset to j - offset] != '.' }
                        ?.let { currentLayout[i + it to j - it] },
                    range
                        .firstOrNull { offset -> currentLayout[i + offset to j] != '.' }
                        ?.let { currentLayout[i + it to j] },
                    range
                        .firstOrNull { offset -> currentLayout[i + offset to j + offset] != '.' }
                        ?.let { currentLayout[i + it to j + it] }

                ).count { it == '#' }
                if (value == 'L' && occupied == 0) {
                    isUpdated = true
                    newLayout[position] = '#'
                } else if (value == '#' && occupied >= 5) {
                    isUpdated = true
                    newLayout[position] = 'L'
                } else {
                    newLayout[position] = currentLayout.getOrDefault(position, '.')
                }
            }
            currentLayout = newLayout
        }
        return currentLayout.values.count { it == '#' }
    }

    private fun numOfAdjacentOccupied(layout: Map<Pair<Int, Int>, Char>, i: Int, j: Int): Int {
        return listOfNotNull(
            layout[i - 1 to j - 1],
            layout[i - 1 to j],
            layout[i - 1 to j + 1],
            layout[i to j - 1],
            layout[i to j + 1],
            layout[i + 1 to j - 1],
            layout[i + 1 to j],
            layout[i + 1 to j + 1],

            ).count { it == '#' }
    }
}
