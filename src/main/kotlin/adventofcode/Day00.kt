package adventofcode

import org.openjdk.jmh.annotations.*
import java.util.concurrent.TimeUnit

@State(Scope.Benchmark)
@Fork(1)
@Warmup(iterations = 10)
@Measurement(iterations = 10, time = 1, timeUnit = TimeUnit.SECONDS)
class Day00 {

    var input = emptyList<String>()

    @Setup
    fun setUp() {
        input = readInput("Day00")
    }

    @Benchmark
    fun part1(): Int {
        return input.size
    }

    @Benchmark
    fun part2(): Int {
        return input.size
    }
}

fun main() {
    val day00 = Day00()

    day00.input = readInput("Day00_test_1")
    check(day00.part1() == -1)

    day00.input = readInput("Day00_test_2")
    check(day00.part2() == -1)

    day00.setUp()
    day00.part1().println()
    day00.part2().println()
}
