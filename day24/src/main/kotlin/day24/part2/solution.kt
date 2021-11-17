package day24.part2

import allPermutations
import day24.part1.calculateDistance
import day24.part1.parseMap
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 17/11/2021
 */
fun part2(input: BufferedReader): Any {
  val distances = parseMap(input)
  return allPermutations(distances.keys.filter { it != 0 }.toSet())
    .map { listOf(0) + it + listOf(0) }
    .minOf { distances.calculateDistance(it) }
}
