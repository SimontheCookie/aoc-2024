import kotlin.math.abs

fun main() {
    fun isSafe(report: List<Int>): Boolean {
        var lastLevel: Int? = null
        var direction: Int? = null
        for (level in report) {
            if (lastLevel != null) {
                when (level.compareTo(lastLevel)) {
                    -1 -> if (direction == 1) return false else direction = -1
                    0 -> return false
                    1 -> if (direction == -1) return false else direction = 1
                }

                if (abs(level - lastLevel) !in 1..3) return false
            }

            lastLevel = level
        }
        return true
    }

    fun part1(input: List<String>): Int {
        val reports = input.map { it.split(" ").map(String::toInt) }
        return reports.count { isSafe(it) }
    }

    fun part2(input: List<String>): Int {
        val reports = input.map { it.split(" ").map(String::toInt) }

        var safeReports = 0

        reports@ for (report in reports) {
            for (i in report.indices) {
                val reducedReport = report.filterIndexed { index, _ -> index != i }
                if (isSafe(reducedReport)) {
                    safeReports++
                    continue@reports
                }
            }
        }

        return safeReports
    }

    val testInput = """7 6 4 2 1
1 2 7 8 9
9 7 6 2 1
1 3 2 4 5
8 6 4 4 1
1 3 6 7 9""".lines()
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
