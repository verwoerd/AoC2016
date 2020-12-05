package day01.part1

import Coordinate
import manhattanDistance
import origin
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 05/12/2020
 */
fun part1(input: BufferedReader): Any {
  return input.readLine().split(',').map { it.trim() }
    .fold(Direction.NORTH to Coordinate(0, 0)) { (direction, coordinate), current ->
     direction.navigate(current, coordinate)
    }.second.let { manhattanDistance(origin, it) }
}

enum class Direction {
  NORTH, SOUTH, WEST, EAST;

  fun turnLeft() = when (this) {
    NORTH -> EAST
    EAST -> SOUTH
    SOUTH -> WEST
    WEST -> NORTH
  }

  fun turnRight() = when (this) {
    NORTH -> WEST
    WEST -> SOUTH
    SOUTH -> EAST
    EAST -> NORTH
  }

  fun turn(direction: Char) = when (direction) {
    'L' -> turnLeft()
    'R' -> turnRight()
    else -> error("Invalid")
  }

  fun navigate(current: String, coordinate: Coordinate): Pair<Direction, Coordinate> {
    val newDirection = turn(current.first())
    return newDirection to when (newDirection) {
      NORTH -> coordinate.plusY(current.drop(1).toInt())
      SOUTH -> coordinate.plusY(-1 * current.drop(1).toInt())
      EAST -> coordinate.plusX(current.drop(1).toInt())
      WEST -> coordinate.plusX(-1 * current.drop(1).toInt())
    }
  }
}
