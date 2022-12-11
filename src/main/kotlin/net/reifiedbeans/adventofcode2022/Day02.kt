package net.reifiedbeans.adventofcode2022

private data class Game(private val opponentMove: Move, private val playerMove: Move) {
    private val outcome get() = playerMove.against(opponentMove)
    val score get() = playerMove.value + this.outcome.score
}

private sealed class Outcome(val score: Int)
private object Win : Outcome(6)
private object Draw : Outcome(3)
private object Defeat : Outcome(0)

private sealed interface Move {
    val value: Int
    val winningCountermove: Move
    val losingCountermove: Move

    fun against(other: Move) = when (other) {
        this.winningCountermove -> Defeat
        this.losingCountermove -> Win
        else -> Draw
    }

    fun countermoveFor(outcome: Outcome) = when (outcome) {
        is Win -> this.winningCountermove
        is Defeat -> this.losingCountermove
        is Draw -> this
    }
}

private object Rock : Move {
    override val value = 1
    override val winningCountermove = Paper
    override val losingCountermove = Scissors
}

private object Paper : Move {
    override val value = 2
    override val winningCountermove = Scissors
    override val losingCountermove = Rock
}

private object Scissors : Move {
    override val value = 3
    override val winningCountermove = Rock
    override val losingCountermove = Paper
}

private fun part1(input: List<String>): Int {
    val parseGame: (input: String) -> Game = { string ->
        val (opponentMove, playerMove) = string.split(" ").map { char ->
            when (char) {
                "A", "X" -> Rock
                "B", "Y" -> Paper
                "C", "Z" -> Scissors
                else -> error("Invalid move: $char")
            }
        }
        Game(opponentMove, playerMove)
    }
    return input.map(parseGame).sumOf(Game::score)
}

private fun part2(input: List<String>): Int {
    val parseGame: (input: String) -> Game = { string ->
        val opponentMove = when (val move = string[0]) {
            'A' -> Rock
            'B' -> Paper
            'C' -> Scissors
            else -> error("Invalid move: $move")
        }
        val desiredOutcome = when (val outcome = string[2]) {
            'X' -> Defeat
            'Y' -> Draw
            'Z' -> Win
            else -> error("Invalid outcome: $outcome")
        }
        val playerMove = opponentMove.countermoveFor(desiredOutcome)
        Game(opponentMove, playerMove)
    }
    return input.map(parseGame).sumOf(Game::score)
}


fun main() {
    val testInput = getInput("Day02_test").readLines()
    check(part1(testInput) == 15)
    check(part2(testInput) == 12)

    val input = getInput("Day02").readLines()
    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
}
