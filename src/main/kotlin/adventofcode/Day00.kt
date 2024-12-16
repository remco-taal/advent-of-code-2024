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
        input = readInputLines(name)
    }

    @Benchmark
    fun part1(): Int {
        return input.size
    }

    @Benchmark
    fun part2(): Int {
        return input.size
    }

    companion object {
        val name: String = Day00::class.java.simpleName
    }
}

fun main() {
    val day00 = Day00()

    day00.input = readInputLines("${Day00.name}_test_1")
    check(day00.part1() == -1)

    day00.input = readInputLines(Day00.name)
    day00.part1().println()

    day00.input = readInputLines("${Day00.name}_test_2")
    check(day00.part2() == -1)

    day00.input = readInputLines(Day00.name)
    day00.part2().println()
}
