package hr.mister11.aoc.year2019.day3

import kotlin.math.abs

class Solution(
    wireInputs: List<String>
) {

    private val wire1 = parseWireInput(wireInputs[0])
    private val wire2 = parseWireInput(wireInputs[1])

    fun part1(): Int {
        val wireMap1 = processWire(wire1)
        val wireMap2 = processWire(wire2)

        return wireMap1.keys
            .intersect(wireMap2.keys)
            .minOf { abs(it.x) + abs(it.y) }
    }

    fun part2(): Int {
        val wireMap1 = processWire(wire1)
        val wireMap2 = processWire(wire2)

        return wireMap1.keys
            .intersect(wireMap2.keys)
            .minOf { key -> wireMap1.getOrDefault(key, 0) + wireMap2.getOrDefault(key, 0) }
    }

    private fun processWire(wire: Wire): Map<Position, Int> {
        val pathMap = mutableMapOf<Position, Int>()
        var currentPosition = Position(0, 0)
        var counter = 1
        wire.paths.forEach { path ->
            val positions = getPathPositions(path, currentPosition)
            positions.forEach { position ->
                pathMap.putIfAbsent(position, counter++)
            }
            currentPosition = positions.last()
        }
        return pathMap
    }

    private fun getPathPositions(path: Path, currentPosition: Position): List<Position> {
        return when (path.direction) {
            Direction.R -> IntRange(1, path.length).map { currentPosition + Position(0, it) }
            Direction.L -> IntRange(1, path.length).map { currentPosition - Position(0, it) }
            Direction.U -> IntRange(1, path.length).map { currentPosition + Position(it, 0) }
            Direction.D -> IntRange(1, path.length).map { currentPosition - Position(it, 0) }
        }
    }

}

fun parseWireInput(wireInput: String): Wire {
    return wireInput
        .split(",")
        .map { it[0].toString() to it.substring(1) }
        .map { (directionValue, lengthValue) ->
            Path(Direction.fromString(directionValue), lengthValue.toInt())
        }
        .toWire()
}

data class Position(
    val x: Int,
    val y: Int
) {
    operator fun plus(other: Position): Position {
        return Position(x + other.x, y + other.y)
    }

    operator fun minus(other: Position): Position {
        return Position(x - other.x, y - other.y)
    }
}

data class Wire(
    val paths: List<Path>
)

fun List<Path>.toWire() = Wire(this)

data class Path(
    val direction: Direction,
    val length: Int
)

enum class Direction {
    R, L, U, D;

    companion object {
        fun fromString(value: String): Direction = values().first { it.name == value }
    }
}
