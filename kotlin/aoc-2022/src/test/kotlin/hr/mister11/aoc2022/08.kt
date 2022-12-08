package hr.mister11.aoc2022

import kotlin.test.Test
import kotlin.test.assertEquals

class Solution08Test {

    @Test
    fun testPart1() {
        assertEquals("21", Solution08.part1(readExample("08")))
    }

    @Test
    fun testPart2() {
        assertEquals("8", Solution08.part2(readExample("08")))
    }
}