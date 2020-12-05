package day02.part1

import day02.part1.Direction.Companion.fromChar
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 05/12/2020
 */
fun part1(input: BufferedReader): Any {
  return input.lineSequence().fold("" to 5) { (code, value), line ->
    line.map { fromChar(it) }.fold(value) { location, direction ->
      direction.newValue(location)
    }.let { code + it to it }
  }.first
}

enum class Direction(private val diff: Int, private val range: Set<Int>) {
  UP(-3, setOf(4, 5, 6, 7, 8, 9)),
  LEFT(-1, setOf(2, 3, 5, 6, 8, 9)),
  DOWN(3, setOf(1, 2, 3, 4, 5, 6)),
  RIGHT(1, setOf(1, 2, 4, 5, 7, 8));

  fun newValue(current: Int) = when (current) {
    in range -> current + diff
    else -> current
  }

  companion object {
    fun fromChar(c: Char) = when (c) {
      'U' -> UP
      'L' -> LEFT
      'D' -> DOWN
      'R' -> RIGHT
      else -> error("Invalid")
    }
  }

}
