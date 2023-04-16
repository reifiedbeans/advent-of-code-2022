package net.reifiedbeans.adventofcode2022

private object Day08 {
    private data class Forest(private val trees: List<List<Int>>) {
        val m = trees.first().size
        val n = trees.size

        companion object {
            fun parse(input: List<String>) = Forest(input.map { it.map(Char::digitToInt) })
        }

        private fun treeNorthView(i: Int, j: Int) = this.trees.slice(IntRange(0, i - 1)).map { it[j] }.reversed()
        private fun treeSouthView(i: Int, j: Int) = this.trees.slice(IntRange(i + 1, n - 1)).map { it[j] }
        private fun treeWestView(i: Int, j: Int) = this.trees[i].slice(IntRange(0, j - 1)).reversed()
        private fun treeEastView(i: Int, j: Int) = this.trees[i].slice(IntRange(j + 1, m - 1))

        private fun treeViews(i: Int, j: Int) = setOf(
            treeNorthView(i, j),
            treeSouthView(i, j),
            treeWestView(i, j),
            treeEastView(i, j),
        )

        private fun treeIsVisible(i: Int, j: Int): Boolean {
            val currentHeight = this.trees[i][j]
            return this.treeViews(i, j).any { trees ->
                trees.all { it < currentHeight }
            }
        }

        private fun treeScenicScore(i: Int, j: Int): Int {
            val currentHeight = this.trees[i][j]
            return this.treeViews(i, j).map { trees ->
                trees.indices.find { trees[it] >= currentHeight }?.plus(1) ?: trees.size
            }.reduce(Int::times)
        }

        fun numVisibleTrees() = (0 until n).sumOf { i ->
            (0 until m).count { j ->
                treeIsVisible(i, j)
            }
        }

        fun maxScenicScore() = (0 until n).maxOf { i ->
            (0 until m).maxOf { j ->
                treeScenicScore(i, j)
            }
        }
    }

    fun part1(input: List<String>) = Forest.parse(input).numVisibleTrees()

    fun part2(input: List<String>) = Forest.parse(input).maxScenicScore()
}

fun main() {
    val testInput = getInput("Day08_test").readLines()
    check(Day08.part1(testInput) == 21)
    check(Day08.part2(testInput) == 8)

    val input = getInput("Day08").readLines()
    println("Part 1: ${Day08.part1(input)}")
    println("Part 2: ${Day08.part2(input)}")
}
