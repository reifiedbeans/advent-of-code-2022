package net.reifiedbeans.adventofcode2022

private typealias Crate = Char
private typealias Stack = ArrayDeque<Crate>

private object Day05 {
    private fun Stack.removeFirst(n: Int): List<Crate> {
        val crates = this.take(n)
        repeat(n) {
            this.removeFirst()
        }
        return crates
    }

    private data class Operation(val quantity: Int, val source: Stack, val destination: Stack)

    private class Ship(private val stacks: List<Stack>, operations: List<Operation>) {
        private val operations: MutableList<Operation>

        companion object {
            fun from(input: List<String>): Ship {
                val numberOfStacks = input.dropWhile { it.contains("[") }
                    .first()
                    .split(" ")
                    .filter(String::isNotBlank)
                    .maxOf(String::toInt)

                val stacks = List(numberOfStacks) { Stack() }
                input.filter { it.contains("[") }.map { line ->
                    line.mapIndexed { index, char ->
                        if (char.isLetter()) {
                            stacks[index / 4].addLast(char)
                        }
                    }
                }

                val operations = input.filter { it.contains("move") }.map { line ->
                    line.split(" ")
                        .mapNotNull(String::toIntOrNull)
                        .let {
                            val quantity = it[0]
                            val source = it[1]
                            val destination = it[2]
                            Operation(quantity, stacks[source - 1], stacks[destination - 1])
                        }
                }

                return Ship(stacks, operations)
            }
        }

        init {
            this.operations = operations.toMutableList()
        }

        fun executeOperations(canMoveMultipleAtOnce: Boolean = false) {
            for (op in operations) {
                if (canMoveMultipleAtOnce) {
                    val crates = op.source.removeFirst(op.quantity)
                    op.destination.addAll(0, crates)
                } else {
                    repeat(op.quantity) {
                        val crate = op.source.removeFirst()
                        op.destination.addFirst(crate)
                    }
                }
            }
            operations.clear()
        }

        fun topCrates() = stacks.map(Stack::first).joinToString("")
    }

    fun part1(input: List<String>) = Ship.from(input)
        .apply { executeOperations() }
        .topCrates()

    fun part2(input: List<String>) = Ship.from(input)
        .apply { executeOperations(canMoveMultipleAtOnce = true) }
        .topCrates()
}

fun main() {
    val testInput = getInput("Day05_test").readLines()
    check(Day05.part1(testInput) == "CMZ")
    check(Day05.part2(testInput) == "MCD")

    val input = getInput("Day05").readLines()
    println("Part 1: ${Day05.part1(input)}")
    println("Part 2: ${Day05.part2(input)}")
}
