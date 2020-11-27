package hr.mister11.aoc.year2019.day1

import hr.mister11.aoc.Resources
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class SolutionTest {

    @Test
    fun part1Test() {
        val solution = Solution(Resources.readFileAsList(1))
        Assertions.assertEquals(3368364, solution.part1())
    }

    @Test
    fun part2Test() {
        val solution = Solution(Resources.readFileAsList(1))
        Assertions.assertEquals(5049684, solution.part2())
    }

}
