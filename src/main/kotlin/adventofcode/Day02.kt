package adventofcode

import org.openjdk.jmh.annotations.*
import java.util.concurrent.TimeUnit
import kotlin.math.abs

@State(Scope.Benchmark)
@Fork(1)
@Warmup(iterations = 5)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
class Day02 {

    var input = emptyList<String>()

    @Setup
    fun setUp() {
        input = readInput("Day02")
    }

    @Benchmark
    fun part1(): Int {
        val safeReports = input.filter { report ->
            val levels = report.split(' ').map { it.toInt() }.zipWithNext()
            val allValidDifference = levels.all { (currentLevel, nextLevel) ->
                abs(currentLevel - nextLevel) in 1..3
            }
            if (!allValidDifference) return@filter false

            return@filter levels.all allIncreasing@{ (currentLevel, nextLevel) ->
                currentLevel < nextLevel
            } || levels.all allDecreasing@{ (currentLevel, nextLevel) ->
                currentLevel > nextLevel
            }
        }
        return safeReports.count()
    }

    @Benchmark
    fun part1Impl2(): Int {
        var safeReports = 0
        input.forEach report@ { report ->
            var isIncreasing = false
            var isDecreasing = false
            val levels = report.split(' ').map { it.toInt() }
            for (i in 0 until levels.lastIndex) {
                val currentLevel = levels[i]
                val nextLevel = levels[i + 1]
                val difference = abs(currentLevel - nextLevel)
                when {
                    difference < 1 || difference > 3 -> return@report // Invalid difference
                    currentLevel == nextLevel -> return@report // Not increasing or decreasing
                    currentLevel < nextLevel -> {
                        if (isDecreasing) return@report // Increasing but already decreasing
                        isIncreasing = true
                    }
                    else -> {
                        if (isIncreasing) return@report // Decreasing but already increasing
                        isDecreasing = true
                    }
                }
                if (i == levels.lastIndex - 1) {
                    safeReports++ // All checks passed and at last level
                }
            }
        }
        return safeReports
    }

    @Benchmark
    fun part2(): Int {
        return input.size
    }
}

fun main() {
    val day02 = Day02()

    day02.input = readInput("Day02_test")
    check(day02.part1() == 2)

    day02.setUp()
    day02.part1().println()
    day02.part1Impl2().println()

    day02.input = readInput("Day02_test")
    check(day02.part2() == 4)

    day02.setUp()
    day02.part2().println()
}
