package hr.mister11.aoc2022

fun main() {
    val input = readInput("06")
    
    println("Part 1: ${Solution06.part1(input)}")
    println("Part 2: ${Solution06.part2(input)}")
}


object Solution06 {
    fun part1(input: String): String {
        return (input
            .windowed(4)
            .indexOfFirst { it.length == it.toSet().size } + 4).toString()
    }

    fun part2(input: String): String {
        return (input
            .windowed(14)
            .indexOfFirst { it.length == it.toSet().size } + 14).toString()
    }
}
