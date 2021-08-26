package day08.part1

import java.io.BufferedReader
import kotlin.streams.asSequence

/**
 * @author verwoerd
 * @since 26/08/2021
 */
val rectRegex = Regex("rect (\\d+)x(\\d+)")
val rotateRowRegex = Regex("rotate row y=(\\d+) by (\\d+)")
val rotateColumnRegex = Regex("rotate column x=(\\d+) by (\\d+)")

fun part1(input: BufferedReader): Any {
  val display = List(6) { List(50) { false } };
  val lines = input.lines().asSequence().fold(display) { acc, line ->
    when {
      rectRegex.matches(line) -> {
        val (a, b) = rectRegex.matchEntire(line)!!.destructured
        acc.mapIndexed { indexY, value ->
          when (indexY) {
            in (0 until b.toInt()) -> value.mapIndexed { index, x ->
              when (index) {
                in (0 until a.toInt()) -> true
                else -> x
              }
            }
            else -> value
          }
        }
      }
      rotateRowRegex.matches(line) -> {
        val (a, b) = rotateRowRegex.matchEntire(line)!!.groupValues.drop(1).map { it.toInt() }
        acc.mapIndexed { indexY, value ->
          when (indexY) {
            a -> value.takeLast(b) + value.dropLast(b)
            else -> value
          }
        }
      }
      rotateColumnRegex.matches(line) -> {
        val (a, b) = rotateColumnRegex.matchEntire(line)!!.groupValues.drop(1).map { it.toInt() }
        val newValues = acc.takeLast(b).map { it[a] } + acc.dropLast(b).map { it[a] }
        acc.mapIndexed { indexY, value ->
          value.mapIndexed { index, x ->
            when (index) {
              a -> newValues[indexY]
              else -> x
            }
          }
        }
      }
      else -> error("invalid operation")
    }

  }
  lines.print()
  return lines.sumBy { line -> line.count { it } }
}

fun List<List<Boolean>>.print() {
  println(joinToString(separator = "\n") { line ->
    line.joinToString(separator = "") {
      when (it) {
        true -> "#"
        else -> "."
      }
    }
  })
  println("------------------------")
}
