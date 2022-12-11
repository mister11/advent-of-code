package hr.mister11.aoc2022

import kotlin.test.Test
import kotlin.test.assertEquals

class Solution11Test {

    @Test
    fun testPart1() {
        assertEquals("10605", Solution11.part1(readExample("11")))
    }

    @Test
    fun testPart2() {
        assertEquals("2713310158", Solution11.part2(readExample("11")))
    }
}