package hr.mister11.aoc

import java.io.File
import java.nio.file.Files
import java.nio.file.Paths

fun readFileAsString(file: String): String = Files.readString(file.toPath())
fun readFileAsList(file: String): List<String> = Files.readAllLines(file.toPath())
fun writeToFile(content: String, outFile: String) = File(outFile).writeText(content)

private fun String.toPath() = Paths.get(this)