package hr.mister11.aoc2022

import kotlin.test.Test
import kotlin.test.assertEquals

class Solution07Test {

    @Test
    fun testPart1() {
        assertEquals("95437", Solution07.part1(readExample("07")))
    }

    @Test
    fun testPart2() {
        assertEquals("24933642", Solution07.part2(readExample("07")))
    }
}