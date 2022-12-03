package net.reifiedbeans.adventofcode2022

object Day02 {
    class Game(private val opponentMove: Move, private val playerMove: Move) {
        val score: Int
            get() = playerMove.scoreAgainst(opponentMove)
    }

    enum class Outcome(val value: Int) {
        WIN(6),
        DRAW(3),
        DEFEAT(0),
    }

    sealed interface Move {
        val value: Int
        val winningCountermove: Move
        val losingCountermove: Move

        fun against(other: Move) = when (other) {
            winningCountermove -> Outcome.DEFEAT
            losingCountermove -> Outcome.WIN
            else -> Outcome.DRAW
        }

        fun moveForOutcome(outcome: Outcome) = when (outcome) {
            Outcome.WIN -> winningCountermove
            Outcome.DEFEAT -> losingCountermove
            Outcome.DRAW -> this
        }

        fun scoreAgainst(other: Move) = value + against(other).value
    }

    object Rock : Move {
        override val value = 1
        override val winningCountermove = Paper
        override val losingCountermove = Scissors
    }

    object Paper : Move {
        override val value = 2
        override val winningCountermove = Scissors
        override val losingCountermove = Rock
    }

    object Scissors : Move {
        override val value = 3
        override val winningCountermove = Rock
        override val losingCountermove = Paper
    }

    fun part1(input: List<String>): Int {
        val parseGame: (input: String) -> Game = { string ->
            val (opponentMove, playerMove) = string.split(" ").map { char ->
                when (char) {
                    "A", "X" -> Rock
                    "B", "Y" -> Paper
                    "C", "Z" -> Scissors
                    else -> throw Exception("Invalid move")
                }
            }
            Game(opponentMove, playerMove)
        }
        return input.sumOf { parseGame(it).score }
    }

    fun part2(input: List<String>): Int {
        val parseGame: (input: String) -> Game = { string ->
            val opponentMove = when (val move = string[0]) {
                'A' -> Rock
                'B' -> Paper
                'C' -> Scissors
                else -> throw Exception("Invalid move: $move")
            }
            val desiredOutcome = when (val outcome = string[2]) {
                'X' -> Outcome.DEFEAT
                'Y' -> Outcome.DRAW
                'Z' -> Outcome.WIN
                else -> throw Exception("Invalid outcome: $outcome")
            }
            Game(opponentMove, opponentMove.moveForOutcome(desiredOutcome))
        }
        return input.sumOf { parseGame(it).score }
    }
}

fun main() {
    val testInput = readInput("Day02_test")
    check(Day02.part1(testInput) == 15)
    check(Day02.part2(testInput) == 12)

    val input = readInput("Day02")
    println("Part 1: ${Day02.part1(input)}")
    println("Part 2: ${Day02.part2(input)}")
}