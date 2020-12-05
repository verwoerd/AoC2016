package day03.part2

import day03.part1.regex
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 05/12/2020
 */
fun part2(input: BufferedReader): Any {
  return input.lineSequence()
    .map { line -> regex.matchEntire(line)!!.groupValues.drop(1).map { it.toInt() } }
    .fold(Triple(listOf<Int>(), listOf<Int>(), listOf<Int>())) { (start, middle, end), (col1, col2, col3) ->
      Triple(start + col1, middle + col2, end + col3)
    }.let { it.first + it.second + it.third }
    .chunked(3)
    .count { (a, b, c) -> a + b > c && a + c > b && b + c > a }
}
