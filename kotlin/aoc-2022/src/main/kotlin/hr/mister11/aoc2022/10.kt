package hr.mister11.aoc2022

fun main() {
    val input = readInput("10")

    println("Part 1: ${Solution10.part1(input)}")
    println("Part 2: \n${Solution10.part2(input)}")
}

private const val CYCLE_CRT_LENGTH = 40
private val OBSERVABLE_CYCLES = listOf(20, 60, 100, 140, 180, 220)

object Solution10 {
    fun part1(input: String): String {
        var registerValue = 1
        var cycle = 0
        return parseInput(input.lines())
            .flatMap { instruction ->
                cycle++
                when (instruction) {
                    is AddInstruction -> {
                        val oldValue = registerValue
                        registerValue += instruction.value
                        listOf(
                            Cycle(cycle, oldValue, oldValue, oldValue),
                            Cycle(++cycle, oldValue, oldValue, registerValue)
                        )
                    }
                    NoopInstruction -> {
                        listOf(
                            Cycle(cycle, registerValue, registerValue, registerValue)
                        )
                    }
                }
            }
            .filter { cycle -> cycle.index in OBSERVABLE_CYCLES }
            .sumOf { cycle -> cycle.during * cycle.index }
            .toString()

    }

    fun part2(input: String): String {
        var registerValue = 1
        var cycle = 0
        val cycles = parseInput(input.lines())
            .flatMap { instruction ->
                cycle++
                when (instruction) {
                    is AddInstruction -> {
                        val oldValue = registerValue
                        registerValue += instruction.value
                        listOf(
                            Cycle(cycle, oldValue, oldValue, oldValue),
                            Cycle(++cycle, oldValue, oldValue, registerValue)
                        )
                    }
                    NoopInstruction -> {
                        listOf(
                            Cycle(cycle, registerValue, registerValue, registerValue)
                        )
                    }
                }
            }

        return cycles.chunked(CYCLE_CRT_LENGTH)
            .map {cycles ->
                drawCycleLine(cycles)
            }
            .joinToString(separator = "\n")
    }

    private fun drawCycleLine(cycles: List<Cycle>): String {
        return cycles.mapIndexed { index, cycle ->
            val cycleValue = cycle.during
            val cycleIndices: List<Int> = listOf(cycleValue - 1, cycleValue, cycleValue + 1)

            if (index in cycleIndices) {
                '#'
            } else {
                '.'
            }
        }.joinToString(separator = "")
    }

    private fun parseInput(lines: List<String>): List<Instruction> {
        return lines
            .map { line -> line.split("\\s+".toRegex()) }
            .map { tokens ->
                when (tokens[0]) {
                    "addx" -> AddInstruction(tokens[1].toInt())
                    "noop" -> NoopInstruction
                    else -> throw IllegalArgumentException("Unknown instruction")
                }
            }
    }
}

sealed interface Instruction

object NoopInstruction : Instruction
data class AddInstruction(val value: Int) : Instruction


data class Cycle(val index: Int, val before: Int, val during: Int, val after: Int)