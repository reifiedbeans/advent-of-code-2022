package net.reifiedbeans.adventofcode2022

object Day01 {
    private fun List<String>.sum() = this.sumOf(String::toInt)

    fun part1(input: List<List<String>>) = input.maxOf { it.sum() }

    fun part2(input: List<List<String>>) = input.map { it.sum() }
        .sortedDescending()
        .take(3)
        .sum()
}

fun main() {
    val testInput = readInputChunked("Day01_test")
    check(Day01.part1(testInput) == 24000)
    check(Day01.part2(testInput) == 45000)

    val input = readInputChunked("Day01")
    println("Part 1: ${Day01.part1(input)}")
    println("Part 2: ${Day01.part2(input)}")
}
