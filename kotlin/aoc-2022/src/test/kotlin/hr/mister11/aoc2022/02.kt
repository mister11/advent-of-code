package hr.mister11.aoc2022

import kotlin.test.Test
import kotlin.test.assertEquals

class Solution02Test {

    @Test
    fun testPart1() {
        assertEquals("15", Solution02.part1(readExample("02")))
    }

    @Test
    fun testPart2() {
        assertEquals("12", Solution02.part2(readExample("02")))
    }
}
