package adventofcode

import org.openjdk.jmh.annotations.*
import java.util.concurrent.TimeUnit

@State(Scope.Benchmark)
@Fork(1)
@Warmup(iterations = 5)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
class Day05 {

    var input = emptyList<String>()

    @Setup
    fun setUp() {
        input = readInputLines("Day05")
    }

    @Benchmark
    fun part1(): Int {
        val pageOrderingRules = input.takeWhile { it.isNotBlank() }
        val pageOrderingRulesMap = pageOrderingRules.fold(
            initial = mutableMapOf<Int, MutableSet<Int>>()
        ) { map, pageOrderingRule ->
            val pair = pageOrderingRule.split('|').let { it[0].toInt() to it[1].toInt() }
            map.apply { getOrPut(pair.first) { mutableSetOf(pair.second) }.add(pair.second) }
        }

        val updates = input
            .slice(pageOrderingRules.size + 1..input.lastIndex)
            .map { update ->
                update.split(',').map { page ->
                    page.toInt()
                }
            }
        val updatesInRightOrder = updates.filter { update ->
            update.withIndex().all { (index, pages) ->
                val pagesBefore = update.slice(0..index)
                val pagesAfter = pageOrderingRulesMap[pages] ?: setOf()
                pagesBefore.all { it !in pagesAfter }
            }
        }
        return updatesInRightOrder.sumOf { page ->
            val indexOfMiddlePageNumber = when {
                page.size % 2 == 0 -> (page.size / 2) -1
                else -> page.size / 2
            }
            page[indexOfMiddlePageNumber]
        }
    }

    @Benchmark
    fun part2(): Int {
        return input.size
    }
}

fun main() {
    val day05 = Day05()

    day05.input = readInputLines("Day05_test")
    check(day05.part1() == 143)

    day05.input = readInputLines("Day05")
    day05.part1().println()

    day05.input = readInputLines("Day05_test")
    check(day05.part2() == -1)

    day05.input = readInputLines("Day05")
    day05.part2().println()
}
