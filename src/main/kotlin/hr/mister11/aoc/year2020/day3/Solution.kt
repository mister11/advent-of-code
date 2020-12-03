package hr.mister11.aoc.year2020.day3

import hr.mister11.aoc.Resources

fun main() {
    val solution = Solution(Resources.readFileAsList(2020, 3))
    println("Part 1: ${solution.part1(Pair(1, 3))}")
    println("Part 2: ${solution.part2()}")
}

class Solution(
    mapRaw: List<String>
) {

    private val map: List<List<TileType>> = parseMap(mapRaw)

    fun part1(slope: Pair<Int, Int>): Int {
        val columns = map[0].size
        return generateSequence(Pair(0, 0)) { Pair(it.first + slope.first, it.second + slope.second) }
            .takeWhile { it.first < map.size }
            .count { map[it.first][it.second % columns] == TileType.TREE }
    }

    fun part2(): Long {
        val slopes = listOf(Pair(1, 1), Pair(1, 3), Pair(1, 5), Pair(1, 7), Pair(2, 1))
        return slopes.map { part1(it).toLong() }.reduce { acc, value -> acc * value }
    }

    private fun parseMap(mapRaw: List<String>): List<List<TileType>> {
        return mapRaw.map { mapRowRaw ->
            mapRowRaw.map {
                if (it == '.') {
                    TileType.SQUARE
                } else {
                    TileType.TREE
                }
            }
        }
    }

}

enum class TileType {
    SQUARE, TREE
}