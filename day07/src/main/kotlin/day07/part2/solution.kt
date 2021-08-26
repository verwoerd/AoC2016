package day07.part2

import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 05/12/2020
 */
fun part2(input: BufferedReader): Any {
  return input.lineSequence()
    .count { line ->
      val options = line.split(Regex("[\\[\\]]")).withIndex().groupBy({ it.index % 2 }) { it.value }
      options[1]!!.flatMap { part -> part.windowed(3).filter { it[0] == it[2] && it[1] != it[2] } }
        .map { it.toBab() }.any { bab -> options[0]!!.any { it.contains(bab) } }
    }
}

fun String.toBab() = "${this[1]}${this[0]}${this[1]}"
