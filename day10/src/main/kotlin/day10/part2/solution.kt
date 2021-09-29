package day10.part2

import day10.part1.Chip
import day10.part1.executeBotCompare
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 26/08/2021
 */
fun part2(input: BufferedReader): Any {
  val outputs =  executeBotCompare(input, true).second
  return outputs.filter{it.key in (0..2)}.map { it.value.id }.fold(1L){acc, l -> acc*l }
}
