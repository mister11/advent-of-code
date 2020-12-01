package hr.mister11.aoc

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

object Resources {

    private val classLoader = Resources.javaClass.classLoader

    fun readFileRaw(year: Int, day: Int): String = Files.readString(inputPath(year, day)).trim()
    fun readFileAsList(year: Int, day: Int): List<String> = Files.readAllLines(inputPath(year, day))
    private fun inputPath(year: Int, day: Int): Path? {
        return classLoader.getResource("data/${year}/day${day}/input.txt")?.toURI()?.path?.toPath()
    }
    private fun String.toPath() = Paths.get(this)
}
