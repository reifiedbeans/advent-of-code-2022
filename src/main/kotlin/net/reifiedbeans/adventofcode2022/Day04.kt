package net.reifiedbeans.adventofcode2022

object Day04 {
    private fun IntRange.Companion.from(s: String) = s.split("-")
        .map(String::toInt)
        .let {(a, b) -> IntRange(a, b) }

    private fun IntRange.fullyContains(other: IntRange) = this.first <= other.first && this.last >= other.last

    private fun IntRange.overlapsWith(other: IntRange) = listOf(this, other)
        .sortedBy { it.first }
        .let { it[0].last >= it[1].first }

    private fun parseRanges(line: String)= line.split(",")
        .map { IntRange.from(it) }
        .let { (r1, r2) -> Pair(r1, r2) }

    fun part1(input: List<String>) = input.map { parseRanges(it) }
        .count { (first, second) -> first.fullyContains(second) || second.fullyContains(first) }

    fun part2(input: List<String>) = input.map { parseRanges(it) }
        .count { (first, second) -> first.overlapsWith(second) }
}

fun main() {
    val testInput = readInput("Day04_test")
    check(Day04.part1(testInput) == 2)
    check(Day04.part2(testInput) == 4)

    val input = readInput("Day04")
    println("Part 1: ${Day04.part1(input)}")
    println("Part 2: ${Day04.part2(input)}")
}
