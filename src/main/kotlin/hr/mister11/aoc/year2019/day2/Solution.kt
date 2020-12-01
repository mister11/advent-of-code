package hr.mister11.aoc.year2019.day2

import hr.mister11.aoc.Resources
import hr.mister11.aoc.year2019.intcode.Intcode

fun main() {
    val solution = Solution(Resources.readFileRaw(2019, 2))
    println("Part 1: ${solution.part1()}")
    println("Part 2: ${solution.part2()}")
}

class Solution(private val intcodeInput: String) {

    fun part1(): Int {
        val intcode = Intcode(intcodeInput.split(",").map { it.toInt() }.toMutableList())
        intcode.restore1202ProgramAlarm()
        runCatching { intcode.execute() } // nicer try-catch with empty catch clause
        return intcode.getValueAt(0)
    }

    fun part2(): Int {
        val range = IntRange(0, 99)
        range.forEach { input1 ->
            range.forEach { input2 ->
                val intcode = Intcode(intcodeInput.split(",").map { it.toInt() }.toMutableList())
                intcode.setInputs(input1, input2)
                runCatching { intcode.execute() }
                if (intcode.getValueAt(0) == 19690720) {
                    return 100 * input1 + input2
                }
            }
        }
        throw RuntimeException("Unable to find inputs matching defined output")
    }
}

