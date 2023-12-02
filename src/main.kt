import java.io.File

fun main() {
    val part1File = File("inputs/day1/day1.txt")
    val day = Day1()
    
    println(day.part1(part1File.readText()))
    println(day.part2(part1File.readText()))
}
