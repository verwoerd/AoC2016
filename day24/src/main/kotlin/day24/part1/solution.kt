package day24.part1

import Coordinate
import adjacentCoordinates
import allPermutations
import priorityQueueOf
import java.io.BufferedReader
import kotlin.Comparator
import kotlin.streams.toList

/**
 * @author verwoerd
 * @since 17/11/2021
 */
fun part1(input: BufferedReader): Any {
  val distances = parseMap(input)
    return allPermutations(distances.keys.filter{it != 0}.toSet())
    .map { listOf(0) + it }
    .minOf { distances.calculateDistance(it) }
}

fun parseMap(input: BufferedReader): Map<Int, Map<Int, Int>> {
  val map = input.lines().toList()
  val pointsOfInterest = mutableMapOf<Int, Coordinate>()
  map.forEachIndexed { y, line ->
    line.foldIndexed(pointsOfInterest) { x, acc, c ->
      when (c) {
        in '0'..'9' -> acc[c.digitToInt()] = Coordinate(x, y)
      }
      acc
    }
  }

  return pointsOfInterest.keys.associateWith { map.floodDistanceSearch(pointsOfInterest[it]!!) }
}

fun List<String>.floodDistanceSearch(start: Coordinate): Map<Int, Int> {
  val queue = priorityQueueOf(Comparator.comparing { it.first }, 0 to start)
  val seen = mutableSetOf(start)
  val distances = mutableMapOf<Int, Int>()
  while (queue.isNotEmpty()) {
    val (distance, location) = queue.poll()
    adjacentCoordinates(location)
      .filter { seen.add(it) }
      .filter {
        when (val value = get(it.y)[it.x]) {
          '#' -> false
          '.' -> true
          else -> {
            distances[value.digitToInt()] = distance + 1
            true
          }
        }
      }.map { distance + 1 to it }.toCollection(queue)
  }
  return distances
}

fun Map<Int, Map<Int, Int>>.calculateDistance(route: List<Int>): Int =
  route.windowed(size = 2, step = 1)
    .sumOf { (from, to) -> get(from)!![to]!! }


