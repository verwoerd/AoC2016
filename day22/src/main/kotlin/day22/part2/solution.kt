package day22.part2

import Coordinate
import adjacentCoordinates
import day22.part1.readInput
import priorityQueueOf
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 16/11/2021
 */
fun part2(input: BufferedReader): Any {
  val nodes = readInput(input).associateBy { it.location }
  val target = Coordinate(nodes.keys.maxByOrNull { it.x }!!.x, 0)
  // observation: There is exactly 1 empty spot, and the first 2 rows are no blockers.
  val emptySpot = nodes.values.first { it.used == 0 }
  val blockers = nodes.values
    .filter { it != emptySpot }
    .filter { node ->
      nodes.keys.map { node to nodes[it]!! }
      .filter { (a, _) -> a.used > 0 }
      .filter { (a, b) -> a.location != b.location }
      .none { (a, b) -> a.used <= b.available }
    }
  val realMap = nodes.toMutableMap().also { m -> blockers.forEach { m.remove(it.location) } }

  // move empty spot to top row around the blockers
  val seen = mutableSetOf(emptySpot.location)
  val queue = priorityQueueOf(Comparator.comparing { it.first },0 to emptySpot.location)
  var newPoint = emptySpot.location
  var turns = -1
  while(queue.isNotEmpty()) {
    val (turn, current) = queue.poll()
    if(current.y == 0) {
      turns = turn
      newPoint = current
      break
    }
    adjacentCoordinates(current).filter { it in realMap }.filter { seen.add(it) }.map { turn+1 to it }.toCollection(queue)
  }
  if(turns < 0) error("did not find path")
  // move the empty spot adjacent to the target data
  turns += target.x - 1 - newPoint.x
  // Every move data to the left 5 steps and leaves the empty spot before the data
  // move the target to node (0,1)
  turns += (target.x-1)*5
  //make the last move from (0,1) to (0,0)
  return ++turns
}
