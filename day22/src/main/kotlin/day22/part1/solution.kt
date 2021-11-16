package day22.part1

import Coordinate
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 16/11/2021
 */
fun part1(input: BufferedReader): Any {
  val nodes = readInput(input).associateBy { it.location }
  return nodes.keys
    .flatMap { coordinate -> nodes.keys.map { nodes[coordinate]!! to nodes[it]!! } }
    .filter { (a, _) -> a.used > 0 }
    .filter { (a, b) -> a.location != b.location }
    .count { (a, b) -> a.used <= b.available }
}


val INPUT_REGEX = Regex("/dev/grid/node-x(\\d+)-y(\\d+)\\s+(\\d+)T\\s+(\\d+)T\\s+(\\d+)T\\s+(\\d+)%")

fun readInput(input: BufferedReader) =
  input.lineSequence()
    .drop(2)
    .map { INPUT_REGEX.matchEntire(it) ?: error("can't parse $it") }
    .map { result -> result.groupValues.drop(1).map { it.toInt() } }
    .map { Node(Coordinate(it[0], it[1]), it[2], it[3]) }
    .toList()

data class Node(val location: Coordinate, val size: Int, val used: Int) {
  val available: Int
    get() = size - used
}
