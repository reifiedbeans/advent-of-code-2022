package net.reifiedbeans.adventofcode2022

import java.io.File

fun getInput(name: String) = File("inputs", "$name.txt")

fun getExpectedOutput(name: String) = File("expectedOutputs", "$name.txt")

fun File.readLinesChunked(delimiter: String = "\n\n") =
    this.readText().trim('\n').split(delimiter).map(String::lines)
