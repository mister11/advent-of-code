package hr.mister11.aoc.year2020.day1

import hr.mister11.aoc.Resources

fun main() {
    val solution = Solution(Resources.readFileAsList(2020, 1))
    println("Part 1: ${solution.part1()}")
    println("Part 2: ${solution.part2()}")
}

class Solution(
    inputNumbers: List<String>
) {

    private val goalSum = 2020
    private val numbers: List<Int> = inputNumbers.map { it.toInt() }

    fun part1(): Int {
        val processedNumbers = mutableSetOf<Int>()
        numbers.forEach { number ->
            val left = goalSum - number
            if (processedNumbers.contains(left)) {
                return left * number
            }
            processedNumbers.add(number)
        }
        return 0
    }

    fun part2(): Int {
        val sortedNumbers = numbers.sorted()
        sortedNumbers
            .forEachIndexed { index, value ->
                var left = index + 1
                var right = numbers.size - 1
                while (left < right) {
                    if (value + sortedNumbers[left] + sortedNumbers[right] == goalSum) {
                        return value * sortedNumbers[left] * sortedNumbers[right]
                    }
                    if (value + sortedNumbers[left] + sortedNumbers[right] > goalSum) {
                        right--
                    }
                    if (value + sortedNumbers[left] + sortedNumbers[right] < goalSum) {
                        left++
                    }
                }
            }
        return 0
    }
}