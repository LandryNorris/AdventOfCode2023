import java.io.File

interface  Day {
    val dayNumber: Int
    val overrideFile: File? get() = null

    fun runPart1(): Int {
        return part1(getFileContents())
    }

    fun runPart2(): Int {
        return part2(getFileContents())
    }

    fun getFileContents(): String {
        val userSpecifiedFile = overrideFile
        val file = userSpecifiedFile ?: File("inputs/day$dayNumber/day$dayNumber.txt")

        if(!file.exists()) {
            if(userSpecifiedFile != null) {
                println("You have overridden the file path to " +
                        "${userSpecifiedFile.absoluteFile}. Make sure a file " +
                        "exists at this path, or remove the override.")
            } else {
                println("A file is expected at ${file.absoluteFile}. Please" +
                        "copy the input for today's puzzle into this location.")
            }
        }

        return file.readText()
    }

    fun part1(input: String): Int
    fun part2(input: String): Int = 0 // so we don't have to implement part2 until part1 is done
}