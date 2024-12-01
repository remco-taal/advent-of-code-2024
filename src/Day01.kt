import kotlin.math.absoluteValue

fun main() {
    fun part1(input: List<String>): Int {
        val leftLocationIds = mutableListOf<Int>()
        val rightLocationIds = mutableListOf<Int>()
        input.forEach { line ->
            val locationIds = line.split("   ")
            leftLocationIds.add(locationIds.first().toInt())
            rightLocationIds.add(locationIds.last().toInt())
        }

        leftLocationIds.sort()
        rightLocationIds.sort()

        return leftLocationIds.withIndex().sumOf { (index, leftLocationId) ->
            val distance = (leftLocationId - rightLocationIds[index]).absoluteValue
            return@sumOf distance
        }
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 11)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
