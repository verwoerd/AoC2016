package day15.part1

import chineseRemainderTheorem
import java.io.BufferedReader
import java.math.BigInteger.ONE
import kotlin.streams.toList

/**
 * @author verwoerd
 * @since 07/10/2021
 */
fun part1(input: BufferedReader): Any {
  val remainders = input.lines().map {
    val(disk, positions, start) = INPUT_REGEX.matchEntire(it)!!.destructured
    // x + disk + start % positions = 0
    val remainder = positions.toBigInteger()
    val value = (disk.toBigInteger() + start.toBigInteger() + ONE)
    remainder to value
  }.toList()
  return remainders.chineseRemainderTheorem() + ONE
}

val INPUT_REGEX = Regex("Disc #(\\w) has (\\w+) positions; at time=0, it is at position (\\w+)\\.")
