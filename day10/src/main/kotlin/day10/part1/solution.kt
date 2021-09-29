package day10.part1

import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 26/08/2021
 */
const val firstTarget = 17L
const val secondTarget = 61L

fun part1(input: BufferedReader): Any {
  return executeBotCompare(input, false).first
}

fun executeBotCompare(input: BufferedReader, fullSearch: Boolean = false): Pair<Long, Map<Long, Chip>> {
  val (chips, bots) = input.lineSequence().fold(mutableListOf<Chip>() to mutableListOf<Bot>()) { (chips, bots), line ->
    when {
      CHIP_REGEX.matches(line) -> {
        val (value, bot) = CHIP_REGEX.matchEntire(line)!!.groupValues.drop(1).map { it.toLong() }
        chips += Chip(value, bot)
      }
      BOT_REGEX.matches(line) -> {
        val (bot, low, high) = BOT_REGEX.matchEntire(line)!!.groupValues.drop(1).map { it.toLong() }
        bots += Bot(bot, low, high)
      }
      BOT_OUTPUT_REGEX.matches(line) -> {
        val (bot, low, high) = BOT_OUTPUT_REGEX.matchEntire(line)!!.groupValues.drop(1).map { it.toLong() }
        bots += Bot(bot, null, high, low)
      }
      BOT_DOUBLE_REGEX.matches(line) -> {
        val (bot, low, high) = BOT_DOUBLE_REGEX.matchEntire(line)!!.groupValues.drop(1).map { it.toLong() }
        bots += Bot(bot, null, null, low, high)
      }
      else -> error("can't match $line")
    }
    chips to bots
  }
  val botsMap = bots.groupBy { it.id }.mapValues { it.value.first() }
  val outputs = mutableMapOf<Long, Chip>()


  val botsHoldingChips = chips.groupBy { it.current }.toMutableMap().withDefault { emptyList() }
  val queue = botsHoldingChips.filter { it.value.size == 2 }.toMutableMap()
  while (queue.isNotEmpty()) {
    val currentBotId = queue.keys.first()
    val currentChips = queue.remove(currentBotId)!!


    val low = currentChips.minByOrNull { it.id }!!
    val high = currentChips.maxByOrNull { it.id }!!
    if (!fullSearch && low.id == firstTarget && high.id == secondTarget) {
      return currentBotId to outputs
    }
    val bot = botsMap[currentBotId]!!
    bot.low.takeIf { it != null }?.let {
      botsHoldingChips[it] = botsHoldingChips.getValue(it) + low
      if (botsHoldingChips.getValue(it).size == 2) {
        queue[it] = botsHoldingChips.getValue(it)
      }
    }
    bot.high.takeIf { it != null }?.let {
      botsHoldingChips[it] = botsHoldingChips.getValue(it) + high
      if (botsHoldingChips.getValue(it).size == 2) {
        queue[it] = botsHoldingChips.getValue(it)
      }
    }
    bot.lowOutput.takeIf { it != null }?.let {
      outputs[it] = low
    }
    bot.highOutput.takeIf { it != null }?.let {
      outputs[it] = high
    }
  }
  return -1L to outputs
}


val CHIP_REGEX = Regex("value (\\d+) goes to bot (\\d+)")
val BOT_REGEX = Regex("bot (\\d+) gives low to bot (\\d+) and high to bot (\\d+)")
val BOT_OUTPUT_REGEX = Regex("bot (\\d+) gives low to output (\\d+) and high to bot (\\d+)")
val BOT_DOUBLE_REGEX = Regex("bot (\\d+) gives low to output (\\d+) and high to output (\\d+)")

data class Bot(
  val id: Long,
  val low: Long?,
  val high: Long?,
  val lowOutput: Long? = null,
  val highOutput: Long? = null
)

data class Chip(
  val id: Long,
  val current: Long,
)
