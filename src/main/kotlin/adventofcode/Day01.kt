package adventofcode

import org.openjdk.jmh.annotations.*
import java.util.concurrent.TimeUnit
import kotlin.math.abs

@State(Scope.Benchmark)
@Fork(1)
@Warmup(iterations = 5)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
class Day01 {

    var input = emptyList<String>()

    @Setup
    fun setUp() {
        input = readInputLines("Day01")
    }

    @Benchmark
    fun part1(): Int {
        val (leftLocationIds, rightLocationIds) = input.toPairOfLocationIds()
        return leftLocationIds
            .sorted()
            .zip(rightLocationIds.sorted())
            .sumOf { (leftLocationId, rightLocationId) ->
                val distance = abs(leftLocationId - rightLocationId)
                return@sumOf distance
            }
    }

    @Benchmark
    fun part2(): Int {
        val (leftLocationIds, rightLocationIds) = input.toPairOfLocationIds()
        return leftLocationIds.sumOf { leftLocationId ->
            val similarityScore = leftLocationId * rightLocationIds.count { it == leftLocationId }
            return@sumOf similarityScore
        }
    }

    @Benchmark
    fun part2Impl2(): Int {
        val (leftLocationIds, rightLocationIds) = input.toPairOfLocationIds()
        val countMap = rightLocationIds.groupingBy { it }.eachCount()
        return leftLocationIds.sumOf { leftLocationId ->
            val similarityScore = leftLocationId * (countMap[leftLocationId] ?: 0)
            return@sumOf similarityScore
        }
    }

    private fun List<String>.toPairOfLocationIds(): Pair<List<Int>, List<Int>> = map { line ->
        val leftLocationId = line.substringBefore(' ').toInt()
        val rightLocationId = line.substringAfterLast(' ').toInt()
        leftLocationId to rightLocationId
    }.unzip()
}

fun main() {
    val day01 = Day01()

    day01.input = readInputLines("Day01_test")
    check(day01.part1() == 11)

    day01.input = readInputLines("Day01_test")
    check(day01.part2() == 31)

    day01.setUp()
    day01.part1().println()
    day01.part2().println()
    day01.part2Impl2().println()
}
