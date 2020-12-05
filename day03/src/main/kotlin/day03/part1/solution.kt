package day03.part1

import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 05/12/2020
 */
fun part1(input: BufferedReader): Any {
  return input.lineSequence()
    .map { line -> regex.matchEntire(line)!!.groupValues.drop(1).map { it.toInt() }}
    .count { (a,b,c) -> a+b > c && a+c> b && b+c > a}
}

val regex = Regex("^\\s+(\\d+)\\s+(\\d+)\\s+(\\d+)")
