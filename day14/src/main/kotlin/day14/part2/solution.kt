package day14.part2

import asHexBytes
import asHexString
import day14.part1.findBit
import day14.part1.isTripletString
import java.io.BufferedReader
import java.security.MessageDigest

/**
 * @author verwoerd
 * @since 06/10/2021
 */
fun part2(input: BufferedReader): Any {
  val salt = input.readLine()
  val digest = MessageDigest.getInstance("MD5")
  val indexMap = mutableMapOf<Int, Pair<String, String?>>()
  return indexMap.findBit { index ->
    val hash = digest.digest("$salt$index".toByteArray())
    generateSequence(hash) { digest.digest(it.asHexBytes()) }.take(2017).last().asHexString()
      .let { it to it.isTripletString() }
  }
}
