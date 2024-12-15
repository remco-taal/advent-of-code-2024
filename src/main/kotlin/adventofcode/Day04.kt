package adventofcode

import org.openjdk.jmh.annotations.*
import java.util.concurrent.TimeUnit

private typealias Grid = List<CharArray>

@State(Scope.Benchmark)
@Fork(1)
@Warmup(iterations = 5)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
class Day04 {

    var input = emptyList<String>()

    @Setup
    fun setUp() {
        input = readInputLines("Day04")
    }

    @Benchmark
    fun part1(): Int {
        val grid = input.map { it.toCharArray() }

        var totalXmas = 0
        grid.forEachIndexed yAxis@{ y, chars ->
            chars.forEachIndexed xAxis@{ x, char ->
                if (char != 'X') return@xAxis

                // Before
                if (grid.getBeforeOrNull(x, y) == MAS) {
                    totalXmas++
                }

                // Top left
                if (grid.getTopLeftOrNull(x, y, 3) == MAS) {
                    totalXmas++
                }

                // Top
                if (grid.getTopOrNull(x, y) == MAS) {
                    totalXmas++
                }

                // Top right
                if (grid.getTopRightOrNull(x, y, 3) == MAS) {
                    totalXmas++
                }

                // After
                if (grid.getAfterOrNull(x, y) == MAS) {
                    totalXmas++
                }

                // Bottom right
                if (grid.getBottomRightOrNull(x, y, 3) == MAS) {
                    totalXmas++
                }

                // Bottom
                if (grid.getBottomOrNull(x, y) == MAS) {
                    totalXmas++
                }

                // Bottom left
                if (grid.getBottomLeftOrNull(x, y, 3) == MAS) {
                    totalXmas++
                }
            }
        }
        return totalXmas
    }

    @Benchmark
    fun part2(): Int {
        val grid = input.map { it.toCharArray() }

        var `totalX-mas` = 0
        grid.forEachIndexed yAxis@{ y, chars ->
            chars.forEachIndexed xAxis@{ x, char ->
                if (char != 'A') return@xAxis

                val topLeft = grid.getTopLeftOrNull(x, y, 1) ?: return@xAxis
                val topRight = grid.getTopRightOrNull(x, y, 1) ?: return@xAxis
                val bottomLeft = grid.getBottomLeftOrNull(x, y, 1) ?: return@xAxis
                val bottomRight = grid.getBottomRightOrNull(x, y, 1) ?: return@xAxis

                if (topLeft == "M" && bottomRight == "S" || topLeft == "S" && bottomRight == "M") {
                    if (topRight == "M" && bottomLeft == "S" || topRight == "S" && bottomLeft == "M") {
                        `totalX-mas`++
                    }
                }
            }
        }

        return `totalX-mas`
    }

    private fun Grid.getTopOrNull(x: Int, y: Int): String? {
        if (y - 3 < 0) return null

        return buildString {
            this@getTopOrNull
                .slice(y - 3 until y)
                .reversed()
                .forEach { append(it[x]) }
        }
    }

    private fun Grid.getTopRightOrNull(x: Int, y: Int, totalChars: Int): String? {
        if (y - totalChars < 0 || x + totalChars > this[0].lastIndex) return null

        return buildString {
            this@getTopRightOrNull
                .slice(y - totalChars until y)
                .reversed()
                .forEachIndexed { index, yChars -> append(yChars[x + (index + 1)]) }
        }
    }

    private fun Grid.getTopLeftOrNull(x: Int, y: Int, totalChars: Int): String? {
        if (y - totalChars < 0 || x - totalChars < 0) return null

        return buildString {
            this@getTopLeftOrNull
                .slice(y - totalChars until y)
                .reversed()
                .forEachIndexed { index, yChars -> append(yChars[x - (index + 1)]) }
        }
    }

    private fun Grid.getAfterOrNull(x: Int, y: Int): String? {
        if (x + 3 > this[0].lastIndex) return null

        return buildString {
            repeat(3) {
                val times = it + 1
                append(this@getAfterOrNull[y][x + times])
            }
        }
    }

    private fun Grid.getBottomRightOrNull(x: Int, y: Int, totalChars: Int): String? {
        if (y + totalChars > lastIndex || x + totalChars > this[0].lastIndex) return null

        return buildString {
            this@getBottomRightOrNull.slice(y + 1..y + totalChars).forEachIndexed { index, chars ->
                append(chars[x + (index + 1)])
            }
        }
    }

    private fun Grid.getBottomOrNull(x: Int, y: Int): String? {
        if (y + 3 > lastIndex) return null

        return buildString {
            this@getBottomOrNull.slice(y + 1..y + 3).forEach { append(it[x]) }
        }
    }

    private fun Grid.getBottomLeftOrNull(x: Int, y: Int, totalChars: Int): String? {
        if (y + totalChars > lastIndex || x - totalChars < 0) return null

        return buildString {
            this@getBottomLeftOrNull.slice(y + 1..y + totalChars).forEachIndexed { index, yChars ->
                append(yChars[x - (index + 1)])
            }
        }
    }

    private fun Grid.getBeforeOrNull(x: Int, y: Int): String? {
        if (x - 3 < 0) return null

        return buildString {
            repeat(3) {
                val times = it + 1
                append(this@getBeforeOrNull[y][x - times])
            }
        }
    }

    companion object {
        const val MAS = "MAS"
    }
}

fun main() {
    val day04 = Day04()

    day04.input = readInputLines("Day04_test")
    check(day04.part1() == 18)

    day04.input = readInputLines("Day04")
    day04.part1().println()

    day04.input = readInputLines("Day04_test")
    check(day04.part2() == 9)

    day04.input = readInputLines("Day04")
    day04.part2().println()
}
