package day18.part1

import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 11/11/2021
 */
fun part1(input: BufferedReader, depth: Int = 40): Any = execute(input,depth)


fun execute(input: BufferedReader, depth: Int) : Any {
  val startLine = input.readLine().map { it == '.' }
  var line = startLine
  var count = startLine.count { it }
  repeat(depth - 1) {
    line = line.nextLine()
    count += line.count { it }
  }
  return count
}

fun List<Boolean>.nextLine() = mapIndexed { index, center ->
  val left = getOrElse(index - 1) { true }
  val right = getOrElse(index + 1) { true }
  !((!left && !center && right) ||
      (!center && !right && left) ||
      (!left && center && right) ||
      (!right && center && left))
}
