package day09.part1

import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 26/08/2021
 */
fun part1(input: BufferedReader): Any {
  val line = input.readLine()
  val result = StringBuilder()
  var i = 0
  while (i < line.length) {
    when (val char = line[i]) {
      '(' -> {
        i++
        val left = line.drop(i).takeWhile { it != 'x' }
        i+= left.length + 1
        val right = line.drop(i).takeWhile { it != ')' }
        i += right.length + 1
        val toRepeat = line.drop(i).take(left.toInt())
        repeat(right.toInt()) { result.append(toRepeat) }
        i += left.toInt()
      }
      else -> result.append(char).also { i++ }
    }
  }
  return result.length
}
