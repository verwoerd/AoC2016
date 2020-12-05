package day04.part1

import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 05/12/2020
 */
fun part1(input: BufferedReader): Any {
  return input.lineSequence()
    .map { regex.matchEntire(it)!!.groupValues.drop(1) }
    .filter { (room, _, checksum) -> checkSum(room) == checksum }
    .sumBy { (_, code, _) -> code.toInt() }
}

val regex = Regex("^([\\w\\-]+)-(\\d+)\\[(\\w+)]")


fun checkSum(line: String): String {
  val chars = line.groupBy { it }
  return chars.keys.asSequence().filter { it != '-' }
    .sorted().sortedByDescending { chars[it]!!.size }.take(5).joinToString("")
}
