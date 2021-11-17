plugins {
  id("aoc.problem")
}
project.application.mainClass.set("MainKt")

dependencies {
  implementation(project(":day23"))
  implementation(project(":day12"))
}
