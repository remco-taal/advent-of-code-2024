import kotlin.math.abs

fun main() {
    fun part1(input: List<String>) = -1

    fun part2(input: List<String>) = -1

    val testInput = readInput("Day00_test")
    check(part1(testInput) == 11)

    val input = readInput("Day00")
    part1(input).println()
    part2(input).println()
}
