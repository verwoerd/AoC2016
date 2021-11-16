plugins {
  id("aoc.problem")
}
project.application.mainClass.set("MainKt")
dependencies {
  implementation(project(":day12"))
}
