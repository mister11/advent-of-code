package hr.mister11.aoc.year2020.day5

import hr.mister11.aoc.Resources
import kotlin.math.min

fun main() {
    val solution = Solution(Resources.readFileAsList(2020, 5))
    println("Part 1: ${solution.part1()}")
    println("Part 2: ${solution.part2()}")
}

class Solution(
    private val boardingPasses: List<String>
) {

    fun part1(): Int {
        return boardingPasses
            .map { getSeatId(it) }
            .maxOrNull() ?: -1
    }

    fun part2(): Int {
        val seatIds = boardingPasses
            .map { getSeatId(it) }
            .sorted()
        var index = 0
        val size = seatIds.size
        while (index < size - 1) {
            if (seatIds[index + 1] - seatIds[index] > 1) {
                return seatIds[index] + 1
            }
            index++
        }
        return -1
    }

    private fun getSeatId(boardingPass: String): Int {
        var front = 0
        var back = 127
        var left = 0
        var right = 7
        boardingPass.forEach {
            when (it) {
                'R' -> left += ((right - left) / 2 + 1)
                'L' -> right -= ((right - left) / 2 + 1)
                'B' -> front += ((back - front) / 2 + 1)
                'F' -> back -= ((back - front) / 2 + 1)
            }
        }
        return 8 * min(front, back) + min(left, right)
    }
}

