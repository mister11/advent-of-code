package hr.mister11.aoc2022

import kotlin.math.abs

fun main() {
    val input = readInput("08")

    println("Part 1: ${Solution08.part1(input)}")
    println("Part 2: ${Solution08.part2(input)}")
}

object Solution08 {
    fun part1(input: String): String {
        val lines = input.lines()
        val trees = parseInput(lines)

        val maxRow = lines.size
        val maxCol = lines[0].length

        val interiorTrees = trees
            .filter { (position, _) ->
                val (x, y) = position
                x >= 1 && y >= 1 && x < maxRow - 1 && y < maxCol - 1
            }

        val outerTreesCount = 2 * maxRow + 2 * (maxCol - 2)
        val innerTreesCount = interiorTrees
            .entries
            .count { (position, tree) ->
                val neighbors = neighbors(position, maxRow, maxCol)

                neighbors
                    // for any positions top, bottom, left, right...
                    .any { sidePositions ->
                        // ... all trees have to be lower than current tree
                        sidePositions.all { sidePosition -> trees.getValue(sidePosition) < tree }
                    }

            }

        return (outerTreesCount + innerTreesCount).toString()
    }

    fun part2(input: String): String {
        val lines = input.lines()
        val trees = parseInput(lines)

        val maxRow = lines.size
        val maxCol = lines[0].length

        val interiorTrees = trees
            .filter { (position, _) ->
                val (x, y) = position
                x >= 1 && y >= 1 && x < maxRow - 1 && y < maxCol - 1
            }

        return interiorTrees
            .entries
            .maxOfOrNull { (position, tree) ->
                val neighbors = neighbors(position, maxRow, maxCol)

                neighbors
                    .map { sidePositions ->
                        val a = sidePositions
                            .find { position -> trees.getValue(position) >= tree }
                            ?: sidePositions.last()
                        abs(a.first - position.first) + abs(a.second - position.second)
                    }
                    .reduce { acc, i -> acc * i }

            }
            .toString()

    }

    private fun neighbors(position: Pair<Int, Int>, maxRow: Int, maxCol: Int): List<List<Pair<Int, Int>>> {
        val positions = mutableListOf<List<Pair<Int, Int>>>()
        val (x, y) = position

        // left
        var xTemp = x
        val leftPositions = mutableListOf<Pair<Int, Int>>()
        while (xTemp-- > 0) {
            leftPositions.add((xTemp to y))
        }
        positions.add(leftPositions)

        // right
        xTemp = x
        val rightPositions = mutableListOf<Pair<Int, Int>>()
        while (xTemp++ < maxRow - 1) {
            rightPositions.add((xTemp to y))
        }
        positions.add(rightPositions)

        //top
        var yTemp = y
        val topPositions = mutableListOf<Pair<Int, Int>>()
        while (yTemp-- > 0) {
            topPositions.add(x to yTemp)
        }
        positions.add(topPositions)

        //bottom
        yTemp = y
        val bottomPositions = mutableListOf<Pair<Int, Int>>()
        while (yTemp++ < maxCol - 1) {
            bottomPositions.add(x to yTemp)
        }
        positions.add(bottomPositions)

        return positions
    }

    private fun parseInput(lines: List<String>): Map<Pair<Int, Int>, Int> {
        return lines
            .flatMapIndexed { rowIndex: Int, line: String ->
                line
                    .toCharArray()
                    .mapIndexed { columnIndex, tree ->
                        (rowIndex to columnIndex) to tree.digitToInt()
                    }
            }
            .toMap()
    }
}