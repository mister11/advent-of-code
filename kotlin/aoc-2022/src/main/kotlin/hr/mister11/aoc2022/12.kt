package hr.mister11.aoc2022


fun main() {
    val input = readInput("12")

    println("Part 1: ${Solution12.part1(input)}")
    println("Part 2: ${Solution12.part2(input)}")
}

object Solution12 {
    fun part1(input: String): String {
        val area = parseArea(input)

        val startPosition = area.start
        return solve(startPosition, area).toString()
    }

    fun part2(input: String): String {
        val area = parseArea(input)

        return area
            .map
            .filterValues { it == 0 }
            .map { solve(it.key, area) }
            .min()
            .toString()
    }

    private fun solve(startPosition: Position, area: Area): Int {
        val endPosition = area.end

        val toVisit = ArrayDeque<Position>()
        toVisit.add(startPosition)

        val visited = mutableMapOf<Position, Int>()
        visited[startPosition] = 0
        while (toVisit.isNotEmpty()) {
            val currentPosition = toVisit.removeFirst()
            val neighbors = area.neighbors(currentPosition)
            val currentStep = visited.getOrDefault(currentPosition, 0)
            neighbors
                .forEach {
                    if (currentPosition == endPosition) {
                        return currentStep
                    }
                    if (it !in visited) {
                        visited[it] = currentStep + 1
                        toVisit.add(it)
                    }
                }
        }
        return Int.MAX_VALUE
    }

    private fun parseArea(input: String): Area {
        var startPosition = Position(0, 0)
        var endPosition = Position(0, 0)
        val map = input
            .lines()
            .flatMapIndexed { row, line ->
                line.toCharArray()
                    .mapIndexed { col, char ->
                        when (char) {
                            'S' -> {
                                startPosition = Position(row, col)
                                Position(row, col) to 0
                            }

                            'E' -> {
                                endPosition = Position(row, col)
                                Position(row, col) to 'z' - 'a'
                            }

                            else -> {
                                Position(row, col) to char - 'a'
                            }
                        }
                    }
            }
            .toMap()

        return Area(startPosition, endPosition, map)
    }
}

private fun Area.neighbors(position: Position): List<Position> {
    val steps = listOf(Position(-1, 0), Position(0, -1), Position(1, 0), Position(0, 1))
    val possiblePositions = this.map.keys;
    return steps
        .map { step -> position + step }
        .filter { positionCandidate -> positionCandidate in possiblePositions }
        .filter { positionCandidate -> this.map.getValue(positionCandidate) - this.map.getValue(position) <= 1 }

}

data class SearchNode(
    val position: Position,
    val parent: SearchNode? = null
)

data class Area(
    val start: Position,
    val end: Position,
    val map: Map<Position, Int>
)