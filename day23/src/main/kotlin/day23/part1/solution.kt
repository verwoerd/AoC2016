package day23.part1

import day12.part1.*
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 16/11/2021
 */
fun part1(input: BufferedReader, a: Int = 7): Any {
  val computer = Computer(a)
  computer.execute(input.lineSequence().toMutableList())
  return computer.a
}

val TOGGLE_REGEX = Regex("tgl (\\w+)")
val JNZ_REGISTER_REGEX = Regex("jnz (\\w) (\\w)")

data class Computer(
  var a: Int = 0,
  var b: Int = 0,
  var c: Int = 0,
  var d: Int = 0
) {
  fun execute(program: MutableList<String>) {
    var pc = 0
    while (pc < program.size) {
      val instruction = program[pc]
//      println("[$pc][$this] $instruction")
      when {
        COPY_REGEX_CONST.matches(instruction) -> {
          val (_, left, right) = COPY_REGEX_CONST.matchEntire(instruction)!!.groupValues
          setRegister(right, left.toInt())
        }
        COPY_REGEX_REGISTER.matches(instruction) -> {
          val (_, left, right) = COPY_REGEX_REGISTER.matchEntire(instruction)!!.groupValues
          setRegister(right, getRegister(left))
        }
        INC_REGEX.matches(instruction) -> {
          val (_, left) = INC_REGEX.matchEntire(instruction)!!.groupValues
          setRegister(left, getRegister(left) +1)
        }
        DEC_REGEX.matches(instruction) -> {
          val (_, left) = DEC_REGEX.matchEntire(instruction)!!.groupValues
          setRegister(left, getRegister(left) -1)
        }
        JNZ_REGEX.matches(instruction) -> {
          val (_, left, right) = JNZ_REGEX.matchEntire(instruction)!!.groupValues
          if(getRegister(left) != 0) {
            pc += right.toInt()
            continue
          }
        }
        JNZ_REGISTER_REGEX.matches(instruction) -> {
          val (_, left, right) = JNZ_REGISTER_REGEX.matchEntire(instruction)!!.groupValues
          if(getRegister(left) != 0) {
            pc += getRegister(right)
            continue
          }
        }
        TOGGLE_REGEX.matches(instruction) -> {
          val(_, a) = TOGGLE_REGEX.matchEntire(instruction)!!.groupValues
          val line =pc + getRegister(a)
          println("Toggline line $line")
          if(line in program.indices) {
            val operation = program[line]
            program[line] = when(operation.take(3)) {
              "inc" -> "dec"
              "dec" -> "inc"
              "jnz" -> "cpy"
              "cpy" -> "jnz"
              "tgl" -> "inc"
              else -> error("invalid oepration toggle $operation")
            } + operation.drop(3)
          }
        }
        else -> println("skipping instruction")
      }
      pc++
    }
  }

  private fun setRegister(name: String, value: Int) {
    when (name) {
      "a" -> a = value
      "b" -> b = value
      "c" -> c = value
      "d" -> d = value
//      else -> error("Invalid register $name")
    }
  }

  private fun getRegister(name: String) = when (name) {
    "a" -> a
    "b" -> b
    "c" -> c
    "d" -> d
    else -> name.toInt()
  }
}
