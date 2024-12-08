private val Direction.turnRight
    get() = when (this) {
        Direction.LEFT -> Direction.TOP
        Direction.TOP -> Direction.RIGHT
        Direction.RIGHT -> Direction.BOTTOM
        Direction.BOTTOM -> Direction.LEFT
    }

private val Char.direction
    get() = when (this) {
        '<' -> Direction.LEFT
        '^' -> Direction.TOP
        '>' -> Direction.RIGHT
        'v' -> Direction.BOTTOM
        else -> throw IllegalArgumentException()
    }

private data class Guard(val position: Point, val direction: Direction)

fun main() {
    fun part1(input: List<String>): Int {
        lateinit var guard: Guard

        val obstacles = buildSet {
            for ((r, line) in input.withIndex()) {
                for ((c, char) in line.withIndex()) {
                    when (char) {
                        '#' -> add(Point(r, c))
                        '<', '^', '>', 'v' -> guard = Guard(Point(r, c), char.direction)
                    }
                }
            }
        }

        val visited = linkedSetOf<Point>()
        var currDir = guard.direction
        var currPos: Point = guard.position
        while (currPos.r in input.indices && currPos.c in input[currPos.r].indices) {
            visited.add(currPos)

            val nextPos = currPos.apply(currDir)
            if (obstacles.contains(nextPos)) {
                currDir = currDir.turnRight
            } else {
                currPos = nextPos
            }
        }

        return visited.size
    }

    fun part2(input: List<String>): Int {
        val d = listOf(-1 to 0, 0 to 1, 1 to 0, 0 to -1)

        val VISITED = '&'

        fun List<CharArray>.traverse(startI: Int, startJ: Int): Boolean {
            val n = size
            val m = first().size
            var i = startI
            var j = startJ
            var currentDirection = 0
            val visited = Array(n) { IntArray(m) }
            do {
                if ((visited[i][j] and (1 shl currentDirection)) != 0) break
                visited[i][j] = visited[i][j] or (1 shl currentDirection)
                this[i][j] = VISITED
                val (di, dj) = d[currentDirection]
                if (i + di !in 0..<n || j + dj !in 0..<m) return false
                if (this[i + di][j + dj] == '#') {
                    currentDirection = (currentDirection + 1) % d.size
                } else {
                    i += di
                    j += dj
                }
            } while (true)
            return true
        }

        val (startI, startJ) = input.run {
            for (i in 0..lastIndex) {
                for (j in 0..this.first().lastIndex) {
                    if (this[i][j] == '^') {
                        return@run i to j
                    }
                }
            }
            throw RuntimeException("Should not reach here")
        }
        val a = input.map(String::toCharArray)
        a.traverse(startI, startJ)

        var result = 0
        for (i in 0..a.lastIndex) {
            for (j in 0..a.first().lastIndex) {
                if (a[i][j] == VISITED) {
                    a[i][j] = '#'
                    if (a.traverse(startI, startJ)) {
                        result++;
                    }
                    a[i][j] = '.'
                }
            }
        }

        return result
    }

    val testInput = """....#.....
.........#
..........
..#.......
.......#..
..........
.#..^.....
........#.
#.........
......#...""".lines()
    check(part1(testInput) == 41)
    check(part2(testInput) == 6)

    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}
