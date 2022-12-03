package net.reifiedbeans.adventofcode2022

import java.io.File

fun readInput(name: String) = File("inputs", "$name.txt").readLines()

fun readInputChunked(name: String, delimiter: String = "\n\n") = File("inputs", "$name.txt")
    .readText()
    .split("\n\n")
    .map { it.split("\n") }