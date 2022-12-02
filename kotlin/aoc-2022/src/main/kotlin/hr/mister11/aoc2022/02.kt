package hr.mister11.aoc2022

fun main() {
    val input = readInput("02")

    println("Part 1: ${Solution02.part1(input)}")
    println("Part 2: ${Solution02.part2(input)}")
}

private val opponentMovesMap = mapOf(
    "A" to HandType.ROCK,
    "B" to HandType.PAPER,
    "C" to HandType.SCISSORS
)

private val ourMovesMap = mapOf(
    "X" to HandType.ROCK,
    "Y" to HandType.PAPER,
    "Z" to HandType.SCISSORS
)

private val roundOutcomeMap = mapOf(
    "X" to RoundOutcome.LOST,
    "Y" to RoundOutcome.DRAW,
    "Z" to RoundOutcome.WIN
)

object Solution02 {
    fun part1(input: String): String {
        return input
            .lines()
            .map { line ->
                val token = line.split("\\s+".toRegex())
                Pair(token[0], token[1])
            }
            .sumOf { moves -> scoreRound(moves) }
            .toString()
    }

    private fun scoreRound(moves: Pair<String, String>): Int {
        val (opponentMoveKey, ourMoveKey) = moves
        val opponentHand = opponentMovesMap.getValue(opponentMoveKey)
        val ourHand = ourMovesMap.getValue(ourMoveKey)

        val roundOutcome = when {
            ourHand == opponentHand -> RoundOutcome.DRAW
            ourHand == HandType.ROCK && opponentHand == HandType.SCISSORS -> RoundOutcome.WIN
            ourHand == HandType.SCISSORS && opponentHand == HandType.PAPER -> RoundOutcome.WIN
            ourHand == HandType.PAPER && opponentHand == HandType.ROCK -> RoundOutcome.WIN
            else -> RoundOutcome.LOST
        }

        return roundOutcome.score + ourHand.score
    }

    // let's not use things from part 1
    fun part2(input: String): String {
        return input
            .lines()
            .map { line ->
                val token = line.split("\\s+".toRegex())
                Pair(token[0], token[1])
            }
            .sumOf { moves -> playRound(moves) }
            .toString()
    }

    private fun playRound(lineTokens: Pair<String, String>): Int {
        val (opponentMoveKey, roundOutcomeKey) = lineTokens
        val opponentHand = opponentMovesMap.getValue(opponentMoveKey)

        return when (val roundOutcome = roundOutcomeMap.getValue(roundOutcomeKey)) {
            RoundOutcome.DRAW -> roundOutcome.score + opponentHand.score
            RoundOutcome.LOST -> roundOutcome.score + losingHand(opponentHand).score
            RoundOutcome.WIN -> roundOutcome.score + winningHand(opponentHand).score
        }
    }

    private fun losingHand(hand: HandType): HandType {
        return when (hand) {
            HandType.ROCK -> HandType.SCISSORS
            HandType.PAPER -> HandType.ROCK
            HandType.SCISSORS -> HandType.PAPER
        }
    }

    private fun winningHand(hand: HandType): HandType {
        return when (hand) {
            HandType.ROCK -> HandType.PAPER
            HandType.PAPER -> HandType.SCISSORS
            HandType.SCISSORS -> HandType.ROCK
        }
    }
}

enum class HandType(val score: Int) {
    ROCK(1), PAPER(2), SCISSORS(3)
}

enum class RoundOutcome(val score: Int) {
    LOST(0), DRAW(3), WIN(6)
}
