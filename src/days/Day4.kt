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

    override fun part2(input: String): Int {
        val cards = input.lines().mapNotNull { it.parseCard() }
        val directCardLists = cards.mapIndexed { index, card ->
            val numMatches = card.numMatches()
            val startIndex = index+1
            val endIndex = (startIndex + numMatches).coerceAtMost(cards.size)

            val otherCards = cards.subList(startIndex, endIndex)
            card to otherCards
        }.toMap()

        val ids = cards.map { it.id }

        val numOfEachCard = mutableMapOf<Int, Int>()

        ids.forEach { id ->
            val numMatchingThis = directCardLists.mapValues { it.value.count { card -> card.id == id } }

            numOfEachCard[id] = 1 + numMatchingThis.map { it.value * numOfEachCard.getOrDefault(it.key.id, 0) }.sum()
        }

        return numOfEachCard.map { it.value }.sum()
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

fun <T> Map<*, List<T>>.totalCount(predicate: (T) -> Boolean): Int {
    return values.sumOf { list ->
        list.count { predicate(it) }
    }
}
