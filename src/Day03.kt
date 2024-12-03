fun main() {
    fun part1(input: List<String>): Int {
        val instructions = buildList {
            val regex = Regex("""mul\(\d+,\d+\)""")
            for (line in input) {
                for (it in regex.findAll(line)) {
                    add(it.value)
                }
            }
        }

        return instructions.sumOf {
            val split = it.substring(4, it.length - 1).split(",")
            split[0].trim().toInt() * split[1].trim().toInt()
        }
    }

    fun part2(input: List<String>): Int {
        val instructions = buildList {
            val regex = Regex("""mul\(\d+,\d+\)|do\(\)|don't\(\)""")
            var active = true
            for (line in input) {
                var match = regex.find(line)
                while (match != null) {
                    val value = match.value
                    if (active && value.startsWith("mul(")) {
                        add(value)
                    } else {
                        active = value.startsWith("do(")
                    }

                    match = match.next()
                }
            }
        }

        return instructions.sumOf {
            val split = it.substring(4, it.length - 1).split(",")
            split[0].trim().toInt() * split[1].trim().toInt()
        }
    }

    check(part1("""xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))""".lines()) == 161)
    check(part2("""xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))""".lines()) == 48)

    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}
