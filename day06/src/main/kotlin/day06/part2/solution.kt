package day06.part2

import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 05/12/2020
 */
fun part2(input: BufferedReader): Any {
  return input.lineSequence()
    .fold(Array(31) { mutableMapOf<Char, Int>().withDefault { 0 } }) { array, current ->
      array.also {
        current.forEachIndexed { index, c ->
          array[index][c] = array[index].getValue(c) + 1
        }
      }
    }.mapNotNull { line -> line.minByOrNull { it.value } }
    .joinToString("") { it.key.toString() }
}
