package hr.mister11.aoc.year2019.day6

import hr.mister11.aoc.Resources

fun main() {
    val solution = Solution(Resources.readFileAsList(2019, 6))
    println("Part 1: ${solution.part1()}")
    println("Part 2: ${solution.part2()}")
}

// this is top 5 worst codes that I've ever written, but I didn't want to implement Dijkstra just for fun :)
class Solution(
    rawOrbits: List<String>
) {

    private val orbitsMap = parseRawOrbits(rawOrbits)

    fun part1(): Int {
        val currentOrbitName = "COM"
        var neighbours = orbitsMap.getOrDefault(currentOrbitName, emptyList())
        var level = 1
        while (neighbours.isNotEmpty()) {
            neighbours.forEach { it.existingOrbits = level }
            neighbours = neighbours.flatMap { orbitsMap.getOrDefault(it.name, emptyList()) }
            level++
        }
        return orbitsMap.values.flatten().sumBy { it.existingOrbits }
    }

    fun part2(): Int {
        // this is done here so I don't have track visited in the part1
        val twoWayOrbits = orbitsMap
            .flatMap { (key, orbits) -> orbits.map { it.name to Orbit(key) } }
            .groupBy({it.first}, {it.second})
            .mapValues { it.value.toMutableList() }
            .toMutableMap()

        orbitsMap.forEach { (key, value) ->
            // oh boy, this resetting existing orbits state is looking amazing here
            twoWayOrbits.computeIfAbsent(key) { mutableListOf() }.addAll(value.map { it.copy(existingOrbits = 0) })
        }
        val santaOrbit = twoWayOrbits.filter { it.value.contains(Orbit("SAN")) }.keys.first()
        val meOrbit = twoWayOrbits.filter { it.value.contains(Orbit("YOU")) }.keys.first()

        var steps = 1
        var neighbours = twoWayOrbits.getOrDefault(santaOrbit, mutableListOf()).toSet()
        while (neighbours.isNotEmpty()) {
            if (neighbours.contains(Orbit(meOrbit, 0))) {
                return steps
            }
            neighbours = neighbours.flatMap { twoWayOrbits.getOrDefault(it.name, mutableListOf()) }.toSet()
            steps++
        }

        return -1
    }

    private fun parseRawOrbits(rawOrbits: List<String>): Map<String, List<Orbit>> {
        return rawOrbits
            .map {
                val orbits = it.split(")")
                orbits[0] to Orbit(orbits[1])
            }.groupBy({ it.first }, { it.second })
    }
}

data class Orbit(
    val name: String,
    var existingOrbits: Int = 0
)
