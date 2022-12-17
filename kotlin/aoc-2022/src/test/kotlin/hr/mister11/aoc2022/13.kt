package hr.mister11.aoc2022

import kotlin.test.Test
import kotlin.test.assertEquals

class Solution13Test {

    @Test
    fun testPart1() {
        assertEquals("13", Solution13.part1(readExample("13")))
    }

    @Test
    fun testPart2() {
        assertEquals("140", Solution13.part2(readExample("13")))
    }
}