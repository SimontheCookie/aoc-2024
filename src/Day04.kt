private enum class Direction1(val dr: Int, val dc: Int) {
    LEFT(0, -1),
    LEFT_TOP(-1, -1),
    TOP(-1, 0),
    TOP_RIGHT(-1, 1),
    RIGHT(0, 1),
    RIGHT_BOTTOM(1, 1),
    BOTTOM(1, 0),
    BOTTOM_LEFT(1, -1)
}

private fun Array<CharArray>.safeGet(r: Int, c: Int): Char {
    if (r in indices && c in this[r].indices) return this[r][c]
    return '\b'
}

fun main() {
    fun part1(input: List<String>): Int {
        val a = input.map { it.toCharArray() }.toTypedArray()

        fun checkDir(row: Int, col: Int, direction: Direction1): Boolean {
            for ((i, c) in charArrayOf('X', 'M', 'A', 'S').withIndex()) {
                if (a.safeGet(row + direction.dr * i, col + direction.dc * i) != c) {
                    return false
                }
            }

            return true
        }

        var count = 0

        for (r in a.indices) {
            for (c in a[r].indices) {
                if (a[r][c] == 'X') {
                    for (direction in Direction1.entries) {
                        if (checkDir(r, c, direction)) {
                            count++
                        }
                    }
                }
            }
        }

        return count
    }

    fun part2(input: List<String>): Int {
        val a = input.map { it.toCharArray() }.toTypedArray()

        var count = 0

        for (r in a.indices) {
            for (c in a[r].indices) {
                if (a[r][c] == 'A') {
                    val lt = a.safeGet(r - 1, c - 1)
                    val rt = a.safeGet(r - 1, c + 1)
                    val lb = a.safeGet(r + 1, c - 1)
                    val rb = a.safeGet(r + 1, c + 1)

                    if (((lt == 'M' && rb == 'S') || (lt == 'S' && rb == 'M')) && ((rt == 'M' && lb == 'S') || (rt == 'S' && lb == 'M'))) {
                        count++
                    }
                }
            }
        }

        return count
    }

    val testInput = """MMMSXXMASM
MSAMXMSMSA
AMXSXMAAMM
MSAMASMSMX
XMASAMXAMM
XXAMMXXAMA
SMSMSASXSS
SAXAMASAAA
MAMMMXMMMM
MXMXAXMASX""".lines()
    check(part1(testInput) == 18)
    check(part2(testInput) == 9)

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}
