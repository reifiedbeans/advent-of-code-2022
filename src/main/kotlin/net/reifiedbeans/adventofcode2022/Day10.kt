package net.reifiedbeans.adventofcode2022

import kotlin.collections.ArrayDeque

private object Day10 {
    private sealed interface Instruction
    private object NoOp : Instruction
    private data class AddX(val v: Int) : Instruction

    private sealed class Poller : (Int, Int) -> Unit

    private data class Cpu(private val instructions: List<Instruction>) {
        companion object {
            fun loadInstructions(input: List<String>): Cpu {
                val instructions = input.flatMap {
                    val parts = it.split(" ")
                    val operation = parts.first()
                    val operand = parts.last()
                    when (operation) {
                        "noop" -> listOf(NoOp)
                        "addx" -> listOf(NoOp, AddX(operand.toInt()))
                        else -> error("Unknown operation $operation")
                    }
                }
                return Cpu(instructions)
            }
        }

        var poller: Poller? = null

        fun execute(): Int {
            var x = 1
            var cycle = 0
            val cycles = ArrayDeque(this.instructions)

            while (cycles.isNotEmpty()) {
                val instruction = cycles.removeFirst().also { cycle++ }
                poller?.let { it(cycle, x) }
                when (instruction) {
                    is NoOp -> continue
                    is AddX -> {
                        x += instruction.v
                    }
                }
            }

            return x
        }
    }

    private class StrengthPoller : Poller() {
        val signalStrengths = mutableListOf<Int>()

        override fun invoke(cycle: Int, x: Int) {
            if (cycle == 20 || (cycle - 20) % 40 == 0) {
                this.signalStrengths += cycle * x
            }
        }
    }

    private class CrtPoller : Poller() {
        val output = MutableList(40 * 6) { '.' }

        override fun invoke(cycle: Int, x: Int) {
            val position = cycle - 1
            if (position % 40 in (x - 1..x + 1)) {
                output[position] = '#'
            }
        }
    }

    fun part1(input: List<String>): Int {
        val poller = StrengthPoller()
        val cpu = Cpu.loadInstructions(input)
        cpu.poller = poller
        cpu.execute()

        return poller.signalStrengths.sum()
    }

    fun part2(input: List<String>): String {
        val poller = CrtPoller()
        val cpu = Cpu.loadInstructions(input)
        cpu.poller = poller
        cpu.execute()

        return poller.output
            .chunked(40)
            .joinToString("\n") { it.joinToString("") }
    }
}

fun main() {
    val testInput = getInput("Day10_test").readLines()
    val expectedOutput = getExpectedOutput("Day10_part2").readText().trim()
    check(Day10.part1(testInput) == 13140)
    check(Day10.part2(testInput) == expectedOutput)

    val input = getInput("Day10").readLines()
    println("Part 1: ${Day10.part1(input)}")
    println("Part 2:\n${Day10.part2(input)}")
}
