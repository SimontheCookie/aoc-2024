@file:Suppress("UnstableApiUsage")

import com.google.common.graph.GraphBuilder

fun main() {
    fun part1(input: List<String>): Int {
        val pog = GraphBuilder.directed().immutable<Int>().apply {
            for (line in input.takeWhile { it.isNotEmpty() }) {
                val (l, r) = line.split("|").map { it.toInt() }
                putEdge(l, r)
            }
        }.build()

        var sum = 0

        line@ for (line in input.takeLastWhile { it.isNotEmpty() }) {
            val updates = line.split(",").map { it.toInt() }
            val alreadyProcessed = mutableSetOf<Int>()

            for (update in updates) {
                for (required in pog.successors(update)) {
                    if (alreadyProcessed.contains(required)) continue@line
                }

                alreadyProcessed.add(update)
            }

            require(updates.size % 2 == 1)
            sum += updates[updates.size / 2]
        }

        return sum
    }

    fun part2(input: List<String>): Int {
        val pog = GraphBuilder.directed().immutable<Int>().apply {
            for (line in input.takeWhile { it.isNotEmpty() }) {
                val (l, r) = line.split("|").map { it.toInt() }
                putEdge(l, r)
            }
        }.build()

        val wo = buildList<List<Int>> {
            line@ for (line in input.takeLastWhile { it.isNotEmpty() }) {
                val updates = line.split(",").map { it.toInt() }
                val alreadyProcessed = mutableSetOf<Int>()

                for (update in updates) {
                    for (required in pog.successors(update)) {
                        if (alreadyProcessed.contains(required)) {
                            add(updates)
                            continue@line
                        }
                    }

                    alreadyProcessed.add(update)
                }
            }
        }

        val w = wo.map {
            it.sortedWith { a, b ->
                when {
                    pog.hasEdgeConnecting(a, b) -> -1
                    pog.hasEdgeConnecting(b, a) -> 1
                    else -> 0
                }
            }
        }
        return w.sumOf {
            require(it.size % 2 == 1)
            it[it.size / 2]
        }
    }

    val testInput = """47|53
97|13
97|61
97|47
75|29
61|13
75|53
29|13
97|29
53|29
61|53
97|53
61|29
47|13
75|47
97|75
47|61
75|61
47|29
75|13
53|13

75,47,61,53,29
97,61,53,29,13
75,29,13
75,97,47,61,53
61,13,29
97,13,75,29,47""".lines()
    check(part1(testInput) == 143)
    check(part2(testInput) == 123)

    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}
