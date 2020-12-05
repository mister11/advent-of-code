package hr.mister11.aoc.year2019.intcode

import java.lang.IllegalStateException

interface Command {
    fun execute(intcode: Intcode)
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

class InputCommand(
    private val value: Int,
    private val resultArgument: Argument
): Command {
    override fun execute(intcode: Intcode) {
        val values = intcode.intcodeValues
        values[resultArgument.evaluate(values)] = value
        intcode.index += 2
    }
}

class OutputCommand(
    private val resultArgument: Argument
): Command {
    override fun execute(intcode: Intcode) {
        val values = intcode.intcodeValues
        intcode.addOutput(values[resultArgument.evaluate(values)])
        intcode.index += 2
    }
}

class JumpIfTrueCommand(
    private val argument1: Argument,
    private val argument2: Argument
): Command {
    override fun execute(intcode: Intcode) {
        val values = intcode.intcodeValues
        if (argument1.evaluate(values) != 0) {
            intcode.index = argument2.evaluate(values)
        } else {
            intcode.index += 3
        }
    }
}

class JumpIfFalseCommand(
    private val argument1: Argument,
    private val argument2: Argument
): Command {
    override fun execute(intcode: Intcode) {
        val values = intcode.intcodeValues
        if (argument1.evaluate(values) == 0) {
            intcode.index = argument2.evaluate(values)
        } else {
            intcode.index += 3
        }
    }
}

class LessThanCommand(
    private val argument1: Argument,
    private val argument2: Argument,
    private val resultArgument: Argument
): Command {
    override fun execute(intcode: Intcode) {
        val values = intcode.intcodeValues
        if (argument1.evaluate(values) < argument2.evaluate(values)) {
            values[resultArgument.evaluate(values)] = 1
        } else {
            values[resultArgument.evaluate(values)] = 0
        }
        intcode.index += 4
    }
}

class EqualsCommand(
    private val argument1: Argument,
    private val argument2: Argument,
    private val resultArgument: Argument
): Command {
    override fun execute(intcode: Intcode) {
        val values = intcode.intcodeValues
        if (argument1.evaluate(values) == argument2.evaluate(values)) {
            values[resultArgument.evaluate(values)] = 1
        } else {
            values[resultArgument.evaluate(values)] = 0
        }
        intcode.index += 4
    }
}

class HaltCommand : Command {
    override fun execute(intcode: Intcode) {
        throw IllegalStateException("HALT reached")
    }
}

abstract class BinaryCommand(
    private val argument1: Argument,
    private val argument2: Argument,
    private val resultArgument: Argument
) : Command {

    abstract fun binaryExecute(): (Int, Int) -> Int

    override fun execute(intcode: Intcode) {
        val values = intcode.intcodeValues
        val result = binaryExecute()(argument1.evaluate(values), argument2.evaluate(values))
        values[resultArgument.evaluate(values)] = result
        intcode.index += 4
    }
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
    INPUT(3),
    OUTPUT(4),
    JUMP_IF_TRUE(5),
    JUMP_IF_FALSE(6),
    LESS_THAN(7),
    EQUALS(8),
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
