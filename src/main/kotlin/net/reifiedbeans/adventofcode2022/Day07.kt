package net.reifiedbeans.adventofcode2022

private const val DISK_SIZE = 70_000_000
private const val REQUIRED_SPACE = 30_000_000

private sealed interface Node {
    val size: Int
    val parent: Directory?
}

private class Directory(
    override val parent: Directory?,
    val children: HashMap<String, Node> = hashMapOf(),
) : Node {
    override val size: Int
        get() = children.values.sumOf(Node::size)
}

private class File(override val size: Int, override val parent: Directory?) : Node

private fun constructTree(input: List<String>): Set<Directory> {
    val root = Directory(parent = null)
    val directories = mutableSetOf(root)
    var currentDirectory = root

    fun String.isCommand() = this.startsWith("$")

    for (line in input) {
        if (line.isCommand()) {
            val parts = line.split(" ")
            when (parts[1]) {
                "cd" -> {
                    val param = parts[2]
                    currentDirectory = if (param == "..") {
                        currentDirectory.parent ?: error("Current directory does not have a parent")
                    } else {
                        currentDirectory.children[param] as? Directory ?: error("Cannot change into directory: $param")
                    }
                }

                "ls" -> continue // Doesn't give us any useful information
            }
        } else {
            val parts = line.split(" ")
            val name = parts.last()
            currentDirectory.children[name] = if (parts.first() == "dir") {
                Directory(parent = currentDirectory).also(directories::add)
            } else {
                val size = parts.first().toInt()
                File(size, parent = currentDirectory)
            }
        }
    }

    return directories
}

private fun part1(input: List<String>) = constructTree(input)
    .map(Directory::size)
    .filter { it < 100_000 }
    .sum()

private fun part2(input: List<String>): Int {
    val directories = constructTree(input)
    val root = directories.first()
    val neededSpace = REQUIRED_SPACE - (DISK_SIZE - root.size)
    return directories.map(Directory::size).filter { it >= neededSpace }.min()
}

fun main() {
    // First line is always "$ cd /", so I drop the first line in each input
    val testInput = getInput("Day07_test").readLines().drop(1)
    check(part1(testInput) == 95437)
    check(part2(testInput) == 24933642)

    val input = getInput("Day07").readLines().drop(1)
    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
}
