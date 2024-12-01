import kotlin.math.absoluteValue

private typealias LocationId = Int

fun main() {
    fun part1(input: List<String>): Int {
        val (leftLocationIds, rightLocationIds) = mapInputToPairOfLocationsIds(input)

        leftLocationIds.sort()
        rightLocationIds.sort()

        return leftLocationIds.withIndex().sumOf { (index, leftLocationId) ->
            val distance = (leftLocationId - rightLocationIds[index]).absoluteValue
            return@sumOf distance
        }
    }

    fun part2(input: List<String>): Int {
        val (leftLocationIds, rightLocationIds) = mapInputToPairOfLocationsIds(input)
        return leftLocationIds.sumOf { leftLocationId ->
            val similarityScore = leftLocationId * rightLocationIds.count { it == leftLocationId }
            return@sumOf similarityScore
        }
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 11)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}

private fun mapInputToPairOfLocationsIds(input: List<String>): Pair<Array<LocationId>, Array<LocationId>> {
    val leftLocationIds = Array(input.size) { 0 }
    val rightLocationIds = Array(input.size) { 0 }
    input.forEachIndexed() { index, line ->
        val locationIds = line.split("   ")
        leftLocationIds[index] = locationIds.first().toInt()
        rightLocationIds[index] = locationIds.last().toInt()
    }
    return leftLocationIds to rightLocationIds
}
