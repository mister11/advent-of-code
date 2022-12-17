package hr.mister11.aoc2022

fun main() {
    val input = readInput("13")

    println("Part 1: ${Solution13.part1(input)}")
    println("Part 2: ${Solution13.part2(input)}")
}

object Solution13 {
    fun part1(input: String): String {
        return parsePackets(input)
            .mapIndexed { index, (packet1, packet2) ->
                if (arePacketsOrdered(packet1, packet2)) {
                    println("Index: $index, Match $packet1")
                    index + 1
                } else {
                    0
                }
            }
            .sum()
            .toString()
    }

    fun part2(input: String): String {
        val additionalTwo = listOf(ListItem(values = listOf(ListItem(values = listOf(NumberItem(2))))))
        val additionalSix = listOf(ListItem(values = listOf(ListItem(values = listOf(NumberItem(6))))))
        val additionalPair = Pair(additionalTwo, additionalSix)
        val sortedPackets = (parsePackets(input) + listOf(additionalPair))
            .flatMap { (packet1, packet2) -> listOf(packet1, packet2) }
            .sortedWith(Comparator { packet1, packet2 ->
                if (arePacketsOrdered(packet1, packet2)) {
                    -1
                } else {
                    1
                }
            })

        return ((sortedPackets.indexOf(additionalTwo) + 1) * (sortedPackets.indexOf(additionalSix) + 1)).toString()
    }

    private fun arePacketsOrdered(packet1: List<PacketItem>, packet2: List<PacketItem>): Boolean {
        if (packet1.isEmpty()) {
            return true
        }
        if (packet2.isEmpty()) {
            return false
        }

        val first1 = packet1.first()
        val first2 = packet2.first()

        when {
            first1 is NumberItem && first2 is NumberItem -> {
                return if (first1.value < first2.value) {
                    true
                } else if (first1.value > first2.value) {
                    false
                } else {
                    arePacketsOrdered(packet1.drop(1), packet2.drop(1))
                }
            }

            first1 is NumberItem && first2 is ListItem -> {
                return arePacketsOrdered(listOf(first1), first2.values)
            }

            first1 is ListItem && first2 is NumberItem -> {
                return arePacketsOrdered(first1.values, listOf(first2))
            }

            first1 is ListItem && first2 is ListItem -> {
                if (first1.values.isEmpty() && first2.values.isEmpty()) {
                    return arePacketsOrdered(packet1.drop(1), packet2.drop(1))
                }
                return arePacketsOrdered(first1.values, first2.values)
            }
        }
        error("Oh no!")
    }

    private fun parsePackets(input: String): List<Pair<List<PacketItem>, List<PacketItem>>> {
        return input
            .split("\n\n")
            .map { it.split("\n", limit = 2) }
            .map { packetLines ->
                val packet1 = parsePacketLine(packetLines[0])
                val packet2 = parsePacketLine(packetLines[1])

                packet1 to packet2
            }
    }

    private fun parsePacketLine(packetLine: String): List<PacketItem> {
        val packetLineChars = packetLine.toCharArray().toMutableList()
        return parsePackets(packetLineChars)
    }

    private fun parsePackets(packetLineChars: MutableList<Char>): List<PacketItem> {
        val packetItems = mutableListOf<PacketItem>()
        packetLineChars.removeFirst()
        while (packetLineChars.first() != ']') {
            if (packetLineChars.first() == ',') {
                packetLineChars.removeFirst()
            } else {
                val packetItem = parseItem(packetLineChars)
                packetItems.add(packetItem)
            }
        }
        packetLineChars.removeFirst()
        return packetItems
    }

    private fun parseItem(packetLineChars: MutableList<Char>): PacketItem {
        val currentChar = packetLineChars.first()
        return if (currentChar.isDigit()) {
            var digitString = ""
            while (packetLineChars.first().isDigit()) {
                digitString += packetLineChars.removeFirst()
            }
            NumberItem(digitString.toInt())
        } else if (currentChar == '[') {
            ListItem(parsePackets(packetLineChars))
        } else {
            error("Unable to parse input.")
        }
    }

}

sealed interface PacketItem
data class NumberItem(val value: Int) : PacketItem
data class ListItem(val values: List<PacketItem>) : PacketItem