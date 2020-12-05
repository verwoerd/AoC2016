package day05.part2

import day05.part1.md5Instance
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 05/12/2020
 */
@ExperimentalUnsignedTypes
fun part2(input: BufferedReader): Any {
  val codeword = input.readLine()
  val codeTable = generate(codeword)
  return codeTable.keys.sorted().joinToString("") { codeTable[it]!!.toString(16) }
}

@ExperimentalUnsignedTypes
val seen = mutableSetOf<UByte>()

@ExperimentalUnsignedTypes
fun generate(code: String) = (0..Int.MAX_VALUE).asSequence()
  .map { md5Instance.digest((code + it).toByteArray()).toUByteArray().take(4) }
  .filter { (first, second, third) -> first == UByte.MIN_VALUE && second == UByte.MIN_VALUE && third < 8u }
  .map { (_, _, index, value) -> index to value / 16u }
  .takeWhile { (seen.size != 8) }
  .filter { seen.add(it.first) }
  .associateBy({ it.first }) { it.second }


