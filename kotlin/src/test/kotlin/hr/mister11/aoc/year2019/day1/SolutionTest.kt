package hr.mister11.aoc.year2019.day1

import hr.mister11.aoc.Resources
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class SolutionTest {

    @Test
    fun part1Test() {
        val solution = Solution(Resources.readFileAsList(1))
        Assertions.assertEquals(34241, solution.part1())
    }

    @Test
    fun part2Test() {
        val solution = Solution(Resources.readFileAsList(1))
        Assertions.assertEquals(51316, solution.part2())
    }

}
