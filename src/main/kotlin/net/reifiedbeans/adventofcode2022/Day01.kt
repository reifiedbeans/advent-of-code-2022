package net.reifiedbeans.adventofcode2022

private object Day01 {
    private fun List<String>.sum() = this.sumOf(String::toInt)

    fun part1(input: List<List<String>>) = input.maxOf { it.sum() }

    fun part2(input: List<List<String>>) = input.map { it.sum() }
        .sortedDescending()
        .take(3)
        .sum()
}

fun main() {
    val testInput = getInput("Day01_test").readLinesChunked()
    check(Day01.part1(testInput) == 24000)
    check(Day01.part2(testInput) == 45000)

    val input = getInput("Day01").readLinesChunked()
    println("Part 1: ${Day01.part1(input)}")
    println("Part 2: ${Day01.part2(input)}")
}
