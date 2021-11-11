package hr.mister11.aoc.year2020.day9

import hr.mister11.aoc.Resources

fun main() {
    val solution = Solution(Resources.readFileAsList(2020, 9))
    val part1Result = solution.part1()
    println("Part 1: $part1Result")
    println("Part 2: ${solution.part2(part1Result)}")
}

class Solution(
    rawValues: List<String>
) {

    private val preambleSize = 25
    private val values = rawValues.map { it.toLong() }

    fun part1(): Long {
        var index = preambleSize
        while (index < values.size) {
            val result = findSumPair(values[index], index)
            if (result == null) {
                return values[index]
            } else {
                index++
            }
        }
        return -1
    }

    fun part2(goalValue: Long): Long {
        var i = 0
        var j = 1
        while (i < values.size) {
            val sum = values.subList(i, j).sum()
            if (sum == goalValue) {
                val min = values.subList(i, j).minOrNull() ?: 0
                val max = values.subList(i, j).maxOrNull() ?: 0
                return min + max
            }
            if (sum < goalValue) {
                j++
            } else {
                i++
                j = i + 1
            }
        }
        return -1
    }

    private fun findSumPair(goalValue: Long, index: Int): Pair<Long, Long>? {
        val preambleValues = values.subList(index - preambleSize, index).sorted()
        var i = 0
        var j = preambleSize - 1
        while (i < j) {
            val iValue = preambleValues[i]
            val jValue = preambleValues[j]
            val sum = iValue + jValue
            if (sum == goalValue) {
                return Pair(iValue, jValue)
            }
            if (sum > goalValue) {
                j--
            } else {
                i++
            }
        }
        return null
    }
}