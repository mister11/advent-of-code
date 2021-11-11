package hr.mister11.aoc.year2020.day6

import hr.mister11.aoc.Resources

fun main() {
    val solution = Solution(Resources.readFileRaw(2020, 6))
    println("Part 1: ${solution.part1()}")
    println("Part 2: ${solution.part2()}")
}

class Solution(
    rawGroupAnswers: String
) {

    private val groupAnswers = parseRawGroupAnswers(rawGroupAnswers)

    fun part1() = groupAnswers.sumBy { groupAnswer ->
        groupAnswer.peopleAnswers
            .flatMap { personAnswers -> personAnswers.questions }
            .toSet()
            .size
    }

    fun part2(): Int {
        return groupAnswers.sumBy { groupAnswer ->
            groupAnswer.peopleAnswers
                .map { it.questions }
                .reduce { acc, list -> acc.intersect(list).toList() }
                .size
        }
    }

    private fun parseRawGroupAnswers(rawGroupAnswers: String): List<GroupAnswer> {
        return rawGroupAnswers.split("\\n\\n".toRegex())
            .map { groupAnswer ->
                GroupAnswer(
                    peopleAnswers = groupAnswer.split("\\n".toRegex())
                        .map { personAnswer -> PersonAnswer(personAnswer.toCharArray().toList()) }
                )
            }
    }
}

data class GroupAnswer(
    val peopleAnswers: List<PersonAnswer>
)

data class PersonAnswer(
    val questions: List<Char>
)
