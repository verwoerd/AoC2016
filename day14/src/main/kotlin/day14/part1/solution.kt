package day14.part1

import asHexString
import java.io.BufferedReader
import java.security.MessageDigest

/**
 * @author verwoerd
 * @since 06/10/2021
 */
fun part1(input: BufferedReader): Any {
  val salt = input.readLine()
  val digest = MessageDigest.getInstance("MD5")
  val indexMap = mutableMapOf<Int, Pair<String, String?>>()

  return indexMap.findBit { index: Int ->
    digest.digest("$salt$index".toByteArray())!!.asHexString().let { it to it.isTripletString() }
  }
}

fun MutableMap<Int, Pair<String, String?>>.findBit(mappingFunction: (Int) -> Pair<String, String?>) =
  generateSequence(1) { it + 1 }
    .mapNotNull { index -> computeIfAbsent(index, mappingFunction).takeIf { it.second != null }?.let { index to it.second!![0] } }
    .filter { (index, char) ->
      (1..1000).any { step ->
        computeIfAbsent(step + index, mappingFunction)
          .takeIf { it.second != null }?.first?.isQuintupleString(char) ?: false
      }
    }
    .take(64).last().first

fun String.isTripletString() =
  windowed(3).find { it[0] == it[1] && it[1] == it[2] }

fun String.isQuintupleString(char: Char) =
  contains("$char$char$char$char$char")
