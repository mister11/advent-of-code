package hr.mister11.aoc.year2019.day4

import hr.mister11.aoc.Resources

fun main() {
    val solution = Solution(Resources.readFileRaw(4))
    println("Part 1: ${solution.part1()}")
    println("Part 2: ${solution.part2()}")
}

class Solution(
    rangeInput: String
) {

    private val range: Pair<Int, Int> = parseRangeInput(rangeInput)

    fun part1(): Int {
        return IntRange(range.first, range.second)
            .filter { validateSimplePassword(it.toString()) }
            .count()
    }

    fun part2(): Int {
        return IntRange(range.first, range.second)
            .filter { validateAdvancedPassword(it.toString()) }
            .count()
    }

    private fun validateSimplePassword(password: String): Boolean {
        val length = password.length
        if (length != 6) {
            return false
        }
        if (!isEverIncreasing(password)) {
            return false
        }
        var index = 0
        var hasTwoAdjacentSame = false
        while (index < length - 1) {
            if (password[index] == password[index + 1]) {
                hasTwoAdjacentSame = true
            }
            index++
        }
        return hasTwoAdjacentSame
    }

    private fun validateAdvancedPassword(password: String): Boolean {
        val length = password.length
        if (length != 6) {
            return false
        }
        if (!isEverIncreasing(password)) {
            return false
        }
        var index = 0
        var hasTwoAdjacentSame = false
        while (index < length - 1) {
            if (password[index] == password[index + 1]) {
                hasTwoAdjacentSame = true
                while (++index < length - 1 && password[index] == password[index + 1]) {
                    hasTwoAdjacentSame = false
                }
                // if it's still valid, we found one group of 2 digits which is valid
                if (hasTwoAdjacentSame) {
                    return true
                }
            } else {
                index++
            }
        }
        return hasTwoAdjacentSame
    }

    private fun isEverIncreasing(password: String): Boolean {
        var index = 0
        while (index < password.length - 1) {
            if (password[index] > password[index + 1]) {
                return false
            }
            index++
        }
        return true
    }

    private fun parseRangeInput(rangeInput: String): Pair<Int, Int> {
        val tokens = rangeInput.split("-")
        return tokens[0].toInt() to tokens[1].toInt()
    }
}
