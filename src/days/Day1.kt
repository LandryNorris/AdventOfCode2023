package days

import Day

class Day1: Day {
    override val dayNumber = 1

    override fun part1(input: String): Int {
        return input.lines().sumOf {
            val firstDigit = it.firstOrNull { c ->
                c.isDigit()
            }?.digitToIntOrNull() ?: 0
            val lastDigit = it.lastOrNull { c->
                c.isDigit()
            }?.digitToIntOrNull() ?: 0

            firstDigit*10 + lastDigit
        }
    }
    
    override fun part2(input: String): Int {
        return input.lines().sumOf {
            val firstWordIndex = words.minOfOrNull { word ->
                if(word in it) it.indexOf(word) else 999
            } ?: -1
            val lastWordIndex = words.maxOfOrNull { word ->
                if(word in it) it.lastIndexOf(word) else -1
            } ?: -1
            val firstDigitIndex = it.indexOfFirst { c ->
                c.isDigit()
            }
            val lastDigitIndex = it.indexOfLast { c->
                c.isDigit()
            }
            
            val lastNumberIndex = listOf(lastDigitIndex, lastWordIndex)
                .mapNotNull { it.takeIf { it != -1 && it != 999 } }.maxOrNull() ?: -1
            val firstNumberIndex = listOf(firstDigitIndex, firstWordIndex)
                .mapNotNull { it.takeIf { it != -1 && it != 999 } }.minOrNull() ?: -1

            val firstNumber = it.wordOrDigitAtIndex(firstNumberIndex)
            val lastNumber = it.wordOrDigitAtIndex(lastNumberIndex)
            
            firstNumber*10 + lastNumber
        }
    }
    
    private val words = listOf("zero", "one", "two", "three", "four",
                       "five", "six", "seven", "eight", "nine")
    
    private fun String?.wordToIntOrNull(): Int? {
        val input = this?.trim() ?: return null
        return if(words.contains(input)) words.indexOf(input) else null
    }
    
    private fun String.wordOrDigitAtIndex(i: Int): Int {
        if(i == -1 || i == 999) return 0
        
        if(this[i].isDigit()) return this[i].digitToInt()
        
        if(this[i].isLetter()) {
            val stringAfter = substring(i)
            val word = words.firstOrNull { word -> stringAfter.startsWith(word) }
            return word.wordToIntOrNull() ?: 0
        }
        
        return 0
    }
}
