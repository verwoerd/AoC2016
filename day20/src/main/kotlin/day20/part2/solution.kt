package day20.part2

import day20.part1.readRangeInputAndCombine
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 12/11/2021
 */
fun part2(input: BufferedReader, rangeEnd: Long = 4294967295): Any {
  val ranges = readRangeInputAndCombine(input)
  return rangeEnd - ranges.sumOf { it.last - it.first + 1 } + 1
}
