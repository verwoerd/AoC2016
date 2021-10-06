package day13.part2

import Coordinate
import adjacentCoordinates
import day13.part1.isPassable
import priorityQueueOf
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 05/10/2021
 */
fun part2(input: BufferedReader): Any {
  val number = input.readLine().toInt()
  val start = Coordinate(1, 1)
  val seen = mutableSetOf(start)
  val queue = priorityQueueOf(Comparator { a, b -> a.first.compareTo(b.first) }, 0 to start)
  while (queue.isNotEmpty()) {
    val (steps, current) = queue.poll()
    if(steps == 50) continue
    adjacentCoordinates(current)
      .filter { it.x >= 0 && it.y >= 0 }
      .filter { it.isPassable(number) }
      .filter { seen.add(it) }
      .map { steps +1 to it }
      .toList().toCollection(queue)
  }
  return seen.size
}
