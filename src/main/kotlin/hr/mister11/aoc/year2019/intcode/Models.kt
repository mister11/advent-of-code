package hr.mister11.aoc.year2019.intcode

import java.lang.IllegalStateException

interface Command {
    fun execute(values: MutableList<Int>)
    fun numOfArguments(): Int
}

class AddCommand(
    arg1Index: Int,
    arg2Index: Int,
    resultIndex: Int
) : BinaryCommand(arg1Index, arg2Index, resultIndex) {

    override fun binaryExecute(): (Int, Int) -> Int = { a, b -> a + b }
}

class MultiplyCommand(
    arg1Index: Int,
    arg2Index: Int,
    resultIndex: Int
) : BinaryCommand(arg1Index, arg2Index, resultIndex) {

    override fun binaryExecute(): (Int, Int) -> Int = { a, b -> a * b }
}

class HaltCommand : Command {
    override fun execute(values: MutableList<Int>) {
        throw IllegalStateException("HALT reached")
    }

    override fun numOfArguments() = 0
}

abstract class BinaryCommand(
    private val arg1Index: Int,
    private val arg2Index: Int,
    private val resultIndex: Int
) : Command {

    abstract fun binaryExecute(): (Int, Int) -> Int

    override fun execute(values: MutableList<Int>) {
        val result = binaryExecute()(values[arg1Index], values[arg2Index])
        values[resultIndex] = result
    }

    override fun numOfArguments() = 3
}

enum class OpCode(val value: Int) {
    ADD(1),
    MULTIPLY(2),
    HALT(99);

    companion object {
        fun fromValue(value: Int) = values().first { it.value == value }
    }
}
