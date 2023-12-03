package days

import Day

data class Position(val x: Int, val y: Int)
data class Symbol(val char: Char, val position: Position)
data class GearRatio(val part1: PartNumber, val part2: PartNumber)

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

    override fun part2(input: String): Int {
        val engine = input.lines()
        val symbols = engine.findSymbols()
        val gears = symbols.filter { it.char == '*' }
        val partNumbers = engine.findPartNumbers()

        val gearRatios = gears.mapNotNull { gear ->
            val adjacentPartNumbers = partNumbers.filter {
                gear.adjacentTo(it)
            }

            if(adjacentPartNumbers.size == 2) {
                GearRatio(adjacentPartNumbers.first(), adjacentPartNumbers.last())
            } else null
        }

        return gearRatios.sumOf { it.part1.value * it.part2.value }
    }
}

private fun List<String>.findSymbols(): List<Symbol> {
    return mapIndexedNotNull {  y, line ->
        line.mapIndexedNotNull {  x, c ->
            if(!c.isDigit() && c != '.') {
                Symbol(c, Position(x, y))
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

fun Symbol.adjacentTo(partNumber: PartNumber): Boolean {
    return if(partNumber.isVertical) {
        alongSideVertically(partNumber)
    } else if(partNumber.isHorizontal) {
        alongSideHorizontally(partNumber)
    } else {
        false //not handled yet
    }
}

fun Symbol.alongSideVertically(partNumber: PartNumber): Boolean {
    if(!partNumber.isVertical) return false
    val partNumberX = partNumber.startPosition.x
    if(position.x !in partNumberX-1 .. partNumberX+1) return false

    return position.y in (partNumber.startPosition.y-1 .. partNumber.endPosition.y+1)
}

fun Symbol.alongSideHorizontally(partNumber: PartNumber): Boolean {
    if(!partNumber.isHorizontal) return false
    val partNumberY = partNumber.startPosition.y
    if(position.y !in partNumberY-1 .. partNumberY+1) return false

    return position.x in (partNumber.startPosition.x-1 .. partNumber.endPosition.x+1)
}
