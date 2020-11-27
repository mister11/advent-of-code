package hr.mister11.aoc

import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

object Resources {

    private val classLoader = Resources.javaClass.classLoader

    fun readFileRaw(day: Int): String = Files.readString(day.toInputPath()).trim()
    fun readFileAsList(day: Int): List<String> = Files.readAllLines(day.toInputPath())
    private fun Int.toInputPath() = classLoader.getResource("data/day$this/input.txt")?.toURI()?.path?.toPath()
    private fun String.toPath() = Paths.get(this)
}
