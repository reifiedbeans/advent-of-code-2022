package net.reifiedbeans.adventofcode2022

private fun String.endIndexOfUniqueSubsequence(sequenceLength: Int) = this.windowed(sequenceLength)
    .indexOfFirst { it.toSet().size == it.length } + sequenceLength

private fun part1(input: String) = input.endIndexOfUniqueSubsequence(sequenceLength = 4)

private fun part2(input: String) = input.endIndexOfUniqueSubsequence(sequenceLength = 14)

fun main() {
    val testInput = getInput("Day06_test").readText()
    check(part1(testInput) == 5)
    check(part2(testInput) == 23)

    val input = getInput("Day06").readText()
    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
}
