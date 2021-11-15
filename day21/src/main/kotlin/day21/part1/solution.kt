package day21.part1

import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 15/11/2021
 */
fun part1(input: BufferedReader, start: CharSequence = "abcdefgh"): Any {
  val value = StringBuilder(start)
  return input.lineSequence().fold(value) { current, operation ->
    when {
      SWAP_POSITION_REGEX.matches(operation) -> {
        val (left, right) = SWAP_POSITION_REGEX.matchEntire(operation)!!.groupValues.drop(1).take(2).map { it.toInt() }
        val tmp = current[right]
        current[right] = current[left]
        current[left] = tmp
        current
      }
      SWAP_LETER_REGEX.matches(operation) -> {
        val (left, right) = SWAP_LETER_REGEX.matchEntire(operation)!!.groupValues.drop(1).take(2).map { it[0] }
        current.fold(StringBuilder()) { acc, c ->
          acc.append(
            when (c) {
              left -> right
              right -> left
              else -> c
            }
          )
        }
      }
      ROTATE_LEFT_REGEX.matches(operation) -> {
        val (left) = ROTATE_LEFT_REGEX.matchEntire(operation)!!.groupValues.drop(1).map { it.toInt() }
        current.rotateLeft(left)
      }
      ROTATE_RIGHT_REGEX.matches(operation) -> {
        val (right) = ROTATE_RIGHT_REGEX.matchEntire(operation)!!.groupValues.drop(1).map { it.toInt() }
        current.rotateRight(right)
      }
      ROTOATE_LETTER_REGEX.matches(operation) -> {
        val (char) = ROTOATE_LETTER_REGEX.matchEntire(operation)!!.groupValues.drop(1).take(1).map { it[0] }
        val index = current.indexOf(char)
        current.rotateRight(
          when (index) {
            in 0..3 -> 1 + index
            else -> 2 + index
          }
        )
      }
      REVERSE_THROUGH_REGEX.matches(operation) -> {
        val (left, right) = REVERSE_THROUGH_REGEX.matchEntire(operation)!!.groupValues.drop(1).take(2)
          .map { it.toInt() }
        StringBuilder().append(current.subSequence(0 until left))
          .append(current.subSequence(left..right).reversed())
          .append(current.subSequence(right + 1 until current.length))
      }
      MOVE_REGEX.matches(operation) -> {
        val (left, right) = MOVE_REGEX.matchEntire(operation)!!.groupValues.drop(1).take(2).map { it.toInt() }
        val tmp = current[left]
        current.deleteCharAt(left)
        current.insert(right, tmp)
      }
      else -> error("Unknown command $operation")
    }
  }
}

fun StringBuilder.rotateRight(amount: Int): StringBuilder =
  (amount % length).let { StringBuilder(dropLast(it)).insert(0, takeLast(it)) }

fun StringBuilder.rotateLeft(amount: Int): StringBuilder =
  (amount % length).let {  StringBuilder(drop(it)).append(take(it)) }


val SWAP_POSITION_REGEX = Regex("swap position (\\d+) with position (\\d+)")
val SWAP_LETER_REGEX = Regex("swap letter (\\w) with letter (\\w)")
var ROTATE_LEFT_REGEX = Regex("rotate left (\\d+) steps?")
var ROTATE_RIGHT_REGEX = Regex("rotate right (\\d+) steps?")
var ROTOATE_LETTER_REGEX = Regex("rotate based on position of letter (\\w)")
var REVERSE_THROUGH_REGEX = Regex("reverse positions (\\d+) through (\\d+)")
var MOVE_REGEX = Regex("move position (\\d+) to position (\\d+)")
