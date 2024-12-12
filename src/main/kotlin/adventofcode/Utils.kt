package adventofcode

import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readText

/**
 * Reads text from the given input txt file.
 */
fun readInputText(name: String, trim: Boolean = true) = Path("src/main/resources/$name.txt")
    .readText()
    .apply { if (trim) trim() }

/**
 * Reads lines from the given input txt file.
 */
fun readInputLines(name: String, trim: Boolean = true): List<String> = readInputText(name, trim).lines()

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
