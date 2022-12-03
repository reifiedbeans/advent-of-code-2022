package net.reifiedbeans.adventofcode2022

object Day03 {
    private val Char.priority: Int
        get() {
            return if (this.isLowerCase()) {
                this.code - 96
            } else {
                this.code - 38
            }
        }

    private fun getDuplicatedItem(itemSets: List<HashSet<Char>>): Char {
        val itemCounts = hashMapOf<Char, Int>()
        itemSets.forEach { set ->
            set.forEach {
                itemCounts.merge(it, 1, Int::plus)
                if (itemCounts[it] == itemSets.size) return it
            }
        }
        throw Exception("No duplicated item found")
    }

    fun part1(input: List<String>): Int {
        return input.sumOf { sack ->
            sack.chunked(sack.length / 2)
                .map { it.toHashSet() }
                .let { getDuplicatedItem(it) }
                .priority
        }
    }

    fun part2(input: List<String>): Int {
        return input.chunked(3).sumOf { group ->
            getDuplicatedItem(group.map { it.toHashSet() }).priority
        }
    }
}

fun main() {
    val testInput = readInput("Day03_test")
    check(Day03.part1(testInput) == 157)
    check(Day03.part2(testInput) == 70)

    val input = readInput("Day03")
    println("Part 1: ${Day03.part1(input)}")
    println("Part 2: ${Day03.part2(input)}")
}
