package hr.mister11.aoc2022

private val SAND_STARTING_POSITION = Position(500, 0)

fun main() {
    val input = readInput("14")

    println("Part 1: ${Solution14.part1(input)}")
    println("Part 2: ${Solution14.part2(input)}")
}

object Solution14 {
    fun part1(input: String): String {
        val caveSlice = parseInput(input)
        val maxY = caveSlice.maxBy { position -> position.y }.y
        var isInAbyss = false
        var count = 0

        while (!isInAbyss) {
            var currentPosition = SAND_STARTING_POSITION

            while (currentPosition.y <= maxY) {
                val isBottomTileRock = caveSlice.contains(currentPosition + Position(0, 1))
                val isLeftBottomTileRock = caveSlice.contains(currentPosition + Position(-1, 1))
                val isRightBottomTileRock = caveSlice.contains(currentPosition + Position(1, 1))

                currentPosition = if (!isBottomTileRock) {
                    currentPosition + Position(0, 1)
                } else if (!isLeftBottomTileRock) {
                    currentPosition + Position(-1, 1)
                } else if (!isRightBottomTileRock) {
                    currentPosition + Position(1, 1)
                } else {
                    caveSlice.add(currentPosition)
                    count++
                    break
                }
            }
            if (currentPosition.y > maxY) {
                isInAbyss = true
            }
        }

        return count.toString()
    }

    fun part2(input: String): String {
        val caveSlice = parseInput(input)
        val maxY = caveSlice.maxBy { position -> position.y }.y
        var didNotMove = false
        var count = 0

        while (!didNotMove) {
            var currentPosition = SAND_STARTING_POSITION

            while (currentPosition.y < maxY + 2) {
                val nextYIsFloor = currentPosition.y + 1 == maxY + 2
                val isBottomTileRock = nextYIsFloor || caveSlice.contains(currentPosition + Position(0, 1))
                val isLeftBottomTileRock = nextYIsFloor || caveSlice.contains(currentPosition + Position(-1, 1))
                val isRightBottomTileRock = nextYIsFloor || caveSlice.contains(currentPosition + Position(1, 1))

                currentPosition = if (!isBottomTileRock) {
                    currentPosition + Position(0, 1)
                } else if (!isLeftBottomTileRock) {
                    currentPosition + Position(-1, 1)
                } else if (!isRightBottomTileRock) {
                    currentPosition + Position(1, 1)
                } else {
                    caveSlice.add(currentPosition)
                    count++
                    break
                }
            }
            if (currentPosition == SAND_STARTING_POSITION) {
                didNotMove = true
            }
        }

        return count.toString()
    }

    private fun parseInput(input: String): MutableSet<Position> {
        val caveSlice = mutableSetOf<Position>()

        val rockPaths = input
            .lines()
            .map { line ->
                line.split(" -> ")
                    .map { position ->
                        val positionTokens = position.split(",")
                        Position(positionTokens[0].toInt(), positionTokens[1].toInt())
                    }
            }
            .map { rockPath -> rockPath.windowed(2) }

        rockPaths
            .forEach { rockPath ->
                rockPath.forEach { positionPair ->
                    val startPosition = positionPair[0]
                    val endPosition = positionPair[1]

                    val xRange = if (startPosition.x > endPosition.x) {
                        endPosition.x..startPosition.x
                    } else {
                        startPosition.x..endPosition.x
                    }
                    val yRange = if (startPosition.y > endPosition.y) {
                        endPosition.y..startPosition.y
                    } else {
                        startPosition.y..endPosition.y
                    }

                    for (x in xRange) {
                        for (y in yRange) {
                            caveSlice.add(Position(x, y))
                        }
                    }

                }
            }

        return caveSlice
    }
}