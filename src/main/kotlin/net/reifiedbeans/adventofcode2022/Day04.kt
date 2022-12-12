package net.reifiedbeans.adventofcode2022

private fun IntRange.Companion.from(s: String) = s.split("-")
    .map(String::toInt)
    .let { (a, b) -> IntRange(a, b) }

private infix fun IntRange.fullyContains(other: IntRange) = this.first <= other.first && this.last >= other.last

private infix fun IntRange.overlapsWith(other: IntRange) = this.first <= other.last && this.last >= other.first

private fun parseRanges(line: String) = line.split(",")
    .map { IntRange.from(it) }
    .let { (r1, r2) -> Pair(r1, r2) }

private fun part1(input: List<String>) = input.map(::parseRanges)
    .count { it.first fullyContains it.second || it.second fullyContains it.first }

private fun part2(input: List<String>) = input.map(::parseRanges).count { it.first overlapsWith it.second }

fun main() {
    val testInput = getInput("Day04_test").readLines()
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    val input = getInput("Day04").readLines()
    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
}
