package hr.mister11.aoc.year2019.day2

import hr.mister11.aoc.Resources
import hr.mister11.aoc.year2019.day2.Solution
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class SolutionTest {

    @Test
    fun part1Test() {
        val solution = Solution(Resources.readFileRaw(2))
        Assertions.assertEquals(3850704, solution.part1())
    }

    @Test
    fun part2Test() {
        val solution = Solution(Resources.readFileRaw(2))
        Assertions.assertEquals(6718, solution.part2())
    }
}
