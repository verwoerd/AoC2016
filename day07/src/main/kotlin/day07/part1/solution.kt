package day07.part1

import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 05/12/2020
 */
fun part1(input: BufferedReader): Any {
  return input.lineSequence()
    .count {
      it.split(Regex("[\\[\\]]"))
        .foldIndexed(false to true) { index, (abba, valid), part ->
          when (index % 2) {
            0 -> abba or part.containsAbba() to valid
            else -> abba to (valid and !part.containsAbba())
          }
        }.let { (abba, valid) -> abba && valid }
    }
}

val ipRegex = Regex("^(\\w+)\\[(\\w+)](\\w+)$")

fun String.containsAbba(): Boolean =
  windowed(4).firstOrNull { it[0] == it[3] && it[1] == it[2] && it[0] != it[1] }?.let { true } ?: false
