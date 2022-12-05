package hr.mister11.aoc2022

import kotlin.test.Test
import kotlin.test.assertEquals

class Solution05Test {

    @Test
    fun testPart1() {
        assertEquals("CMZ", Solution05.part1(readExample("05")))
    }

    @Test
    fun testPart2() {
        assertEquals("MCD", Solution05.part2(readExample("05")))
    }
}