package day12.part1

import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 05/10/2021
 */
fun part1(input: BufferedReader): Any {
  val program = input.readLines()
  val computer = Computer()
  computer.execute(program)
  return computer.a
}


val COPY_REGEX_CONST = Regex("cpy (\\d+) (\\w)")
val COPY_REGEX_REGISTER = Regex("cpy (\\w) (\\w)")
val INC_REGEX = Regex("inc (\\w)")
val DEC_REGEX = Regex("dec (\\w)")
val JNZ_REGEX = Regex("jnz (\\w) (-?\\d+)")

data class Computer(
  var a: Int = 0,
  var b: Int = 0,
  var c: Int = 0,
  var d: Int = 0
) {
  fun execute(program: List<String>) {
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
      else -> error("Invalid register $name")
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
