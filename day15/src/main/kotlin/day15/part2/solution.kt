package day15.part2

import chineseRemainderTheorem
import day15.part1.INPUT_REGEX
import java.io.BufferedReader
import java.math.BigInteger.*
import kotlin.streams.toList

/**
 * @author verwoerd
 * @since 07/10/2021
 */
fun part2(input: BufferedReader): Any {
  val remainders = input.lines().map {
    val(disk, positions, start) = INPUT_REGEX.matchEntire(it)!!.destructured
    // x + disk + start % positions = 0
    val remainder = positions.toBigInteger()
    val value = (disk.toBigInteger() + start.toBigInteger() + ONE)
    remainder to value
  }.toList() + listOf(valueOf(11) to valueOf(8))
  return remainders.chineseRemainderTheorem() + ONE
}
