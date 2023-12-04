package days

import Day

class Day4: Day {
    override val dayNumber = 4

    override fun part1(input: String): Int {
        val cards = input.lines().mapNotNull { it.parseCard() }

        val pointsForCards = cards.map {
            val numMatches = it.numMatches()

            if(numMatches == 0) 0
            else 1 shl (numMatches-1)
        }

        return pointsForCards.sum()
    }
}

data class Card(val id: Int, val winningNumbers: List<Int>, val numbers: List<Int>)

fun String.parseCard(): Card? {
    if(isEmpty() || isBlank()) return null

    val parts = split(":")

    val id = parts.first().removePrefix("Card ").trim().toInt()

    val numberLists = parts.last().split("|")

    val winningNumbers = numberLists.first().split(" ").mapNotNull { it.trim().toIntOrNull() }
    val numbers = numberLists.last().split(" ").mapNotNull { it.trim().toIntOrNull() }

    return Card(id, winningNumbers, numbers)
}

fun Card.numMatches(): Int {
    return winningNumbers.intersect(numbers.toSet()).size
}
