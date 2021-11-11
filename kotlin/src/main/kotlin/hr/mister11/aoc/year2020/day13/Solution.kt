package hr.mister11.aoc.year2020.day13

import hr.mister11.aoc.Resources

fun main() {
    val solution = Solution(Resources.readFileAsList(2020, 13))
    println("Part 1: ${solution.part1()}")
    println("Part 2: ${solution.part2()}")
}

class Solution(
    rawData: List<String>
) {

    private val goalTimestamp = rawData[0].toLong()
    private val ids = rawData[1].split(",".toRegex()).filter { it != "x" }.map { it.toLong() }
    private val idsWithXes = rawData[1].split(",".toRegex()).map { it.toLongOrNull() ?: -1 }

    fun part1(): Long {
        return ids
            .map { ((((goalTimestamp / it) + 1) * it) - goalTimestamp to it) }
            .minByOrNull { (waitingTime, _) -> waitingTime }
            ?.let { (waitingTime, id) -> waitingTime * id } ?: -1
    }

    fun part2(): Long {
        return chineseRemainder(
            ids,
            idsWithXes
                .mapIndexedNotNull { index, value -> if (value == -1L) null else index }
                .map { index -> Math.floorMod(-1L * index, idsWithXes[index]) }
        )
    }

    /*
     Code below c/p from: https://rosettacode.org/wiki/Chinese_remainder_theorem#Kotlin
     */
    private fun multInv(a: Long, b: Long): Long {
        if (b == 1L) return 1
        var aa = a
        var bb = b
        var x0 = 0L
        var x1 = 1L
        while (aa > 1) {
            val q = aa / bb
            var t = bb
            bb = aa % bb
            aa = t
            t = x0
            x0 = x1 - q * x0
            x1 = t
        }
        if (x1 < 0L) x1 += b
        return x1
    }

    private fun chineseRemainder(n: List<Long>, a: List<Long>): Long {
        val prod = n.fold(1L) { acc, i -> acc * i }
        var sum = 0L
        for (i in n.indices) {
            val p = prod / n[i]
            sum += a[i] * multInv(p, n[i]) * p
        }
        return sum % prod
    }

}


