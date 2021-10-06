package day13.part1

import Coordinate
import adjacentCircularCoordinates
import adjacentCoordinates
import priorityQueueOf
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 05/10/2021
 */
fun part1(input: BufferedReader, target: Coordinate = Coordinate(31, 39)): Any {
  val number = input.readLine().toInt()
  val start = Coordinate(1, 1)
  val seen = mutableSetOf(start)
  val queue = priorityQueueOf(Comparator { a, b -> a.first.compareTo(b.first) }, 0 to start)
  while (queue.isNotEmpty()) {
    val (steps, current) = queue.poll()
    val next = adjacentCoordinates(current)
      .filter { it.x >= 0 && it.y >= 0 }
      .filter { seen.add(it) }
      .filter { it.isPassable(number) }
      .map { steps +1 to it }.toList()

    if (next.any { it.second == target } ) {
      return steps + 1
    }

    queue.addAll(next)
  }
  error("No path found")
}

fun Coordinate.isPassable(favouriteNumber: Int) =
  (x * x + 3 * x + 2 * x * y + y + y * y + favouriteNumber).countOneBits() and 1 == 0
