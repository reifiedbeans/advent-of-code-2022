package net.reifiedbeans.adventofcode2022

private fun List<String>.sum() = this.sumOf(String::toInt)

private fun part1(input: List<List<String>>) = input.maxOf { it.sum() }

private fun part2(input: List<List<String>>) = input.map { it.sum() }
    .sortedDescending()
    .take(3)
    .sum()


fun main() {
    val testInput = getInput("Day01_test").readLinesChunked()
    check(part1(testInput) == 24000)
    check(part2(testInput) == 45000)

    val input = getInput("Day01").readLinesChunked()
    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
}
