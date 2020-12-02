package hr.mister11.aoc.year2020.day2

import hr.mister11.aoc.Resources

fun main() {
    val solution = Solution(Resources.readFileAsList(2020, 2))
    println("Part 1: ${solution.part1()}")
    println("Part 2: ${solution.part2()}")
}

class Solution(
    policyLines: List<String>
) {

    private val policiesWithPassword = policyLines.map { parsePolicy(it) }

    fun part1(): Int {
        return policiesWithPassword
            .filter { (policy, password) -> isPasswordValidAsOccurrences(password, policy) }
            .count()
    }

    private fun isPasswordValidAsOccurrences(password: String, policy: Policy): Boolean {
        val occurrences = password.filter { it.toString() == policy.value }.length
        return occurrences >= policy.firstNumber && occurrences <= policy.secondNumber
    }

    fun part2(): Int {
        return policiesWithPassword
            .filter { (policy, password) -> isPasswordValidAsPositions(password, policy) }
            .count()
    }

    private fun isPasswordValidAsPositions(password: String, policy: Policy): Boolean {
        val policyValue = policy.value
        val passwordCharAt = { index: Int -> password[index - 1].toString() }
        return (passwordCharAt(policy.firstNumber) == policyValue)
            .xor(passwordCharAt(policy.secondNumber) == policyValue)
    }

    private fun parsePolicy(policyLine: String): Pair<Policy, String> {
        val policyPasswordRegex = "(\\d+)-(\\d+) (\\w): (\\w+)".toRegex()
        val groups = policyPasswordRegex.matchEntire(policyLine)?.groupValues.orEmpty()
        if (groups.isEmpty()) {
            throw IllegalStateException("Either policy parsing or input format is bugged.")
        }
        return Pair(
            Policy(firstNumber = groups[1].toInt(), secondNumber = groups[2].toInt(), value = groups[3]),
            groups[4]
        )
    }

    /*
    1-3 a: abcde
    1-3 b: cdefg
    2-9 c: ccccccccc
     */
}

data class Policy(
    val firstNumber: Int,
    val secondNumber: Int,
    val value: String,
)