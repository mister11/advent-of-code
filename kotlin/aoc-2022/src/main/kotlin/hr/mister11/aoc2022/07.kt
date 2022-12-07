package hr.mister11.aoc2022

import java.util.ArrayDeque

// well, this is an awful solution
// I though B part will require something smarter
fun main() {
    val input = readInput("07")

    println("Part 1: ${Solution07.part1(input)}")
    println("Part 2: ${Solution07.part2(input)}")
}

object Solution07 {
    fun part1(input: String): String {
        val fileSystem = FileSystem()

        val commands = parseInput(input.lines())
        commands.forEach {
            it.execute(fileSystem)
        }
        return fileSystem.candidatesSize(100000)
    }

    fun part2(input: String): String {
        val fileSystem = FileSystem()

        val commands = parseInput(input.lines())
        commands.forEach {
            it.execute(fileSystem)
        }
        return fileSystem.smallestPossibleDir()
    }

    private fun parseInput(inputLines: List<String>): List<Command> {
        var index = 0
        val commands = mutableListOf<Command>()
        while (index < inputLines.size) {
            val tokens = inputLines[index].split("\\s+".toRegex())

            if (tokens[1] == "cd") {
                commands.add(CdCommand(tokens[2]))
                index++
            } else {
                index++
                val dirsContent = mutableListOf<String>()
                while (index < inputLines.size && inputLines[index][0] != '$') {
                    dirsContent.add(inputLines[index])
                    index++
                }
                commands.add(LsCommand(dirsContent))
            }

        }
        return commands
    }
}

class FileSystem(
    private var currentNode: Node = DirectoryNode("/")
) {

    fun candidatesSize(sizeLimit: Int): String {
        while (this.currentNode.parent != null) {
            val totalSize = (this.currentNode as DirectoryNode).nodes.sumOf { it.size }
            this.currentNode.size = totalSize
            this.currentNode = this.currentNode.parent!!
        }

        val totalSize = (this.currentNode as DirectoryNode).nodes.sumOf { it.size }
        this.currentNode.size = totalSize

        var total = 0L
        val remaining = ArrayDeque<Node>()
        remaining.add(this.currentNode)
        while (remaining.isNotEmpty()) {
            val current = remaining.pop()
            if (current is FileNode) {
                continue
            }
            if (current.size < sizeLimit) {
                total += current.size
            }
            remaining.addAll((current as DirectoryNode).nodes)
        }

        return total.toString()
    }

    fun smallestPossibleDir(): String {
        while (this.currentNode.parent != null) {
            val totalSize = (this.currentNode as DirectoryNode).nodes.sumOf { it.size }
            this.currentNode.size = totalSize
            this.currentNode = this.currentNode.parent!!
        }

        val totalSize = (this.currentNode as DirectoryNode).nodes.sumOf { it.size }
        this.currentNode.size = totalSize

        val unused =70000000 - this.currentNode.size
        val diff = 30000000 - unused

        val possible = mutableListOf<Node>()
        val remaining = ArrayDeque<Node>()
        remaining.add(this.currentNode)
        while (remaining.isNotEmpty()) {
            val current = remaining.pop()
            if (current is FileNode) {
                continue
            }
            if (current.size > diff) {
                possible.add(current)
            }
            remaining.addAll((current as DirectoryNode).nodes)
        }

        return possible.minBy { it.size }.size.toString()
    }

    fun changeCurrentNode(name: String) {
        if (name == "/") {
            return
        } else if (name == "..") {
            val parent = this.currentNode.parent
            if (parent != null) {
                val totalSize = (this.currentNode as DirectoryNode).nodes.sumOf { it.size }
                this.currentNode.size = totalSize
                this.currentNode = parent
            }
        } else {
            val nextNode = (this.currentNode as DirectoryNode)
                .nodes.find { it.name == name }
            this.currentNode = nextNode!!
        }
    }

    fun addDirectoryNode(name: String) {
        val node = DirectoryNode(name, this.currentNode)
        (this.currentNode as DirectoryNode).nodes.add(node)
    }

    fun addFileNode(sizeValue: String, name: String) {
        val node = FileNode(name, this.currentNode, sizeValue.toLong())
        (this.currentNode as DirectoryNode).nodes.add(node)
    }

}

sealed class Node {
    abstract val name: String
    abstract val parent: Node?
    abstract var size: Long
}

class FileNode(
    override val name: String,
    override val parent: Node? = null,
    override var size: Long
) : Node()

class DirectoryNode(
    override val name: String,
    override val parent: Node? = null,
    override var size: Long = 0,
    val nodes: MutableList<Node> = mutableListOf()
) : Node()

sealed interface Command {
    fun execute(fileSystem: FileSystem)
}

data class CdCommand(
    private val directoryPath: String
) : Command {
    override fun execute(fileSystem: FileSystem) {
        fileSystem.changeCurrentNode(directoryPath)
    }
}

data class LsCommand(val content: List<String>) : Command {
    override fun execute(fileSystem: FileSystem) {
        content.forEach { line ->
            val tokens = line.split("\\s+".toRegex())

            if (tokens[0] == "dir") {
                fileSystem.addDirectoryNode(tokens[1])
            } else {
                fileSystem.addFileNode(tokens[0], tokens[1])
            }
        }
    }

}

