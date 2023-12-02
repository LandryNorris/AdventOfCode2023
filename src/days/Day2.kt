package days

import Day

class Day2: Day {
    override val dayNumber = 2

    override fun part1(input: String): Int {
        val games = input.lines().mapNotNull { it.parseGame() }

        val numRed = 12
        val numGreen = 13
        val numBlue = 14

        val possibleGames = games.filter {
            it.turns.all { turn ->
                turn.red <= numRed && turn.green <= numGreen && turn.blue <= numBlue
            }
        }

        return possibleGames.sumOf { it.id }
    }

    override fun part2(input: String): Int {
        val games = input.lines().mapNotNull { it.parseGame() }

        val powers = games.map {
            val fewestRed = it.turns.maxOf { turn -> turn.red }
            val fewestGreen = it.turns.maxOf { turn -> turn.green }
            val fewestBlue = it.turns.maxOf { turn -> turn.blue }

            fewestRed*fewestGreen*fewestBlue
        }

        return powers.sum()
    }
}

fun String.parseGame(): Game? {
    val parts = split(":")
    if(parts.size < 2) return null

    val id = parts.first().takeLastWhile { it.isDigit() }.toInt()

    val turnsText = parts.last()

    val splitTurnsText = turnsText.split(";")

    return Game(id, splitTurnsText.map { it.parseTurn() })
}

fun String.parseTurn(): Turn {
    val words = replace(",", "").split(" ")

    val redIndex = words.indexOf("red")
    val blueIndex = words.indexOf("blue")
    val greenIndex = words.indexOf("green")

    val red = if(redIndex > 0) words[redIndex-1].toInt() else 0
    val blue = if(blueIndex > 0) words[blueIndex-1].toInt() else 0
    val green = if(greenIndex > 0) words[greenIndex-1].toInt() else 0

    return Turn(red, green, blue)
}

data class Turn(val red: Int, val green: Int, val blue: Int)

data class Game(val id: Int, val turns: List<Turn>)
