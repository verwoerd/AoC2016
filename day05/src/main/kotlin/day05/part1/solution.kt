package day05.part1

import java.io.BufferedReader
import java.security.MessageDigest

/**
 * @author verwoerd
 * @since 05/12/2020
 */
@ExperimentalUnsignedTypes
fun part1(input: BufferedReader): Any {
  val codeword = input.readLine()
  return generate(codeword)
}

val md5Instance: MessageDigest by lazy { MessageDigest.getInstance("MD5") }

@ExperimentalUnsignedTypes
fun generate(code: String) = (0..Int.MAX_VALUE).asSequence()
  .map { md5Instance.digest((code + it).toByteArray()).toUByteArray() }
  .filter { (first, second, third) -> first == UByte.MIN_VALUE && second == UByte.MIN_VALUE && third < 16u }
  .map { (_, _, value) -> value.toString(16)}
  .take(8).joinToString("")
