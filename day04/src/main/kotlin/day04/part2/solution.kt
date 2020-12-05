package day04.part2

import day04.part1.checkSum
import day04.part1.regex
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 05/12/2020
 */
fun part2(input: BufferedReader): Any {
  return input.lineSequence()
    .map { regex.matchEntire(it)!!.groupValues.drop(1) }
    .filter { (room, _, checksum) -> checkSum(room) == checksum }
    .map { it.first() to it[1].toInt() }
    .map {(string, code)-> string.map {
      when(it) {
        '-' -> ' '
        else -> (it+ (code % 26)).let {char -> when(char){
          in 'a'..'z' -> char
          else -> char - 26
        } }
      } }.joinToString("") to code }
    .first { it.first.contains(Regex("north\\s?pole")) }.second
}
