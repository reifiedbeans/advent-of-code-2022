package net.reifiedbeans.adventofcode2022

private typealias Item = Char

object Day03 {
    data class Rucksack internal constructor(val compartments: Pair<Set<Item>, Set<Item>>) : Iterable<Item> {
        companion object {
            fun parse(s: String): Rucksack {
                val compartments = s.chunked(s.length / 2)
                    .map(String::toSet)
                    .zipWithNext()
                    .first()
                return Rucksack(compartments)
            }
        }

        private val items = compartments.first union compartments.second

        override fun iterator() = items.iterator()
    }

    private val Item.priority
        get() = when (this) {
            in 'a'..'z' -> this - 'a' + 1
            in 'A'..'Z' -> this - 'A' + 27
            else -> error("Invalid item: $this")
        }

    fun part1(input: List<String>) = input.map(Rucksack::parse)
        .map(Rucksack::compartments)
        .flatMap { (first, second) -> first intersect second }
        .sumOf { it.priority }

    fun part2(input: List<String>) = input.map(Rucksack::parse).chunked(3)
        .flatMap { (first, second, third) -> first intersect second intersect third }
        .sumOf { it.priority }
}

fun main() {
    val testInput = readInput("Day03_test")
    check(Day03.part1(testInput) == 157)
    check(Day03.part2(testInput) == 70)

    val input = readInput("Day03")
    println("Part 1: ${Day03.part1(input)}")
    println("Part 2: ${Day03.part2(input)}")
}
