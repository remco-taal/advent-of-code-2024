import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        val (leftLocationIds, rightLocationIds) = mapInputToPairOfLocationIds(input)
        return leftLocationIds
            .sorted()
            .zip(rightLocationIds.sorted())
            .sumOf { (leftLocationId, rightLocationId) ->
                val distance = abs(leftLocationId - rightLocationId)
                return@sumOf distance
            }
    }

    fun part2(input: List<String>): Int {
        val (leftLocationIds, rightLocationIds) = mapInputToPairOfLocationIds(input)
        return leftLocationIds.sumOf { leftLocationId ->
            val similarityScore = leftLocationId * rightLocationIds.count { it == leftLocationId }
            return@sumOf similarityScore
        }
    }

    val testInput = readInput("Day01_test")
    check(part1(testInput) == 11)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}

private fun mapInputToPairOfLocationIds(input: List<String>): Pair<List<Int>, List<Int>> = input.map { line ->
    val leftLocationId = line.substringBefore(' ').toInt()
    val rightLocationId = line.substringAfterLast(' ').toInt()
    leftLocationId to rightLocationId
}.unzip()
