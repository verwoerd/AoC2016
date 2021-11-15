package day21.part2

import day21.part1.*
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 15/11/2021
 */
fun part2(input: BufferedReader, start: CharSequence = "fbgdceah"): Any {
  val value = StringBuilder(start)
  return input.readLines().foldRight(value) { operation, current ->
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
        current.rotateRight(left)
      }
      ROTATE_RIGHT_REGEX.matches(operation) -> {
        val (right) = ROTATE_RIGHT_REGEX.matchEntire(operation)!!.groupValues.drop(1).map { it.toInt() }
        current.rotateLeft(right)
      }
      ROTOATE_LETTER_REGEX.matches(operation) -> {
        val (char) = ROTOATE_LETTER_REGEX.matchEntire(operation)!!.groupValues.drop(1).take(1).map { it[0] }
        val index = current.indexOf(char)
        current.rotateLeft(
          when (index) {
            in 1 until current.length step 2 -> index /2 + 1
            in 2 until current.length step 2 ->  index/2 + 5
            else -> 1
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
        val tmp = current[right]
        current.deleteCharAt(right)
        current.insert(left, tmp)
      }
      else -> error("Unknown command $operation")
    }
  }
}
