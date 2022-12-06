package hr.mister11.aoc2022

import java.util.Stack

fun main() {
    val input = readInput("05")

    println("Part 1: ${Solution05.part1(input)}")
    println("Part 2: ${Solution05.part2(input)}")
}

object Solution05 {
    fun part1(input: String): String {
        val lines = input.lines()
        val splitIndex = lines.indexOf("")
        val supplies = parseSupplies(lines.subList(0, splitIndex))
        val moves = parseMoves(lines.subList(splitIndex + 1, lines.size))

        supplies.rearrangeSingle(moves)
        return supplies.getTopCrates()
    }

    fun part2(input: String): String {
        val lines = input.lines()
        val splitIndex = lines.indexOf("")
        val supplies = parseSupplies(lines.subList(0, splitIndex))
        val moves = parseMoves(lines.subList(splitIndex + 1, lines.size))

        supplies.rearrangeMultiple(moves)
        return supplies.getTopCrates()
    }

    private fun parseSupplies(supplyLines: List<String>): Supplies {
        val indicesLine = supplyLines.last()
        val maxIndex = indicesLine.trim().split("\\s+".toRegex()).last().toInt()

        val emptyStacks = (0 until maxIndex).map { Stack<Char>() }

        val stacks = supplyLines.reversed().drop(1).fold(emptyStacks) { stacks, crateLine ->
            stacks.mapIndexed { index, stack ->
                val value = crateLine.getOrNull(4 * index + 1)
                if (value != null && value != ' ') {
                    stack.push(value)
                }
                stack
            }
        }

        return Supplies(stacks)
    }

    private fun parseMoves(lines: List<String>): List<Move> {
        val regex = "move (\\d+) from (\\d+) to (\\d+)".toRegex()

        return lines.map { moveLine ->
            val groups = regex.find(moveLine)?.groupValues.orEmpty()

            Move(
                amount = groups[1].toInt(),
                fromIndex = groups[2].toInt() - 1,
                toIndex = groups[3].toInt() - 1
            )
        }
    }
}

data class Supplies(val stacks: List<Stack<Char>>) {
    fun rearrangeSingle(moves: List<Move>) {
        for (move in moves) {
            repeat(move.amount) {
                val removed = stacks[move.fromIndex].pop()
                stacks[move.toIndex].push(removed)
            }
        }
    }

    fun rearrangeMultiple(moves: List<Move>) {
        for (move in moves) {
            val values = (0 until move.amount).map { stacks[move.fromIndex].pop() }
            values.reversed().forEach {
                stacks[move.toIndex].push(it)
            }
        }
    }

    fun getTopCrates(): String {
        return stacks.map { it.peek() }.joinToString("")
    }
}

data class Move(
    val amount: Int, val fromIndex: Int, val toIndex: Int
)