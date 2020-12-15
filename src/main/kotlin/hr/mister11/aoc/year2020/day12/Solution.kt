package hr.mister11.aoc.year2020.day12

import hr.mister11.aoc.Resources
import kotlin.math.abs

fun main() {
    val solution = Solution(Resources.readFileAsList(2020, 12))
    println("Part 1: ${solution.part1()}")
    println("Part 2: ${solution.part2()}")
}

class Solution(
    instructionsRaw: List<String>
) {

    private val instructions: List<Instruction> = parseInstructions(instructionsRaw)

    fun part1(): Int {
        var currentDirection = Direction.E
        var currentPosition = Pair(0, 0)
        instructions.forEach {
            when (it.direction) {
                Direction.N, Direction.W, Direction.E, Direction.S -> {
                    currentPosition = evaluateMove(it.value, it.direction, currentPosition)
                }
                Direction.L -> currentDirection = currentDirection.turnShip(it.value, rotate90Left)
                Direction.R -> currentDirection = currentDirection.turnShip(it.value, rotate90Right)
                Direction.F -> currentPosition = evaluateMove(it.value, currentDirection, currentPosition)
            }
        }
        return abs(currentPosition.first) + abs(currentPosition.second)
    }

    fun part2(): Int {
        var shipPosition = Pair(0, 0)
        var waypointOffset = Pair(10, 1)
        instructions.forEach {
            when (it.direction) {
                Direction.N, Direction.W, Direction.E, Direction.S -> {
                    waypointOffset = evaluateMove(it.value, it.direction, waypointOffset)
                }
                Direction.L -> {
                    var remDegrees = it.value
                    while (remDegrees > 0) {
                        waypointOffset = Pair(
                            -waypointOffset.second,
                            waypointOffset.first
                        )
                        remDegrees -= 90
                    }
                }
                Direction.R -> {
                    var remDegrees = it.value
                    while (remDegrees > 0) {
                        waypointOffset = Pair(
                            waypointOffset.second,
                            -waypointOffset.first
                        )
                        remDegrees -= 90
                    }
                }
                Direction.F -> {
                    val westToEastDirection = if (waypointOffset.first >= 0) Direction.E else Direction.W
                    val northToSouthDirection = if (waypointOffset.second >= 0) Direction.N else Direction.S
                    // abs is used since direction will take care of adding/subtracting
                    val westToEastValue = abs(waypointOffset.first) * it.value
                    val northToSouthValue = abs(waypointOffset.second) * it.value
                    shipPosition = evaluateMove(westToEastValue, westToEastDirection, shipPosition)
                    shipPosition = evaluateMove(northToSouthValue, northToSouthDirection, shipPosition)
                }
            }
        }
        return abs(shipPosition.first) + abs(shipPosition.second)
    }

    private fun evaluateMove(
        value: Int,
        direction: Direction,
        position: Pair<Int, Int>
    ): Pair<Int, Int> {
        return when (direction) {
            Direction.N -> Pair(
                position.first,
                position.second + value
            )
            Direction.W -> Pair(
                position.first - value,
                position.second
            )
            Direction.E -> Pair(
                position.first + value,
                position.second
            )
            Direction.S -> Pair(
                position.first,
                position.second - value
            )
            else -> throw RuntimeException("Cannot evaluate move for $direction. THIS SHOULD NOT HAPPEN.")
        }
    }

    private fun parseInstructions(instructionsRaw: List<String>): List<Instruction> {
        return instructionsRaw.map {
            val direction = Direction.fromValue(it[0].toString())
            val value = it.substring(1).toInt()
            Instruction(value, direction)
        }
    }
}

data class Instruction(
    val value: Int,
    val direction: Direction
)

enum class Direction {
    N, W, E, S, L, R, F;

    companion object {
        fun fromValue(value: String) = values().first { it.name == value }
    }
}

fun Direction.turnShip(degrees: Int, rotationFun: (Direction) -> Direction): Direction {
    var remDegrees = degrees
    var currentDirection = this
    while (remDegrees > 0) {
        currentDirection = rotationFun(currentDirection)
        remDegrees -= 90
    }
    return currentDirection
}

private val rotate90Left = { direction: Direction ->
    when (direction) {
        Direction.N -> Direction.W
        Direction.W -> Direction.S
        Direction.E -> Direction.N
        Direction.S -> Direction.E
        else -> throw RuntimeException("Cannot turn left when facing $direction. THIS SHOULD NOT HAPPEN.")
    }
}

private val rotate90Right = { direction: Direction ->
    when (direction) {
        Direction.N -> Direction.E
        Direction.W -> Direction.N
        Direction.E -> Direction.S
        Direction.S -> Direction.W
        else -> throw RuntimeException("Cannot turn left when facing $direction. THIS SHOULD NOT HAPPEN.")
    }
}
