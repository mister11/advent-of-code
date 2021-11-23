package hr.mister11.aoc.year2020.day18

import hr.mister11.aoc.Resources
import java.util.Stack
import kotlin.system.exitProcess

fun main() {

    val solution = Solution(Resources.readFileAsList(2020, 18))
    println("Part 1: ${solution.part1()}")
    println("Part 2: ${solution.part2()}")
}

class Solution(private val expressions: List<String>) {

    private val noPrecedence = { _: String, b: String -> b != "(" && b != ")" }

    private val advancedPrecedence = { a: String, b: String ->
        if (b == "(" || b == ")") {
            false
        } else {
            a == "*" && b == "+"
        }
    }

    fun part1() = expressions.sumOf { evaluateExpression(it, noPrecedence) }

    fun part2() = expressions.sumOf { evaluateExpression(it, advancedPrecedence) }

    private fun evaluateExpression(
        expression: String,
        precedence: (String, String) -> Boolean
    ): Long {
        val values = Stack<Long>()
        val operators = Stack<String>()

        expression.split("")
            .filter { it.trim().isNotEmpty() }
            .forEach { token ->
                val number = token.toLongOrNull()
                if (number != null) {
                    values.push(number)
                } else {
                    when (token) {
                        "(" -> {
                            operators.push(token)
                        }
                        ")" -> {
                            var operator = operators.pop()
                            while (operator != "(") {
                                val a = values.pop()
                                val b = values.pop()
                                when (operator) {
                                    "+" -> {
                                        values.push(a + b)
                                    }
                                    "*" -> {
                                        values.push(a * b)
                                    }
                                    else -> {
                                        println("SHOULD NOT HAPPEN")
                                        exitProcess(-1);
                                    }
                                }
                                operator = operators.pop()
                            }
                        }
                        else -> {
                            while (operators.isNotEmpty() && precedence(token, operators.peek())) {
                                val a = values.pop()
                                val b = values.pop()
                                when (operators.pop()) {
                                    "+" -> {
                                        values.push(a + b)
                                    }
                                    "*" -> {
                                        values.push(a * b)
                                    }
                                    else -> {
                                        println("SHOULD NOT HAPPEN")
                                        exitProcess(-1);
                                    }
                                }
                            }
                            operators.push(token)
                        }
                    }
                }
            }
        while (operators.isNotEmpty()) {
            val a = values.pop()
            val b = values.pop()
            when (operators.pop()) {
                "+" -> {
                    values.push(a + b)
                }
                "*" -> {
                    values.push(a * b)
                }
                else -> {
                    println("SHOULD NOT HAPPEN")
                    exitProcess(-1);
                }
            }
        }
        if (values.size != 1) {
            println("SHOULD NOT HAPPEN")
            exitProcess(-1);
        }
        return values.pop()
    }
}
