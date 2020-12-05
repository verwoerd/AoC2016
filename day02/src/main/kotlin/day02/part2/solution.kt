package day02.part2

import day02.part2.Direction.Companion.fromChar
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 05/12/2020
 */
fun part2(input: BufferedReader): Any {
  return input.lineSequence().fold("" to 5) { (code, value), line ->
    line.map { fromChar(it) }.fold(value) { location, direction ->
      direction.newValue(location)
    }.let { code + it.toString(16).capitalize() to it }
  }.first
}

enum class Direction(private val diff: Int, private val range: Set<Int>) {
  UP(-3, setOf(0x3, 0x6, 0x7, 0x8, 0xA, 0xB, 0xC, 0xD)),
  LEFT(-1, setOf(0x3, 0x4, 0x6, 0x7, 0x8, 0x9, 0xB, 0xC)),
  DOWN(3, setOf(0x1, 0x2, 0x3, 0x4, 0x6, 0x7, 0x8, 0xB)),
  RIGHT(1, setOf(0x2, 0x3, 0x5, 0x6, 0x7, 0x8, 0xA, 0xB));

  fun newValue(current: Int) = when (current) {
    in range -> current + when (this) {
      LEFT, RIGHT -> diff
      UP -> when (current) {
        3, 0xD -> -2
        else -> -4
      }
      DOWN -> when (current) {
        1, 0xB -> 2
        else -> 4
      }
    }
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
