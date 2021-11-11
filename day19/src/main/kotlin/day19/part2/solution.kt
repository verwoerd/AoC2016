package day19.part2

import java.io.BufferedReader
import java.util.*

/**
 * @author verwoerd
 * @since 11/11/2021
 */
fun part2(input: BufferedReader): Any {
  val elfs = input.readLine().toLong()

  val left = (1..elfs / 2).map { it to 1 }.toCollection(LinkedList())
  val right = (elfs / 2 + 1..elfs).map { it to 1 }.toCollection(LinkedList())


  while (left.isNotEmpty() && right.isNotEmpty()) {
    val (elf, presents) = left.removeFirst()
    val (_, stolen) = right.removeFirst()

    right.addLast(elf to (presents + stolen))
    if((left.size + right.size) % 2  == 0) {
      left.addLast(right.removeFirst())
    }
  }
  println(left)
  println(right)

  return when {
    right.isNotEmpty() -> right.first.first
    else -> left.first.first
  }
}
