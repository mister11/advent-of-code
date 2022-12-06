package hr.mister11.aoc2022

import kotlin.test.Test
import kotlin.test.assertEquals

class Solution06Test {

    @Test
    fun testPart1() {
        assertEquals("11", Solution06.part1(readExample("06")))
    }

    @Test
    fun testPart2() {
        assertEquals("26", Solution06.part2(readExample("06")))
    }
}
