private fun sequencer1(size: Int, row: CharArray = charArrayOf()): List<CharArray> {
    if (size == 0) return listOf(row)
    val a = sequencer1(size - 1, row + charArrayOf('+'))
    val b = sequencer1(size - 1, row + charArrayOf('*'))
    return a + b
}

private fun sequencer2(size: Int, row: CharArray = charArrayOf()): List<CharArray> {
    if (size == 0) return listOf(row)
    val a = sequencer2(size - 1, row + charArrayOf('+'))
    val b = sequencer2(size - 1, row + charArrayOf('*'))
    val c = sequencer2(size - 1, row + charArrayOf('|'))
    return a + b + c
}

fun main() {
    fun part1(input: List<String>): Long {
        var total = 0L

        for (line in input) {
            val (left, right) = line.split(": ")
            val expected = left.toLong()
            val values = right.split(' ').map(String::toLong)

            val requiredOperators = values.size - 1
            check(requiredOperators >= 0)

            val allOperators = sequencer1(requiredOperators)

            val anyOperatorMatchesExpected = allOperators.any { operators ->
                values.drop(1).foldIndexed(values.first()) { i, a, b ->
                    when (operators[i]) {
                        '+' -> a + b
                        '*' -> a * b
                        else -> throw IllegalStateException()
                    }
                } == expected
            }

            if (anyOperatorMatchesExpected)
                total += expected
        }

        return total
    }

    fun part2(input: List<String>): Long {
        var total = 0L

        for (line in input) {
            val (left, right) = line.split(": ")
            val expected = left.toLong()
            val values = right.split(' ').map(String::toLong)

            val requiredOperators = values.size - 1
            check(requiredOperators >= 0)

            val allOperators = sequencer2(requiredOperators)

            val anyOperatorMatchesExpected = allOperators.any { operators ->
                values.drop(1).foldIndexed(values.first()) { i, a, b ->
                    when (operators[i]) {
                        '+' -> a + b
                        '*' -> a * b
                        '|' -> (a.toString() + b.toString()).toLong()
                        else -> throw IllegalStateException()
                    }
                } == expected
            }

            if (anyOperatorMatchesExpected)
                total += expected
        }

        return total
    }

    val testInput = """190: 10 19
3267: 81 40 27
83: 17 5
156: 15 6
7290: 6 8 6 15
161011: 16 10 13
192: 17 8 14
21037: 9 7 18 13
292: 11 6 16 20""".lines()
    check(part1(testInput) == 3749L)
    check(part2(testInput) == 11387L)

    val input = readInput("Day07")
    part1(input).println()
    part2(input).println()
}
