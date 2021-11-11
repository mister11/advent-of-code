package hr.mister11.aoc.year2020.day7

import hr.mister11.aoc.Resources

fun main() {
    val solution = Solution(Resources.readFileAsList(2020, 7))
    println("Part 1: ${solution.part1()}")
    println("Part 2: ${solution.part2()}")
}

class Solution(
    bagRulesRaw: List<String>
) {

    private val bagRules = parseRawBagRules(bagRulesRaw)

    fun part1(): Int {
        return bagRules.keys.map { key ->
            var neighbors = bagRules[key].orEmpty()
            while (neighbors.isNotEmpty()) {
                if (neighbors.find { it.contains("shiny gold") } != null) {
                    return@map 1
                }
                neighbors = neighbors
                    .map { it.split("\\s+".toRegex()).subList(1, 3).joinToString(" ") }
                    .flatMap { bagRules[it].orEmpty() }
            }
            return@map 0
        }.sum()
    }

    fun part2(): Int {
        val neighbors = bagRules["shiny gold"].orEmpty()
        return bagCollector(neighbors)
    }

    private fun bagCollector(bags: List<String>): Int {
        if (bags.isEmpty()) {
            return 0
        }
        return bags.map { bag ->
            val tokens = bag.split("\\s+".toRegex())
            val quantity = tokens[0].toInt()
            val nextBag = tokens.subList(1, 3).joinToString(" ")
            quantity + quantity * bagCollector(bagRules[nextBag].orEmpty())
        }.sum()
    }

    private fun parseRawBagRules(bagRulesRaw: List<String>): Map<String, List<String>> {
        return bagRulesRaw.map { bagRuleRaw ->
            val ruleTokens = bagRuleRaw.split("contain")
            val keyBag = ruleTokens[0].trim().split("\\s+".toRegex()).subList(0, 2).joinToString(" ")
            keyBag to getBagValues(ruleTokens[1].trim())
        }.toMap()
    }

    private fun getBagValues(bagValuesRaw: String): List<String> {
        return if (bagValuesRaw == "no other bags.") {
            emptyList()
        } else {
            bagValuesRaw.split(",")
                .map { it.trim().split("\\s+".toRegex()).subList(0, 3).joinToString(" ") }
        }
    }
}
