package day17.part2

import Coordinate
import asHexString
import isInRange
import priorityQueueOf
import xIncrement
import yIncrement
import java.io.BufferedReader
import java.lang.Integer.max
import java.security.MessageDigest

/**
 * @author verwoerd
 * @since 11/11/2021
 */
fun part2(input: BufferedReader): Any {
  val startHash = input.readLine()
  return searchMaxHashPath(startHash)
}

fun searchMaxHashPath(startHash: String): Int {
  val start = Coordinate(0, 0)
  val end = Coordinate(3, 3)
  val charRange = 'b'..'f'
  var bestResult = 0
  val queue =
    priorityQueueOf<Pair<String, Coordinate>>(Comparator { o1, o2 -> o1.first.length.compareTo(o2.first.length) })
  queue.add(startHash to start)
  val md5 = MessageDigest.getInstance("MD5")

  while (queue.isNotEmpty()) {
    val (hash, location) = queue.poll()
    if (location == end) {
      bestResult = max(bestResult, hash.length - startHash.length)
      continue
    }
    val doors = md5.digest(hash.toByteArray()).asHexString().take(4)
    if (doors[0] in charRange) {
      (location - yIncrement).takeIf { it.isInRange(start, end) }?.also { queue.add(hash + 'U' to it) }
    }
    if (doors[1] in charRange) {
      (location + yIncrement).takeIf { it.isInRange(start, end) }?.also { queue.add(hash + 'D' to it) }
    }
    if (doors[2] in charRange) {
      (location - xIncrement).takeIf { it.isInRange(start, end) }?.also { queue.add(hash + 'L' to it) }
    }
    if (doors[3] in charRange) {
      (location + xIncrement).takeIf { it.isInRange(start, end) }?.also { queue.add(hash + 'R' to it) }
    }
  }
  return bestResult
}
