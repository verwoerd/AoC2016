package day01.part2

import Coordinate
import day01.part1.Direction
import manhattanDistance
import origin
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 05/12/2020
 */
fun part2(input: BufferedReader): Any {
  val locations = mutableSetOf<Coordinate>()
  return input.readLine().split(',').map { it.trim() }
    .runningFold(Triple(Direction.NORTH, Coordinate(0, 0), listOf<Coordinate>())) { (direction, coordinate, _), current ->
      val (newDirection, newCoordinate) = direction.navigate(current, coordinate)
      Triple(newDirection, newCoordinate, when (newDirection) {
        Direction.NORTH -> (1 ..current.drop(1).toInt()).map {coordinate.plusY(it)}
        Direction.SOUTH -> (1 ..current.drop(1).toInt()).map  {coordinate.plusY(-1*it)}
        Direction.EAST -> (1 ..current.drop(1).toInt()).map  {coordinate.plusX(it)}
        Direction.WEST -> (1 ..current.drop(1).toInt()).map  {coordinate.plusX(-1*it)}
      })
    }.flatMap {it.third}
    .first { !locations.add(it)  }.let { manhattanDistance(origin, it) }
}
