package adventofcode

import org.openjdk.jmh.annotations.*
import java.util.concurrent.TimeUnit

@State(Scope.Benchmark)
@Fork(1)
@Warmup(iterations = 5)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
class Day03 {

    var input = emptyList<String>()

    @Setup
    fun setUp() {
        input = readInput("Day03")
    }

    @Benchmark
    fun part1(): Int {
        return input.sumOf { line ->
            getMulSumOfLine(line)
        }
    }

    @Benchmark
    fun part2(): Int {
        val stack = ArrayDeque<StringBuilder>()
        stack.add(StringBuilder())
        stack.add(StringBuilder())

        input.forEach { line ->
            var isEnabled = true
            line.forEach { char ->
                val builder = if (isEnabled) stack.first() else stack.last()
                builder.append(char)
                when {
                    builder.endsWith(INSTRUCTION_DONT) -> isEnabled = false
                    builder.endsWith(INSTRUCTION_DO) -> isEnabled = true
                }
            }
        }
        return getMulSumOfLine(stack.first().toString())
    }

    private fun getMulSumOfLine(line: String): Int {
        return Regex(MUL_PATTERN).findAll(line).sumOf { matchResult ->
            val digits = matchResult.groupValues.last().split(',').let { digits ->
                digits[0].toInt() to digits[1].toInt()
            }
            digits.first * digits.second
        }
    }

    companion object {
        const val INSTRUCTION_DO = "do()"
        const val INSTRUCTION_DONT = "don't()"
        const val MUL_PATTERN = """mul\((\d{1,3},\d{1,3})\)"""
    }
}

fun main() {
    val day03 = Day03()

    day03.input = readInput("Day03_test_1")
    check(day03.part1() == 161)

    day03.input = readInput("Day03_test_2")
    check(day03.part2() == 48)

    day03.setUp()
    day03.part1().println()
    day03.part2().println()
}
