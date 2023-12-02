import java.io.File

interface  Day {
    val dayNumber: Int
    val overrideFile: File? get() = null

    fun runPart1(): Int {
        val file = overrideFile ?: File("inputs/day$dayNumber/day$dayNumber.txt")

        return part1(file.readText())
    }

    fun runPart2(): Int {
        val file = overrideFile ?: File("inputs/day$dayNumber/day$dayNumber.txt")

        return part2(file.readText())
    }

    fun part1(input: String): Int
    fun part2(input: String): Int = 0 // so we don't have to implement part2 until part1 is done
}