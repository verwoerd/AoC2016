package day19.part1

import java.io.BufferedReader
import java.util.*
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.log2

/**
 * @author verwoerd
 * @since 11/11/2021
 */
fun part1(input: BufferedReader): Any {
  val elfs = input.readLine().toLong()

  val list = (1..elfs).map {
    it to 1
  }.toCollection(LinkedList())


  while (list.size != 1) {
    val (elf, presents) = list.removeFirst()
    val (_, stolen) = list.removeFirst()
    list.addLast(elf to (presents + stolen))
  }

  return list.first.first
}
