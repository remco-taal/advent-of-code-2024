package adventofcode

import org.openjdk.jmh.annotations.*
import java.util.concurrent.TimeUnit

private typealias Update = List<Int>

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
        val (pageOrderingRules, updates) = parseInput()
        val goodUpdates = getGoodUpdates(updates, pageOrderingRules)
        return goodUpdates.sumOf { page -> page.getMiddlePage() }
    }

    @Benchmark
    fun part2(): Int {
        val (pageOrderingRules, updates) = parseInput()
        val goodUpdates = getGoodUpdates(updates, pageOrderingRules)
        val badUpdates = updates.minus(goodUpdates.toSet())
        val correctedUpdates = badUpdates.map { badUpdate ->
            badUpdate.sortedWith { pageOne, pageTwo ->
                val pagesAfterPageOne = pageOrderingRules[pageOne]
                return@sortedWith when {
                    pagesAfterPageOne.isNullOrEmpty() -> 0
                    pageTwo in pagesAfterPageOne -> -1
                    else -> 1
                }
            }
        }
        return correctedUpdates.sumOf { correctedUpdate -> correctedUpdate.getMiddlePage() }
    }

    private fun parseInput(): ParsedInput {
        val topSection = input.takeWhile { it.isNotBlank() }
        val pageOrderingRules = topSection.fold(
            initial = mutableMapOf<Int, MutableSet<Int>>()
        ) { map, rule ->
            val pair = rule.split('|').let { it[0].toInt() to it[1].toInt() }
            map.apply { getOrPut(pair.first) { mutableSetOf(pair.second) }.add(pair.second) }
        }
        val updates = input
            .slice(topSection.size + 1..input.lastIndex)
            .map { update ->
                update.split(',').map { page ->
                    page.toInt()
                }
            }
        return ParsedInput(pageOrderingRules, updates)
    }

    private fun Update.getMiddlePage(): Int {
        val indexOfMiddlePage = when {
            size % 2 == 0 -> (size / 2) - 1
            else -> size / 2
        }
        return this[indexOfMiddlePage]
    }

    private fun getGoodUpdates(
        updates: List<List<Int>>,
        pageOrderingRules: Map<Int, Set<Int>>
    ) = updates.filter { update ->
        update.withIndex().all { (index, page) ->
            val pagesBefore = update.slice(0..index)
            val pagesAfter = pageOrderingRules[page] ?: setOf()
            pagesBefore.all { it !in pagesAfter }
        }
    }

    private data class ParsedInput(val pageOrderingRules: Map<Int, Set<Int>>, val updates: List<List<Int>>)
}

fun main() {
    val day05 = Day05()

    day05.input = readInputLines("Day05_test")
    check(day05.part1() == 143)

    day05.input = readInputLines("Day05")
    day05.part1().println()

    day05.input = readInputLines("Day05_test")
    check(day05.part2() == 123)

    day05.input = readInputLines("Day05")
    day05.part2().println()
}
