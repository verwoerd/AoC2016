package day11.part2

import day11.part1.Chip
import day11.part1.Generator
import day11.part1.findBfsSolution
import day11.part1.parseInput
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 29/09/2021
 */
fun part2(input: BufferedReader): Any {
  val extraTypes = listOf("elerium", "dilithium")
  val scenario = parseInput(input)
  val newScenario = scenario.copy(
    firstFloor = scenario.firstFloor.copy(
      chips = scenario.firstFloor.chips + extraTypes.map { Chip(it) },
      generators = scenario.firstFloor.generators + extraTypes.map { Generator(it) }
    )
  )
  return findBfsSolution(newScenario)
}
