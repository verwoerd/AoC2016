package day11.part1

import priorityQueueOf
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 29/09/2021
 */
fun part1(input: BufferedReader): Any {
  return findBfsSolution(parseInput(input))
}

fun parseInput(input: BufferedReader): Scenario {
  val floors = input.readLines().mapIndexed { floor, line ->
    val microchips = MICROCHIP.findAll(line).map { Chip(it.groupValues[1]) }.toList()
    val generators = GENERATOR.findAll(line).map { Generator(it.groupValues[1]) }.toList()
    Floor(floor + 1, microchips, generators)
  }
  return Scenario(floors[0], floors[1], floors[2], floors[3])
}

fun findBfsSolution(scenario: Scenario): Any {
  val nextGenerations = scenario.nextGenerations()
  val seen = mutableSetOf(*nextGenerations, scenario)
  val queue = priorityQueueOf(Comparator { a, b -> a.step.compareTo(b.step) }, *nextGenerations)

  while (queue.isNotEmpty()) {
    val current = queue.poll()
    if (current.isFinished()) {
      return current.step
    }
    current.nextGenerations().filter { seen.add(it) }.toCollection(queue)
  }

  error("No solution found!")
}

val MICROCHIP = Regex("a (\\w+)-compatible microchip")
val GENERATOR = Regex("a (\\w+) generator")

interface Component {
  val name: String
}

data class Chip(override val name: String) : Component
data class Generator(override val name: String) : Component
data class Floor(val number: Int, val chips: List<Chip>, val generators: List<Generator>) {
  fun isSafe() =
    generators.isEmpty() || chips.all { chip -> generators.any { it.name == chip.name } }

  private fun isSafeWithout(componentList: List<Component>) =
    generators.all { it in componentList } || chips.filter { it !in componentList }
      .all { chip -> generators.filter { it !in componentList }.any { it.name == chip.name } }

  fun getSafeIterations(): List<List<Component>> {
    val components = generators + chips
    return components.flatMapIndexed { index, component ->
      val cases = components.drop(index + 1).map { listOf(component, it) } + listOf(listOf(component))
      cases.filter { isSafeWithout(it) }
    }
  }

  fun isEmpty() = chips.isEmpty() && generators.isEmpty()

  fun remove(componentList: List<Component>) = copy(
    chips = chips.filter { it !in componentList },
    generators = generators.filter { it !in componentList }
  )

  fun add(componentList: List<Component>) = copy(
    chips = chips + componentList.filterIsInstance<Chip>(),
    generators = generators + componentList.filterIsInstance<Generator>()
  )

  fun countPairs() = chips.count { chip -> generators.any { it.name == chip.name } }
  fun findSingletons() =
    chips.filter { chip -> generators.none { it.name == chip.name } } + generators.filter { generator -> chips.none { it.name == generator.name } }
}

data class Scenario(
  val firstFloor: Floor,
  val secondFloor: Floor,
  val thirdFloor: Floor,
  val fourthFloor: Floor,
  val currentFloor: Int = 1
) {
  var step: Int = 0
  private val types by lazy { (firstFloor.chips + secondFloor.chips + thirdFloor.chips + fourthFloor.chips).map { it.name } }
  val equalityMap by lazy {
    types.map { name -> findComponentLocation(name) { it.chips } to findComponentLocation(name) { it.generators } }
      .groupBy { it.first }
      .mapValues { entry -> entry.value.map { it.second }.sorted() }
  }


  fun nextGenerations() =
    currentFloor().getSafeIterations().flatMap { moveElevator(it) }.filter { it.isSafe() }.toTypedArray()

  override fun hashCode(): Int {
    return equalityMap.hashCode()
  }

  override fun equals(other: Any?): Boolean {
    if (other !is Scenario) return false
    return currentFloor == other.currentFloor && equalityMap == other.equalityMap
  }

  private fun isSafe() = firstFloor.isSafe() && secondFloor.isSafe() && thirdFloor.isSafe() && fourthFloor.isSafe()

  fun isFinished() = firstFloor.isEmpty() && secondFloor.isEmpty() && thirdFloor.isEmpty()

  private fun currentFloor() = when (currentFloor) {
    1 -> firstFloor
    2 -> secondFloor
    3 -> thirdFloor
    4 -> fourthFloor
    else -> error("unknown floor")
  }

  private fun moveElevator(components: List<Component>) = when (currentFloor) {
    1 -> listOf(
      copy(
        firstFloor = firstFloor.remove(components),
        secondFloor = secondFloor.add(components),
        currentFloor = 2
      )
    )
    2 -> listOfNotNull(
      copy(
        thirdFloor = thirdFloor.add(components),
        secondFloor = secondFloor.remove(components),
        currentFloor = 3
      ),
      copy(
        firstFloor = firstFloor.add(components),
        secondFloor = secondFloor.remove(components),
        currentFloor = 1
      ).takeIf { !firstFloor.isEmpty() }
    )
    3 -> listOfNotNull(
      copy(
        thirdFloor = thirdFloor.remove(components),
        fourthFloor = fourthFloor.add(components),
        currentFloor = 4
      ),
      copy(
        thirdFloor = thirdFloor.remove(components),
        secondFloor = secondFloor.add(components),
        currentFloor = 2
      ).takeIf { !firstFloor.isEmpty() || !secondFloor.isEmpty() }
    )
    4 -> listOf(
      copy(
        thirdFloor = thirdFloor.add(components),
        fourthFloor = fourthFloor.remove(components),
        currentFloor = 3
      )
    )
    else -> error("unknown floor")
  }.map { it.also { it.step = step + 1 } }

  private fun findComponentLocation(name: String, selector: (Floor) -> List<Component>) = when {
    selector(firstFloor).matches(name) -> 1
    selector(secondFloor).matches(name) -> 2
    selector(thirdFloor).matches(name) -> 3
    selector(fourthFloor).matches(name) -> 4
    else -> error("chip not found $name")
  }

  private fun List<Component>.matches(name: String): Boolean = any { it.name == name }
}
