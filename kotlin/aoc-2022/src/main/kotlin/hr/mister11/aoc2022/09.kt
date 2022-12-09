package hr.mister11.aoc2022

import kotlin.math.abs

fun main() {
    val input = readInput("09")

    println("Part 1: ${Solution09.part1(input)}")
    println("Part 2: ${Solution09.part2(input)}")
}

object Solution09 {
    fun part1(input: String): String {
        val moves = parseInput(input.lines())

        var headPosition = Position(0, 0)
        var tailPosition = Position(0, 0)
        val visitedPositions = mutableSetOf<Position>()

        moves.forEach { move ->
            (1..move.steps).forEach {
                headPosition = moveHead(move.direction, headPosition)
                tailPosition = moveTail(move.direction, tailPosition, headPosition)

                visitedPositions.add(tailPosition)
            }
        }

        return visitedPositions.size.toString()
    }

    fun part2(input: String): String {
        val moves = parseInput(input.lines())

        val knots = (1..10).map { Position(0, 0) }.toMutableList()
        val visitedPositions = mutableSetOf<Position>()

        moves.forEach { move ->
            (1..move.steps).forEach {
                var index = 0

                while (index < knots.size - 1) {
                    if (index == 0) {
                        val headPosition = moveHead(move.direction, knots.first())
                        val tailPosition = moveTail(move.direction, knots[1], headPosition)

                        knots[0] = headPosition
                        knots[1] = tailPosition
                    } else {
                        val tailPosition1 = moveTail(move.direction, knots[index], knots[index - 1])
                        val tailPosition2 = moveTail(move.direction, knots[index + 1], tailPosition1)

                        knots[index] = tailPosition1
                        knots[index + 1] = tailPosition2
                    }

                    index++
                }

                visitedPositions.add(knots.last())
            }
        }

        return visitedPositions.size.toString()
    }

    private fun parseInput(lines: List<String>): List<HeadMove> {
        return lines
            .map { line -> line.split("\\s+".toRegex()) }
            .map { tokens ->
                val directionValue = tokens[0]
                val steps = tokens[1].toInt()

                val direction = when (directionValue) {
                    "R" -> Direction.RIGHT
                    "L" -> Direction.LEFT
                    "U" -> Direction.UP
                    "D" -> Direction.DOWN
                    else -> throw IllegalArgumentException("Unknown direction")
                }

                HeadMove(direction, steps)
            }
    }

    private fun moveHead(direction: Direction, position: Position): Position {
        return when (direction) {
            Direction.RIGHT -> position.copy(x = position.x + 1)
            Direction.LEFT -> position.copy(x = position.x - 1)
            Direction.UP -> position.copy(y = position.y + 1)
            Direction.DOWN -> position.copy(y = position.y - 1)
        }
    }

    private fun moveTail(direction: Direction, tailPosition: Position, headPosition: Position): Position {
        val diff = abs(tailPosition.x - headPosition.x) + abs(tailPosition.y - headPosition.y)
        val isDiagonal = abs(tailPosition.x - headPosition.x) == 1 && abs(tailPosition.y - headPosition.y) == 1
        return when {
            // tail has to move diagonally
            diff == 3 || diff == 4 -> {
                val xDiff = headPosition.x - tailPosition.x
                val yDiff = headPosition.y - tailPosition.y

                val newX = if (abs(xDiff) == 2) { tailPosition.x + xDiff / 2 } else { tailPosition.x + xDiff }
                val newY = if (abs(yDiff) == 2) { tailPosition.y + yDiff / 2 } else { tailPosition.y + yDiff }

                tailPosition.copy(x = newX, y = newY)
            }
            // tail has to follow
            diff == 2 && !isDiagonal -> {
                val xDiff = headPosition.x - tailPosition.x
                val yDiff = headPosition.y - tailPosition.y
                val newX = if (xDiff != 0) tailPosition.x + xDiff / 2 else tailPosition.x
                val newY = if (yDiff != 0) tailPosition.y + yDiff / 2 else tailPosition.y
                tailPosition.copy(x = newX, y = newY)
            }
            else -> {
                tailPosition
            }
        }
    }
}

data class Position(
    val x: Int,
    val y: Int
)

data class HeadMove(
    val direction: Direction,
    val steps: Int
)

enum class Direction {
    RIGHT, LEFT, UP, DOWN
}