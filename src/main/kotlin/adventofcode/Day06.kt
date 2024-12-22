package adventofcode

import org.openjdk.jmh.annotations.*
import java.util.concurrent.TimeUnit

private typealias MappedArea = List<CharArray>
private typealias VisitedPositions = MutableSet<Position>

private data class Position(val x: Int, val y: Int)

private enum class Orientation {
    UP,
    DOWN,
    LEFT,
    RIGHT;

    companion object {
        fun next(current: Orientation) = when (current) {
            UP -> RIGHT
            DOWN -> LEFT
            LEFT -> UP
            RIGHT -> DOWN
        }
    }
}

@State(Scope.Benchmark)
@Fork(1)
@Warmup(iterations = 5)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
class Day06 {

    var input = emptyList<String>()

    @Setup
    fun setUp() {
        input = readInputLines(name)
    }

    @Benchmark
    fun part1(): Int {
        val mappedArea = input.map { it.toCharArray() }
        val startPosition = mappedArea.withIndex().firstNotNullOf { (y, line) ->
            val x = line.indexOf('^')
            return@firstNotNullOf if (x == -1) null else Position(x, y)
        }

        val visitedPositions = mutableSetOf(startPosition)
        var orientation = Orientation.UP
        var currentPosition = startPosition
        while (true) {
            val path = mappedArea.getPatrolPath(currentPosition, orientation)
            val steps = path.takeWhile { it != '#' }.count()
            if (steps == path.size) {
                visitedPositions.addAll(currentPosition, orientation, steps)
                break
            }
            currentPosition = visitedPositions.addAll(currentPosition, orientation, steps)
            orientation = Orientation.next(orientation)
        }
        return visitedPositions.size
    }

    @Benchmark
    fun part2(): Int {
        return input.size
    }

    private fun VisitedPositions.addAll(currentPosition: Position, orientation: Orientation, steps: Int): Position {
        var finalPosition: Position? = null
        for (step in 1..steps) {
            val nextPosition = when (orientation) {
                Orientation.UP -> currentPosition.copy(y = currentPosition.y - step)
                Orientation.DOWN -> currentPosition.copy(y = currentPosition.y + step)
                Orientation.LEFT -> currentPosition.copy(x = currentPosition.x - step)
                Orientation.RIGHT -> currentPosition.copy(x = currentPosition.x + step)
            }
            add(nextPosition)
            if (step == steps) {
                finalPosition = nextPosition
            }
        }
        return finalPosition!!
    }

    private fun MappedArea.getPatrolPath(currentPosition: Position, orientation: Orientation): List<Char> {
        return when (orientation) {
            Orientation.UP -> take(currentPosition.y).map { it[currentPosition.x] }.reversed()
            Orientation.DOWN -> takeLast(lastIndex - currentPosition.y).map { it[currentPosition.x] }
            Orientation.LEFT -> get(currentPosition.y).take(currentPosition.x).reversed()
            Orientation.RIGHT -> get(currentPosition.y).takeLast(lastIndex - currentPosition.x)
        }
    }

    companion object {
        val name: String = Day06::class.java.simpleName
    }
}

fun main() {
    val day06 = Day06()

    day06.input = readInputLines("${Day06.name}_test")
    check(day06.part1() == 41)

    day06.input = readInputLines(Day06.name)
    day06.part1().println()

    day06.input = readInputLines("${Day06.name}_test")
    check(day06.part2() == -1)

    day06.input = readInputLines(Day06.name)
    day06.part2().println()
}
