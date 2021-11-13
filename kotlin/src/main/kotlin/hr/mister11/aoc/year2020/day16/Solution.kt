package hr.mister11.aoc.year2020.day16

import hr.mister11.aoc.Resources

fun main() {

    val solution = Solution(Resources.readFileRaw(2020, 16))
    println("Part 1: ${solution.part1()}")
    println("Part 2: ${solution.part2()}")
}

class Solution(input: String) {

    private val inputData = parseInput(input)

    fun part1(): Int {
        val (rules, _, nearbyTickets) = inputData
        val allRanges = rules.values.flatten()

        // this processing, although similar to part2, is written so that it makes
        // more sense if looked as a separate task
        return nearbyTickets.flatMap { it.values }
            .filter { value -> notInAnyRange(value, allRanges) }
            .sum()
    }

    fun part2(): Long {
        val (rules, myTicket, nearbyTickets) = inputData
        val allRanges = rules.values.flatten()

        val ticketsMatrix = nearbyTickets
            .filter { ticket -> isValidTicket(ticket, allRanges) }
            .plus(myTicket)
            .map { ticket -> ticket.values }

        val mutableRules = rules.toMutableMap()
        val fieldsMatrix = ticketsMatrix[0]
            .indices
            // swap rows and columns making rows fields that have to be checked
            .map { index -> ticketsMatrix.map { row -> row[index] } }

        val indexToName = mutableMapOf<Int, String>()
        while (mutableRules.isNotEmpty()) {
            val (index, type) = determineIndexType(fieldsMatrix, mutableRules)
            mutableRules.remove(type)
            indexToName[index] = type
        }

        return indexToName
            .filterValues { it.startsWith("departure") }
            .keys
            .map { myTicket.values[it] }
            .fold(1L) { acc, value -> acc * value }
    }

    private fun notInAnyRange(value: Int, allRanges: List<IntRange>): Boolean {
        return allRanges
            .any { range -> value in range }
            .not()
    }

    private fun isValidTicket(ticket: Ticket, allRanges: List<IntRange>): Boolean {
        // all ticket values have to be in at least one of the ranges
        return ticket.values.all { value ->
            allRanges.any { range -> value in range }
        }
    }

    private fun determineIndexType(
        fieldsMatrix: List<List<Int>>,
        rules: Map<String, List<IntRange>>
    ): Pair<Int, String> {
        var ruleName = ""
        val index = fieldsMatrix.indexOfFirst { fields ->
            val applicableRules = rules
                .filter { (_, ranges) ->
                    fields.all { value -> ranges.any { range -> value in range } }
                }
            // this can be 0 since we iterate over and over through matrix
            // it's not the most efficient thing, but matrix size allows me to do it
            if (applicableRules.size == 1) {
                ruleName = applicableRules.keys.first()
            }
            applicableRules.size == 1
        }
        return index to ruleName
    }

    private fun parseInput(input: String): InputData {
        val inputChunks = input.split("\n\n")
        return InputData(
            rules = parseRules(inputChunks[0]),
            myTicket = parseMyTicket(inputChunks[1]),
            nearbyTickets = parseNearbyTickets(inputChunks[2])
        )
    }

    private fun parseRules(rulesText: String): Map<String, List<IntRange>> {
        val rulesRegex = "(\\D+): (\\d+)-(\\d+) or (\\d+)-(\\d+)".toRegex()
        return rulesText.split("\n")
            .associate { ruleLine ->
                val groups = rulesRegex.matchEntire(ruleLine)?.groups?.drop(1).orEmpty()
                val name = groups.first()?.value.orEmpty()
                name to groups
                    .drop(1) // remove name
                    .chunked(2)
                    .map { (rangeStart, rangeEnd) ->
                        IntRange(
                            rangeStart?.value?.toIntOrNull() ?: 0,
                            rangeEnd?.value?.toIntOrNull() ?: 0
                        )
                    }
            }
    }

    private fun parseMyTicket(myTicketText: String): Ticket {
        return myTicketText.split("\n")
            .drop(1)
            .map { ticketLine -> Ticket(ticketLine.split(",").map { it.toInt() }) }
            .first()
    }

    private fun parseNearbyTickets(nearbyTicketText: String): List<Ticket> {
        return nearbyTicketText.split("\n")
            .drop(1)
            .map { ticketLine -> Ticket(ticketLine.split(",").map { it.toInt() }) }
    }
}

data class InputData(
    val rules: Map<String, List<IntRange>>,
    val myTicket: Ticket,
    val nearbyTickets: List<Ticket>
)

data class Ticket(
    val values: List<Int>
)
