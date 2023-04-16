package net.reifiedbeans.adventofcode2022

private object Day06 {
    private fun String.endIndexOfUniqueSubsequence(sequenceLength: Int) = this.windowed(sequenceLength)
        .indexOfFirst { it.toSet().size == it.length } + sequenceLength

    fun part1(input: String) = input.endIndexOfUniqueSubsequence(sequenceLength = 4)

    fun part2(input: String) = input.endIndexOfUniqueSubsequence(sequenceLength = 14)
}

fun main() {
    val testInput = getInput("Day06_test").readText()
    check(Day06.part1(testInput) == 5)
    check(Day06.part2(testInput) == 23)

    val input = getInput("Day06").readText()
    println("Part 1: ${Day06.part1(input)}")
    println("Part 2: ${Day06.part2(input)}")
}
