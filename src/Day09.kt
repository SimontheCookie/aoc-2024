// inspired by @eagely

private data class Space(val pos: Int, val size: Int)
private data class File(val pos: Int, val size: Int, val id: Int)

fun main() {
    fun part1(input: List<String>): Long {
        val memory = mutableListOf<Int>()

        var id = 0
        for ((index, digit) in input.first().map { it.digitToInt() }.withIndex()) {
            repeat(digit) {
                memory.add(if (index % 2 == 0) id else -1)
            }

            if (index % 2 == 0) {
                id++
            }
        }

        var indexFront = 0
        var indexEnd = memory.size - 1

        while (-1 in memory) {
            if (memory[indexFront] != -1) {
                indexFront++
                continue
            }
            memory[indexFront] = memory[indexEnd]
            memory.removeAt(indexEnd)
            indexEnd--
        }

        return memory.withIndex().sumOf { (index, digit) -> index.toLong() * digit }
    }

    fun part2(input: List<String>): Long {
        val queue = ArrayDeque<File>()
        val space = ArrayDeque<Space>()
        val result = mutableListOf<Int?>()

        var p = 0
        var fileId = 0

        for ((i, c) in input.first().map { it.digitToInt() }.withIndex()) {
            if (i % 2 == 0) {
                queue.add(File(p, c, fileId))
                repeat((p..<p + c).count()) {
                    result.add(fileId)
                }
                p += c
                fileId += 1
            } else {
                space.add(Space(p, c))
                result.addAll(List(c) { null })
                p += c
            }
        }

        for ((pos, size, fid) in queue.reversed()) {
            space.withIndex().firstOrNull { (_, s) ->
                s.pos < pos && size <= s.size
            }?.let { (si, s) ->
                (0..<size).forEach { i ->
                    result[pos + i] = null
                    result[s.pos + i] = fid
                }
                space[si] = Space(s.pos + size, s.size - size)
            }
        }

        return result.withIndex().sumOf { (i, v) -> i.toLong() * (v ?: 0) }
    }

    val testInput = """2333133121414131402""".lines()
    check(part1(testInput) == 1928L)
    check(part2(testInput) == 2858L)

    val input = readInput("Day09")
    part1(input).println()
    part2(input).println()
}
