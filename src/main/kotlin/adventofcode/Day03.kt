package adventofcode

import org.openjdk.jmh.annotations.*
import java.util.concurrent.TimeUnit

@State(Scope.Benchmark)
@Fork(1)
@Warmup(iterations = 5)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
class Day03 {

    var input = ""

    @Setup
    fun setUp() {
        input = readInputText("Day03")
    }

    @Benchmark
    fun part1(): Int {
        return getMulSum(input)
    }

    @Benchmark
    fun part2(): Int {
        return input.split("do").filterNot { it.startsWith("n't()") }.sumOf { getMulSum(it) }
    }

    private fun getMulSum(line: String): Int {
        return """mul\((\d{1,3},\d{1,3})\)""".toRegex().findAll(line).sumOf { matchResult ->
            val digits = matchResult.groupValues.last().split(',').let { digits ->
                digits[0].toInt() to digits[1].toInt()
            }
            digits.first * digits.second
        }
    }
}

fun main() {
    val day03 = Day03()

    day03.input = readInputText("Day03_test_1")
    check(day03.part1() == 161)

    day03.input = readInputText("Day03_test_2")
    check(day03.part2() == 48)

    day03.setUp()
    day03.part1().println()
    day03.part2().println()
}
