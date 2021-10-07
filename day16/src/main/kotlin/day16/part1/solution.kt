package day16.part1

import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 07/10/2021
 */
fun part1(input: BufferedReader, diskSize: Int = 272): Any {
  var disk = input.readLine().toCharArray().toList()
  while (disk.size < diskSize) {
    disk = modifiedDragonCurve(disk)
  }
  return checkSum(disk.take(diskSize))
}

fun modifiedDragonCurve(a: List<Char>): List<Char> {
  val b = a.reversed().map {
    when (it) {
      '0' -> '1'
      '1' -> '0'
      else -> error("invalid input")
    }
  }
  return a + listOf('0') + b
}

tailrec fun checkSum(disk: List<Char>): String {
  val checksum = disk.windowed(size = 2, step = 2)
    .fold(mutableListOf<Char>()) { acc, list ->
      acc.also {
        it.add(
          when {
            list[0] == list[1] -> '1'
            else -> '0'
          }
        )
      }
    }
  return if (checksum.size % 2 == 1) checksum.joinToString("") else checkSum(checksum)
}
