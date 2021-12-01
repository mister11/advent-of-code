package hr.mister11.aoc.year2020.day19

import hr.mister11.aoc.Resources

fun main() {

    val solution = Solution(Resources.readFileAsList(2020, 19))
    println("Part 1: ${solution.part1()}")
    println("Part 2: ${solution.part2()}")
}

class Solution(input: List<String>) {

    private val rules = parseInput(input)

    fun part1(): Long {
        val (leafRules, combinedRules) = rules

        println(leafRules)
        println(combinedRules)

        for (ruleId in combinedRules[0]?.first().orEmpty()) {

        }
        return 0
    }

    fun part2() = 0

    private fun parseInput(input: List<String>): Pair<Map<Int, String>, Map<Int, List<List<Int>>>> {
        val leafRule = "(\\d+): \"(\\w)\"".toRegex()
        val combinedRule = "(\\d+): (.*)".toRegex()

        val leafRules = hashMapOf<Int, String>()
        val combinedRules = hashMapOf<Int, MutableList<List<Int>>>()
        input.forEach {
            val leafGroups = leafRule.findAll(it).flatMap { it.groupValues.drop(1) }.toList()
            if (leafGroups.isNotEmpty()) {
                leafRules[leafGroups[0].toInt()] = leafGroups[1]
            } else {
                val combined = combinedRule.findAll(it).flatMap { it.groupValues.drop(1) }.toList()
                val ruleId = combined[0].toInt()
                val combinations = combined[1].split(" | ")
                combinations.forEach { combination ->
                    combinedRules.computeIfAbsent(ruleId) { mutableListOf() }
                        .add(combination.split(" ").map { it.toInt() })
                }
            }
        }
        return Pair(leafRules, combinedRules)
    }
}
