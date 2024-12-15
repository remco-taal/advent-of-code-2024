package adventofcode

import org.openjdk.jmh.annotations.*
import java.util.concurrent.TimeUnit

@State(Scope.Benchmark)
@Fork(1)
@Warmup(iterations = 5)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
class Day00 {

    var input = emptyList<String>()

    @Setup
    fun setUp() {
        input = readInputLines("Day00")
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
    val day04 = Day04()

    day04.input = readInputLines("Day00_test_1")
    check(day04.part1() == -1)

    day04.input = readInputLines("Day00")
    day04.part1().println()

    day04.input = readInputLines("Day00_test_2")
    check(day04.part2() == -1)

    day04.input = readInputLines("Day00")
    day04.part2().println()
}
