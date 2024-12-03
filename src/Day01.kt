import java.util.*
import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Long {
        val left = PriorityQueue<Long>(input.size)
        val right = PriorityQueue<Long>(input.size)

        for (line in input) {
            val (a, b) = line.split("   ").map(String::toLong)
            left.add(a)
            right.add(b)
        }

        var totalDistance = 0L
        while (left.isNotEmpty()) {
            totalDistance += abs(left.poll() - right.poll())
        }

        return totalDistance
    }

    fun part2(input: List<String>): Long {
        val left = PriorityQueue<Long>(input.size)
        val right = mutableMapOf<Long, Long>()

        for (line in input) {
            val (a, b) = line.split("   ").map(String::toLong)
            left.add(a)
            right[b] = (right[b] ?: 0L) + 1L
        }

        var totalSimilarity = 0L
        while (left.isNotEmpty()) {
            val v = left.poll()
            totalSimilarity += v * (right[v] ?: 0L)
        }

        return totalSimilarity
    }

    check(part1(listOf("3   4", "4   3", "2   5", "1   3", "3   9", "3   3")) == 11L)
    check(part2(listOf("3   4", "4   3", "2   5", "1   3", "3   9", "3   3")) == 31L)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
