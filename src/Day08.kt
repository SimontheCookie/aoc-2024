fun main() {
    fun part1(input: List<String>): Int {
        val a = buildMap {
            for ((row, line) in input.withIndex()) {
                for ((column, char) in line.withIndex()) {
                    if (char.isLetterOrDigit()) put(Point(row, column), char)
                }
            }
        }

        val b = buildSet {
            for ((position, frequency) in a.entries) {
                for ((otherPosition, otherFrequency) in a.entries) {
                    if (otherPosition != position && otherFrequency == frequency) {
                        val newPoint = position.reflection(otherPosition)
                        if (newPoint.r in input.indices && newPoint.c in input[newPoint.r].indices)
                            add(newPoint)
                    }
                }
            }
        }

        return b.size
    }

    fun part2(input: List<String>): Int {
        val a = buildMap {
            for ((row, line) in input.withIndex()) {
                for ((column, char) in line.withIndex()) {
                    if (char.isLetterOrDigit()) put(Point(row, column), char)
                }
            }
        }

        val b = buildSet {
            for ((position, frequency) in a.entries) {
                for ((otherPosition, otherFrequency) in a.entries) {
                    if (otherPosition != position && otherFrequency == frequency) {
                        var point = position
                        var center = otherPosition
                        var newCenter = point.reflection(center)
                        while (newCenter.r in input.indices && newCenter.c in input[newCenter.r].indices) {
                            add(newCenter)
                            point = center
                            center = newCenter
                            newCenter = point.reflection(center)
                        }

                        point = position.reflection(otherPosition)
                        center = otherPosition
                        newCenter = point.reflection(center)
                        while (newCenter.r in input.indices && newCenter.c in input[newCenter.r].indices) {
                            add(newCenter)
                            point = center
                            center = newCenter
                            newCenter = point.reflection(center)
                        }
                    }
                }
            }
        }

        return b.size
    }

    val testInput = """............
........0...
.....0......
.......0....
....0.......
......A.....
............
............
........A...
.........A..
............
............""".lines()
    check(part1(testInput) == 14)
    check(part2(testInput) == 34)

    val input = readInput("Day08")
    part1(input).println()
    part2(input).println()
}
