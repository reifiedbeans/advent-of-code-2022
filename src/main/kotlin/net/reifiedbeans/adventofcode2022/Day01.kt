package net.reifiedbeans.adventofcode2022

object Day01 {
    fun part1(input: List<List<String>>): Int {
        return input.maxOf { it.map(Integer::parseInt).sum() }
    }

    fun part2(input: List<List<String>>): Int {
        return input.map { it.map(Integer::parseInt).sum() }
            .sortedDescending()
            .take(3)
            .sum()
    }
}

fun main() {
    val testInput = readInputChunked("Day01_test")
    check(Day01.part1(testInput) == 24000)

    val input = readInputChunked("Day01")
    println("Part 1: ${Day01.part1(input)}")
    println("Part 2: ${Day01.part2(input)}")
}