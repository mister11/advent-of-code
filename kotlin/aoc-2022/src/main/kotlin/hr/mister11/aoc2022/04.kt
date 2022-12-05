package hr.mister11.aoc2022

fun main() {
    val input = readInput("04")
    
    println("Part 1: ${Solution04.part1(input)}")
    println("Part 2: ${Solution04.part2(input)}")
}


object Solution04 {
    fun part1(input: String): String {
        return input
            .trim()
            .lines()
            .map { line -> line.split(",") }
            .map { elfRanges ->
               val range1 = parseRange(elfRanges[0])
                val range2 = parseRange(elfRanges[1])
                Pair(range1, range2)
            }
            .count { (range1, range2) ->
                val range1Start = range1.first
                val range1End = range1.last
                val range2Start = range2.first
                val range2End = range2.last

                val firstInSeconds = range1Start in range2 && range1End in range2
                val secondInFirst = range2Start in range1 && range2End in range1

                firstInSeconds || secondInFirst
            }
            .toString()
    }

    fun part2(input: String): String {
        return input
            .trim()
            .lines()
            .map { line -> line.split(",") }
            .map { elfRanges ->
                val range1 = parseRange(elfRanges[0])
                val range2 = parseRange(elfRanges[1])
                Pair(range1, range2)
            }
            .count { (range1, range2) ->
                val range1Start = range1.first
                val range1End = range1.last
                val range2Start = range2.first
                val range2End = range2.last

                val firstInSeconds = range1Start in range2 || range1End in range2
                val secondInFirst = range2Start in range1 || range2End in range1

                firstInSeconds || secondInFirst
            }
            .toString()
    }

    private fun parseRange(range: String): IntRange {
        val tokens = range.split("-")
        val a = tokens[0].toInt()
        val b = tokens[1].toInt()
        return IntRange(a, b)
    }
}
