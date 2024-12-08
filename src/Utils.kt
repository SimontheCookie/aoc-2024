import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readText

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("src/$name.txt").readText().trim().lines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)

data class Point(val r: Int, val c: Int) {
    fun apply(direction: Direction) = Point(r + direction.dr, c + direction.dc)

    fun reflection(center: Point) = Point(center.r + (center.r - r), center.c + (center.c - c))
}

enum class Direction(val dr: Int, val dc: Int) {
    LEFT(0, -1),
    TOP(-1, 0),
    RIGHT(0, 1),
    BOTTOM(1, 0)
}
