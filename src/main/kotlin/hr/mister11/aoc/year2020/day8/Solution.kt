package hr.mister11.aoc.year2020.day8

import hr.mister11.aoc.Resources
import kotlin.RuntimeException

fun main() {
    val solution = Solution(Resources.readFileAsList(2020, 8))
    println("Part 1: ${solution.part1()}")
    println("Part 2: ${solution.part2()}")
}

class Solution(
    rawCommands: List<String>
) {

    private val commandSplitRegex = "\\s+".toRegex()
    private val commands = parseCommands(rawCommands)

    fun part1(): Int {
        return executeBoot(commands)
    }

    fun part2(): Int {
        val map = generateAllBoots()
            .map { boot ->
                runCatching {
                    executeBoot(boot, ignoreLoop = false)
                }
            }
        return map
            .find { it.isSuccess }
            ?.getOrThrow() ?: throw IllegalStateException("Part 2 result not found")
    }

    private fun executeBoot(commands: List<Command>, ignoreLoop: Boolean = true): Int {
        var index = 0
        var accumulator = 0

        val executedCommands = mutableSetOf<Int>()
        while (true) {
            if (!ignoreLoop && index >= commands.size) {
                return accumulator
            }
            if (executedCommands.contains(index)) {
                if (ignoreLoop) {
                    return accumulator
                } else {
                    throw RuntimeException("Loop")
                }
            } else {
                executedCommands.add(index)
            }
            when (val command = commands[index]) {
                is Command.Nop -> index++
                is Command.Jmp -> index += command.arg
                is Command.Acc -> {
                    accumulator += command.arg
                    index++
                }
            }
        }
    }

    private fun generateAllBoots(): List<List<Command>> {
        val boots = mutableListOf<List<Command>>()
        boots.add(commands)
        commands.forEachIndexed { index, command ->
            when (command) {
                is Command.Nop -> {
                    val commandsCopy = ArrayList(commands)
                    commandsCopy[index] = Command.Jmp(command.arg)
                    boots.add(commandsCopy)
                }
                is Command.Jmp -> {
                    val commandsCopy = ArrayList(commands)
                    commandsCopy[index] = Command.Nop(command.arg)
                    boots.add(commandsCopy)
                }
                is Command.Acc -> {}
            }
        }
        return boots
    }

    private fun parseCommands(rawCommands: List<String>): List<Command> {
        return rawCommands.map { rawCommand ->
            val tokens = rawCommand.split(commandSplitRegex)
            val command = tokens[0]
            val arg = tokens[1].toInt()

            when (command) {
                "nop" -> Command.Nop(arg)
                "acc" -> Command.Acc(arg)
                "jmp" -> Command.Jmp(arg)
                else -> throw IllegalArgumentException("Unknown command $command")
            }
        }
    }
}

sealed class Command {
    data class Nop(val arg: Int) : Command()
    data class Jmp(val arg: Int) : Command()
    data class Acc(val arg: Int) : Command()
}