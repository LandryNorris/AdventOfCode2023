import days.Day2
import java.io.File

fun main() {
    val dayFile = File("inputs/day2/day2.txt")
    val day = Day2()
    
    println(day.part1(dayFile.readText()))
    println(day.part2(dayFile.readText()))
}
