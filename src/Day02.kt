import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        val safeReports = input.filter { report ->
            val levels = report.split(' ').map { it.toInt() }.zipWithNext()
            val allValidDifference = levels.all { (currentLevel, nextLevel) ->
                abs(currentLevel - nextLevel) in 1..3
            }
            if (!allValidDifference) return@filter false

            return@filter levels.all allIncreasing@ { (currentLevel, nextLevel) -> currentLevel < nextLevel } || levels.all allDecreasing@ { (currentLevel, nextLevel) -> currentLevel > nextLevel }
        }
        return safeReports.count()
    }

    fun part2(input: List<String>): Int {
        TODO()
    }

    val testInput = readInput("Day02_test")
    check(part1(testInput) == 2)

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
