package hr.mister11.aoc.year2019.day3

import hr.mister11.aoc.Resources
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class SolutionTest {

    @Test
    fun part1Test() {
        val solution = Solution(Resources.readFileAsList(3))
        Assertions.assertEquals(731, solution.part1())
    }

    @Test
    fun part2Test() {
        val solution = Solution(Resources.readFileAsList(3))
        Assertions.assertEquals(5672, solution.part2())
    }
}
