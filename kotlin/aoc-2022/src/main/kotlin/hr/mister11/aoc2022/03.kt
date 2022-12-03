package hr.mister11.aoc2022

fun main() {
    val input = readInput("03")

    println("Part 1: ${Solution03.part1(input)}")
    println("Part 2: ${Solution03.part2(input)}")
}

object Solution03 {
    fun part1(input: String): String {
        return input
            .lines()
            .asSequence()
            .map { line -> line.chunked(line.length / 2) }
            .map { lineChunks -> Pair(lineChunks[0].toSet(), lineChunks[1].toSet()) }
            .map { (charSet1, charSet2) -> charSet1.intersect(charSet2).single() }
            .sumOf(::letterPriority)
            .toString()
    }

    fun part2(input: String): String {
        return input
            .lines()
            .asSequence()
            .chunked(3)
            .map { group -> group.map { backpack -> backpack.toSet() } }
            .map { groupsAsChars -> groupsAsChars.intersectAll().single() }
            .sumOf(::letterPriority)
            .toString()
    }

    private fun letterPriority(letter: Char): Int {
        return if (letter - 'a' > 0) {
            letter - 'a' + 1
        } else {
            letter - 'A' + 1 + 26
        }
    }

    private fun <T> List<Set<T>>.intersectAll(): Set<T> {
        return this.reduce { acc, item -> acc.intersect(item) }
    }
}
