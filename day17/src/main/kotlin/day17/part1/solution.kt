package day17.part1

import Coordinate
import asHexString
import isInRange
import priorityQueueOf
import xIncrement
import yIncrement
import java.io.BufferedReader
import java.security.MessageDigest

/**
 * @author verwoerd
 * @since 11/11/2021
 */
fun part1(input: BufferedReader): Any {
  val startHash = input.readLine()
  return searchHashPath(startHash)
}

fun searchHashPath(startHash: String): String {
  val start = Coordinate(0, 0)
  val end = Coordinate(3,3)
  val charRange = 'b'..'f'
  val queue = priorityQueueOf<Pair<String, Coordinate>>(Comparator { o1, o2 -> o1.first.length.compareTo(o2.first.length) })
  queue.add(startHash to start)
  val md5 = MessageDigest.getInstance("MD5")


  while(queue.isNotEmpty()) {
    val (hash, location) = queue.poll()
    if(location == end) {
      return hash.drop(startHash.length)
    }
    val doors = md5.digest(hash.toByteArray()).asHexString().take(4)
    if(doors[0] in charRange) {
      (location - yIncrement).takeIf { it.isInRange(start, end) }?.also { queue.add(hash+'U' to it) }
    }
    if(doors[1] in charRange) {
      (location + yIncrement).takeIf { it.isInRange(start, end) }?.also { queue.add(hash+'D' to it) }
    }
    if(doors[2] in charRange) {
      (location - xIncrement).takeIf { it.isInRange(start, end)}?.also { queue.add(hash+'L' to it) }
    }
    if(doors[3] in charRange) {
      (location + xIncrement).takeIf { it.isInRange(start, end)}?.also { queue.add(hash+'R' to it) }
    }
  }
  error("no path found")
}
