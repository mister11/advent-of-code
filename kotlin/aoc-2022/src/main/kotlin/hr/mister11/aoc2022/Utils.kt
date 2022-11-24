package hr.mister11.aoc2022

fun readInput(day: String): String {
    return readResourceFile("/input/$day.txt")
}

fun readExample(day: String): String {
    return readResourceFile("/example/$day.txt")
}

private fun readResourceFile(path: String): String {
    return object {}.javaClass.getResourceAsStream(path)?.bufferedReader()?.readText().orEmpty().trim()
}
