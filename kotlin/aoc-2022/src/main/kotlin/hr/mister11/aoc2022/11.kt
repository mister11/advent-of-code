package hr.mister11.aoc2022


fun main() {
    val input = readInput("11")

    println("Part 1: ${Solution11.part1(input)}")
    println("Part 2: ${Solution11.part2(input)}")
}

object Solution11 {
    fun part1(input: String): String {
        val monkeys = parseMonkeys(input)
        val inspections = List(monkeys.size) { 0 }.toMutableList()
        repeat(20) {
            monkeys.forEachIndexed { index, monkey ->
                monkey.items.forEach { item ->
                    inspections[index]++

                    val newWorryLevel = monkey.operation(item.worryLevel) / 3L
                    if (newWorryLevel % monkey.test.divisor == 0L) {
                        monkeys[monkey.test.trueIndex].items.add(Item(newWorryLevel))
                    } else {
                        monkeys[monkey.test.falseIndex].items.add(Item(newWorryLevel))
                    }
                }
                monkey.items.clear()

                println()
            }
        }
        return inspections
            .sortedDescending()
            .take(2)
            .reduce { acc, i -> acc * i }
            .toString()
    }

    fun part2(input: String): String {
        val monkeys = parseMonkeys(input)
        val inspections = List(monkeys.size) { 0L }.toMutableList()
        // thanks Reddit, but I don't like problems like this
        val globalDivisor = monkeys.map { it.test.divisor }.reduce { acc, i -> acc * i }
        repeat(10000) {
            monkeys.forEachIndexed { index, monkey ->
                monkey.items.forEach { item ->
                    inspections[index]++

                    val newWorryLevel = monkey.operation(item.worryLevel) % globalDivisor
                    if (newWorryLevel % monkey.test.divisor == 0L) {
                        monkeys[monkey.test.trueIndex].items.add(Item(newWorryLevel))
                    } else {
                        monkeys[monkey.test.falseIndex].items.add(Item(newWorryLevel))
                    }
                }
                monkey.items.clear()
            }
        }
        return inspections
            .sortedDescending()
            .take(2)
            .reduce { acc, i -> acc * i }
            .toString()
    }

    private fun parseMonkeys(input: String): List<Monkey> {
        return input.split("\n\n")
            .map { monkeyString -> parseMonkey(monkeyString) }
    }

    private fun parseMonkey(monkeyString: String): Monkey {
        val lines = monkeyString.lines().map { it.trim() }
        val (_, items) = lines[1].split(":", limit = 2)
        val (_, operation) = lines[2].split(":", limit = 2)
        val testLine = lines[3].split("\\s+".toRegex())
        val trueLine = lines[4].split("\\s+".toRegex())
        val falseLine = lines[5].split("\\s+".toRegex())

        return Monkey(
            items = parseItems(items),
            operation = parseOperation(operation),
            test = parseTest(testLine.last(), trueLine.last(), falseLine.last())
        )
    }

    private fun parseItems(items: String): MutableList<Item> {
        return items
            .trim()
            .split(",")
            .map { Item(it.trim().toLong()) }
            .toMutableList()
    }

    private fun parseOperation(operation: String): (Long) -> Long {
        val operationAction = operation.trim().split("=")[1]
        val operationRegex = "(.*) (.) (.*)".toRegex()
        val match = operationRegex.find(operationAction)?.groupValues.orEmpty()
        val operand = match[2]
        val secondArgString = match[3]
        val secondArg = if (secondArgString == "old") null else secondArgString.toLong()

        return if (operand == "+") {
            { value -> value + (secondArg ?: value) }
        } else {
            { value -> value * (secondArg ?: value) }
        }
    }

    private fun parseTest(testDivision: String, trueIndexValue: String, falseIndexValue: String): Test {
        return Test(
            testDivision.toLong(),
            trueIndexValue.toInt(),
            falseIndexValue.toInt()
        )
    }
}

data class Monkey(
    val items: MutableList<Item>,
    val operation: (Long) -> Long,
    val test: Test
)

data class Item(
    val worryLevel: Long
)

data class Test(
    val divisor: Long,
    val trueIndex: Int,
    val falseIndex: Int
)