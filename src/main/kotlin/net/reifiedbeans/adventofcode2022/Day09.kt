package net.reifiedbeans.adventofcode2022

import kotlin.math.abs

private object Day09 {
    private sealed class Direction
    private object Left : Direction()
    private object Right : Direction()
    private object Down : Direction()
    private object Up : Direction()

    private class Knot {
        private val _visitedPositions = mutableSetOf<Pair<Int, Int>>()
        val visitedPositions
            get() = this._visitedPositions.toSet()

        var x = 0
            private set
        var y = 0
            private set

        fun visit(x: Int, y: Int) {
            this.x = x
            this.y = y
            this._visitedPositions.add(Pair(x, y))
        }
    }

    private class RopeBridge(val moves: List<Direction>) {
        companion object {
            fun parse(input: List<String>): RopeBridge {
                val moves = input.map {
                    val parts = it.split(" ")
                    val direction = when (val d = parts.first()) {
                        "L" -> Left
                        "R" -> Right
                        "D" -> Down
                        "U" -> Up
                        else -> error("Unexpected direction $d")
                    }
                    val steps = parts.last().toInt()
                    List(steps) { direction }
                }.flatten()

                return RopeBridge(moves)
            }
        }

        fun simulate(ropeLength: Int): List<Knot> {
            assert(ropeLength > 1)
            val rope = List(ropeLength) { Knot().apply { visit(0, 0) } }
            val head = rope.first()

            for (direction in this.moves) {
                val hx = head.x
                val hy = head.y

                when (direction) {
                    Left -> head.visit(hx - 1, hy)
                    Right -> head.visit(hx + 1, hy)
                    Down -> head.visit(hx, hy - 1)
                    Up -> head.visit(hx, hy + 1)
                }

                for ((leader, follower) in rope.windowed(2)) {
                    val dx = leader.x - follower.x
                    val dy = leader.y - follower.y

                    val x = follower.x
                    val y = follower.y

                    if (maxOf(abs(dx), abs(dy)) > 1) {
                        follower.visit(x + dx.coerceIn(-1, 1), y + dy.coerceIn(-1, 1))
                    }
                }
            }
            return rope
        }
    }

    fun part1(input: List<String>) = RopeBridge.parse(input)
        .simulate(ropeLength = 2)
        .last()
        .visitedPositions
        .size

    fun part2(input: List<String>) = RopeBridge.parse(input)
        .simulate(ropeLength = 10)
        .last()
        .visitedPositions
        .size
}

fun main() {
    val testInput1 = getInput("Day09_test1").readLines()
    val testInput2 = getInput("Day09_test2").readLines()
    check(Day09.part1(testInput1) == 13)
    check(Day09.part2(testInput2) == 36)

    val input = getInput("Day09").readLines()
    println("Part 1: ${Day09.part1(input)}")
    println("Part 2: ${Day09.part2(input)}")
}
