package hr.mister11.aoc2022

import kotlin.test.Test
import kotlin.test.assertEquals

class Solution01Test {

    @Test
    fun testPart1() {
        assertEquals("24000", Solution01.part1(readExample("01")))
    }

    @Test
    fun testPart2() {
        assertEquals("45000", Solution01.part2(readExample("01")))
    }
}