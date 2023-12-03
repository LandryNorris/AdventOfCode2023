package days

import Day
import java.io.File

data class Position(val x: Int, val y: Int)

data class PartNumber(val value: Int, val startPosition: Position, val endPosition: Position)

class Day3: Day {
    override val dayNumber = 3

    override fun part1(input: String): Int {
        val engine = input.lines()
        val symbols = engine.findSymbols()
        val partNumbers = engine.findPartNumbers()

        val partNumbersAdjacentToSymbol = partNumbers.filter { partNumber ->
            symbols.any {
                it.adjacentTo(partNumber)
            }
        }

        return partNumbersAdjacentToSymbol.sumOf { it.value }
    }
}

private fun List<String>.findSymbols(): List<Position> {
    return mapIndexedNotNull {  y, line ->
        line.mapIndexedNotNull {  x, c ->
            if(!c.isDigit() && c != '.') {
                Position(x, y)
            } else null
        }
    }.flatten()
}

private fun List<String>.findPartNumbers(): List<PartNumber> {
    return mapIndexedNotNull { y, line ->
        val regex = """(\d+)""".toRegex()
        regex.findAll(line).map {
            val start = Position(it.range.first, y)
            val end = Position(it.range.last, y)
            PartNumber(it.value.toInt(), start, end)
        }.toList()
    }.flatten()
}

val PartNumber.isHorizontal get() = startPosition.y == endPosition.y
val PartNumber.isVertical get() = startPosition.x == endPosition.x

fun Position.adjacentTo(partNumber: PartNumber): Boolean {
    return if(partNumber.isVertical) {
        alongSideVertically(partNumber)
    } else if(partNumber.isHorizontal) {
        alongSideHorizontally(partNumber)
    } else {
        false //not handled yet
    }
}

fun Position.sameColumnAs(partNumber: PartNumber): Boolean {
    return x == partNumber.startPosition.x && x == partNumber.endPosition.x
}

fun Position.sameRowAs(partNumber: PartNumber): Boolean {
    return y == partNumber.startPosition.y && y == partNumber.endPosition.y
}

fun Position.alongSideVertically(partNumber: PartNumber): Boolean {
    if(!partNumber.isVertical) return false
    val partNumberX = partNumber.startPosition.x
    if(x !in partNumberX-1 .. partNumberX+1) return false

    return y in (partNumber.startPosition.y-1 .. partNumber.endPosition.y+1)
}

fun Position.alongSideHorizontally(partNumber: PartNumber): Boolean {
    if(!partNumber.isHorizontal) return false
    val partNumberY = partNumber.startPosition.y
    if(y !in partNumberY-1 .. partNumberY+1) return false

    return x in (partNumber.startPosition.x-1 .. partNumber.endPosition.x+1)
}
