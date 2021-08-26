package day09.part2

import java.io.BufferedReader
import java.util.*

/**
 * @author verwoerd
 * @since 26/08/2021
 */
fun part2(input: BufferedReader): Any {
  val line = input.readLine().toMutableList()
  var result = 0L
  while (line.isNotEmpty()) {
    when (line.removeFirst()) {
      '(' -> result += line.extract()
      else -> result++
    }
  }
  return result
}

fun <T> MutableList<T>.removeFirstWhile(condition: (T) -> Boolean) =
  takeWhile(condition).also { repeat(it.size) { removeFirst() } }

fun MutableList<Char>.extract(): Long {
  val left = removeFirstWhile { it != 'x' }.joinToString("").toLong()
  removeFirst() // x
  val right = removeFirstWhile { it != ')' }.joinToString("").toLong()
  removeFirst() // )
  val toRepeat = (1..left).map { removeFirst() }.toMutableList()
  if (toRepeat.none { it == '(' }) {
    return left * right
  }
  val simple = toRepeat.removeFirstWhile { it != '(' }.count()
  var i = right * simple
  while (toRepeat.isNotEmpty()) {
    toRepeat.removeFirst() // (
    i += right * toRepeat.extract()
  }
  return i
}
