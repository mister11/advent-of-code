package hr.mister11.aoc.year2020.day14

import hr.mister11.aoc.Resources
import java.util.BitSet

fun main() {

    val bs = BitSet(4)

//    bs.set(3)
    bs.set(4)
//    bs.set(5)
//    println(bs.toLongArray().firstOrNull())
//    println(Long.MAX_VALUE)

    val solution = Solution(Resources.readFileAsList(2020, 14))
    println("Part 1: ${solution.part1()}")
    println("Part 2: ${solution.part2()}")
}

class Solution(
    private val inputRaw: List<String>
) {

    private val valuesAssignRegex = "mem\\[(\\d+)] = (\\d+)".toRegex()

    private lateinit var mask: String

    fun part1(): Long {
        val memory = mutableMapOf<Long, Long>()
        inputRaw.forEach { inputLine ->
            if (inputLine.startsWith("mask")) {
                mask = inputLine.split("=")[1].trim()
            } else {
                val groups = valuesAssignRegex.matchEntire(inputLine)?.groupValues.orEmpty()
                val address = groups[1].toLong()
                val value = groups[2].toLong()
                memory[address] = calculateValue(value, mask)
            }
        }
        return memory.values.sum()
    }

    private fun calculateValue(value: Long, mask: String): Long {
        val valueAsBinary = value.toString(2)
        var valueIndex = valueAsBinary.length - 1
        val maskLength = mask.length
        var maskIndex = maskLength - 1
        val finalValueBitset = BitSet(maskLength)
        while (maskIndex >= 0) {
            val valueAtIndex = valueAsBinary.getOrElse(valueIndex) { '0' } == '1'
            val finalValue = if (mask[maskIndex] == 'X') {
                valueAtIndex
            } else {
                mask[maskIndex] == '1'
            }
            finalValueBitset.set(maskLength - maskIndex - 1, finalValue)
            valueIndex--
            maskIndex--
        }
        return finalValueBitset.toLongArray().firstOrNull() ?: -1
    }

    fun part2(): Long {
        val memory = mutableMapOf<Long, Long>()
        inputRaw.forEach { inputLine ->
            if (inputLine.startsWith("mask")) {
                mask = inputLine.split("=")[1].trim()
            } else {
                val groups = valuesAssignRegex.matchEntire(inputLine)?.groupValues.orEmpty()
                val address = groups[1].toLong()
                val value = groups[2].toLong()
                calculateAddresses(address, mask)
                    .forEach { memory[it] = value }
            }
        }
        return memory.values.sum()
    }

    private fun calculateAddresses(address: Long, mask: String): List<Long> {
        val addressAsBinary = address.toString(2)
        var addressIndex = addressAsBinary.length - 1
        val maskLength = mask.length
        var maskIndex = maskLength - 1
        val finalAddressBitSet = BitSet(maskLength)
        val floatingPositions = mutableSetOf<Int>()
        while (maskIndex >= 0) {
            val maskValueAtIndex = mask[maskIndex]
            val finalValue = if (maskValueAtIndex == '0') {
                addressAsBinary.getOrElse(addressIndex) { '0' } == '1'
            } else {
                if (maskValueAtIndex == 'X') {
                    floatingPositions.add(maskLength - maskIndex - 1)
                }
                maskValueAtIndex == '1' // setting it to false for "floating" and ORing with powerset later
            }
            finalAddressBitSet.set(maskLength - maskIndex - 1, finalValue)
            addressIndex--
            maskIndex--
        }
        return floatingPositions.powerSet()
            .map { powerSet ->
                val powerSetBitSet = BitSet(maskLength)
                powerSetBitSet.or(finalAddressBitSet) // set to previously calculated
                powerSet.forEach { powerSetBitSet.set(it) }
                powerSetBitSet
            }
            .map { it.toLongArray().firstOrNull() ?: -1 }

    }

    // taken from StackOverflow
    private fun <T> Collection<T>.powerSet(): Set<Set<T>> = when {
        isEmpty() -> setOf(setOf())
        else -> drop(1).powerSet().let { it + it.map { it + first() } }
    }
 }
