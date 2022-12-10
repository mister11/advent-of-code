package hr.mister11.aoc2022

import kotlin.test.Test
import kotlin.test.assertEquals

class Solution10Test {

    @Test
    fun testPart1() {
        assertEquals("13140", Solution10.part1(readExample("10")))
    }

    @Test
    fun testPart2() {
        assertEquals("""
           ##..##..##..##..##..##..##..##..##..##..
           ###...###...###...###...###...###...###.
           ####....####....####....####....####....
           #####.....#####.....#####.....#####.....
           ######......######......######......####
           #######.......#######.......#######.....
        """.trimIndent(), Solution10.part2(readExample("10")))
    }
}