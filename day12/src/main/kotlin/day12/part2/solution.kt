package day12.part2

import day12.part1.Computer
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 05/10/2021
 */
fun part2(input: BufferedReader): Any {
  val program = input.readLines()
  val computer = Computer(c = 1)
  computer.execute(program)
  return computer.a
}
