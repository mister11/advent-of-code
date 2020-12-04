package hr.mister11.aoc.year2019.intcode

import java.lang.IllegalStateException

interface Command {
    fun execute(values: MutableList<Int>)
    fun numOfArguments(): Int
}

class AddCommand(
    argument1: Argument,
    argument2: Argument,
    resultArgument: Argument
) : BinaryCommand(argument1, argument2, resultArgument) {

    override fun binaryExecute(): (Int, Int) -> Int = { a, b -> a + b }
}

class MultiplyCommand(
    argument1: Argument,
    argument2: Argument,
    resultArgument: Argument
) : BinaryCommand(argument1, argument2, resultArgument) {

    override fun binaryExecute(): (Int, Int) -> Int = { a, b -> a * b }
}

class HaltCommand : Command {
    override fun execute(values: MutableList<Int>) {
        throw IllegalStateException("HALT reached")
    }

    override fun numOfArguments() = 0
}

abstract class BinaryCommand(
    private val argument1: Argument,
    private val argument2: Argument,
    private val resultArgument: Argument
) : Command {

    abstract fun binaryExecute(): (Int, Int) -> Int

    override fun execute(values: MutableList<Int>) {
        val result = binaryExecute()(argument1.evaluate(values), argument2.evaluate(values))
        values[resultArgument.evaluate(values)] = result
    }

    override fun numOfArguments() = 3
}

data class Argument(
    val value: Int,
    val parameterMode: ParameterMode
) {
    fun evaluate(values: List<Int>): Int {
        return when (parameterMode) {
            ParameterMode.POSITIONAL -> values[value]
            ParameterMode.IMMEDIATE -> value
        }
    }
}

enum class OpCode(val value: Int) {
    ADD(1),
    MULTIPLY(2),
    HALT(99);

    companion object {
        fun fromValue(value: Int) = values().first { it.value == value }
    }
}

enum class ParameterMode(val value: Int) {
    POSITIONAL(0),
    IMMEDIATE(1);

    companion object {
        fun fromValue(value: Int) = values().first { it.value == value }
    }
}
