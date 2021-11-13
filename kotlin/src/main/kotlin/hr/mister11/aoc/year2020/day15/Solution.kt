package hr.mister11.aoc.year2020.day15

import hr.mister11.aoc.Resources

fun main() {

    val solution = Solution(Resources.readFileRaw(2020, 15))
    println("Part 1: ${solution.part1()}")
    println("Part 2: ${solution.part2()}")
}

class Solution(input: String) {

    private val numbers = input.split(",").map { it.toInt() }

    fun part1(): Int {
        val previouslySpokenTurn = hashMapOf<Int, Pair<Int, Int>>()
        numbers
            .forEachIndexed { index, number ->
                previouslySpokenTurn[number] = (index + 1 to -1)
            }
        var lastSpoken = numbers.last()
        var turn = numbers.size + 1
        while (turn <= 2020) {
            val lastSpokenTurn = previouslySpokenTurn.getOrElse(lastSpoken)  {
                throw RuntimeException("Should not happen")
            }
            lastSpoken = if (lastSpokenTurn.second == -1) {
                0
            } else {
                val diff = lastSpokenTurn.first - lastSpokenTurn.second
                diff
            }
            val newLastSpokenTurn = previouslySpokenTurn.getOrElse(lastSpoken) {
                Pair(-1, -1)
            }
            previouslySpokenTurn[lastSpoken] = (turn to newLastSpokenTurn.first)
            turn++
        }
        return lastSpoken
    }

    // same as part1 with different while exit condition
    // is there a repetition pattern? doesn't seem so
    fun part2(): Int {
        val previouslySpokenTurn = hashMapOf<Int, Pair<Int, Int>>()
        numbers
            .forEachIndexed { index, number ->
                previouslySpokenTurn[number] = (index + 1 to -1)
            }
        var lastSpoken = numbers.last()
        var turn = numbers.size + 1
        while (turn <= 30000000) {
            val lastSpokenTurn = previouslySpokenTurn.getOrElse(lastSpoken)  {
                throw RuntimeException("Should not happen")
            }
            lastSpoken = if (lastSpokenTurn.second == -1) {
                0
            } else {
                val diff = lastSpokenTurn.first - lastSpokenTurn.second
                diff
            }
            val newLastSpokenTurn = previouslySpokenTurn.getOrElse(lastSpoken) {
                Pair(-1, -1)
            }
            previouslySpokenTurn[lastSpoken] = (turn to newLastSpokenTurn.first)
            turn++
        }
        return lastSpoken
    }

}
