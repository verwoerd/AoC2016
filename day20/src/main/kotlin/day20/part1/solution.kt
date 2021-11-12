package day20.part1

import priorityQueueOf
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 12/11/2021
 */
fun part1(input: BufferedReader): Any {
  val ranges = readRangeInputAndCombine(input)
  return when {
    ranges.first().first > 0 -> 0
    else -> ranges.first().last + 1
  }
}

fun readRangeInputAndCombine(input: BufferedReader): MutableSet<LongRange> {
  val ranges = input.lineSequence()
    .map { line -> line.split('-').take(2).map { it.toLong() } }
    .map { (left, right) -> left..right }
    .toCollection(priorityQueueOf(Comparator { o1, o2 -> o1.first.compareTo(o2.first) }))
  val optimizedRanges = mutableSetOf<LongRange>()
  while (ranges.isNotEmpty()) {
    var current = ranges.poll()
    while (ranges.isNotEmpty() && (ranges.peek().first in current || ranges.peek().first == current.last + 1)) {
      val combine = ranges.poll()
      if (combine.last !in current) {
        current = current.first..combine.last
      }
    }
    optimizedRanges.add(current)
  }
  return optimizedRanges
}
