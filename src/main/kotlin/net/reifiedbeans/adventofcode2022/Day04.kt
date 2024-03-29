package net.reifiedbeans.adventofcode2022

private object Day04 {
    private fun IntRange.Companion.from(s: String) = s.split("-")
        .map(String::toInt)
        .let { (a, b) -> IntRange(a, b) }

    private infix fun IntRange.fullyContains(other: IntRange) = this.first <= other.first && this.last >= other.last

    private infix fun IntRange.overlapsWith(other: IntRange) = this.first <= other.last && this.last >= other.first

    private fun parseRanges(line: String) = line.split(",")
        .map { IntRange.from(it) }
        .let { (r1, r2) -> Pair(r1, r2) }

    fun part1(input: List<String>) = input.map(::parseRanges)
        .count { it.first fullyContains it.second || it.second fullyContains it.first }

    fun part2(input: List<String>) = input.map(::parseRanges).count { it.first overlapsWith it.second }
}

fun main() {
    val testInput = getInput("Day04_test").readLines()
    check(Day04.part1(testInput) == 2)
    check(Day04.part2(testInput) == 4)

    val input = getInput("Day04").readLines()
    println("Part 1: ${Day04.part1(input)}")
    println("Part 2: ${Day04.part2(input)}")
}
