import java.io.File

/**
 * Solution for Day 1 for Advent of Code 2020.
 * The idea here is that we are given a list of numbers, and we want the product of the 2 or 3 which add up to 2020.
 *
 * This crap-fest was the first bit of Kotlin I ever wrote in my life, so give it some slack.
 */
fun day1Solution(linesOfFile: List<String>) {
    var firstIndex = -1;
    var secondIndex = -1;
    var thirdIndex = -1;

    // Ready for the most inefficient algorithm ever? Here we go. O(n^3) baby.
    for (firstLine in linesOfFile) {
        firstIndex++;
        secondIndex = -1;

        for (secondLine in linesOfFile) {
            secondIndex++;
            thirdIndex = 0;

            for (thirdLine in linesOfFile) {
                // The checks regarding the indices are probably not needed, because it's really unlikely that the number
                // will add up to be 2020 if it is itself.
                if (firstLine.toInt() + secondLine.toInt() + thirdLine.toInt() == 2020 && firstIndex != secondIndex && firstIndex != thirdIndex) {
                    println("Match $firstLine $secondLine");
                    println(firstLine.toInt() * secondLine.toInt() * thirdLine.toInt());
                    return;
                }
            }
        }
    }
}

/**
 * Solution for Day 2 for Advent of Code 2020.
 *
 * This is the SECOND chunk of Kotlin I've ever written in my life. Before this was made, the "chasis" for this project was
 * put in place. Before, the horror seen in the day1 function was just slapped haphazardly in the main function.
 *
 * The idea for this one is we're given a list of password rules, and passwords. We need to return the total
 * number of passwords that adhere to the rules.
 */
fun day2Solution(linesOfFile: List<String>) {
    var goodCount = 0;

    for (line in linesOfFile) {
        if (Day2PasswordDeclaration(line).matchesPartTwo()) {
            goodCount++;
        }
    }

    println("Count of good passwords: $goodCount");
}

/**
 * Day 3 solution to Advent of Code 2020. This involves simulating a grid of trees and using a slope to predict
 * a sled's trajectory across the grid. It will count the encountered trees as it goes.
 */
fun day3Solution(linesOfFile: List<String>) {
    val grid = Day3Grid(linesOfFile)
    println("Part One Solution: " + grid.simulate(3, 1))

    val slopeOne = grid.simulate(1, 1)
    val slopeTwo = grid.simulate(3, 1)
    val slopeThree = grid.simulate(5, 1)
    val slopeFour = grid.simulate(7, 1)
    val slopeFive = grid.simulate(1, 2)

    val product = slopeOne * slopeTwo * slopeThree * slopeFour * slopeFive
    println("Part Two Solution (using Long): $product");
}

/**
 * Day 4 solution to Advent of Code 2020. This involves parsing a text file that acts as a sort of database, for passports.
 */
fun day4Solution(linesOfFile: List<String>) {
    val database = Day4Database(linesOfFile)
    val okayRecords = database.validate()
    println("Part One Solution: $okayRecords")
    val okayRecordsPartTwo = database.validatePartTwo()
    println("Part Two Solution: $okayRecordsPartTwo")
}

/**
 * Day 5 solution to Advent of Code 2020 - Binary Space Partitioning of an Airplane.
 */
fun day5Solution(linesOfFile: List<String>) {
    // Wants: 552
    var highestPassId = 0
    var seats = HashMap<Int, MutableList<Int>>()

    for (line in linesOfFile) {
        val (row, column) = Day5BoardingPass(line).getPassId()

        var seatList = seats[row]
        if (seatList == null) {
            seats[row] = mutableListOf()
        }
        seats[row]!!.add(column)
    }

    var badRow: MutableList<Int> = mutableListOf()
    var badRowNumber: Int? = null

    for (row in seats.keys) {
        if (seats[row]!!.count() != 8) {
            seats[row]!!.sort()

            badRow = seats[row]!!
            badRowNumber = row
        }
    }

    for (number in 1..8) {
        if (!badRow.contains(number)) {
            // The -1 here is necessary because of a bug in Day5BoardingPass - it just happens to work for Part 1
            // because the input data ends in an 'F' instead of a 'B'
            val passId = (badRowNumber!! - 1) * 8 + number
            println("Pass ID: $passId")
        }
    }

}

fun day6Solution(lines: List<String>) {
    val quiz = Day6Quiz(lines)
    val partOneAnswer = quiz.getAllQuestionsAnsweredByGroups()
    println("Solution 1: $partOneAnswer")
}

// Everything below this line is what makes this program somewhat "extensible". It sets up the ability to change what
// day it is via an enum.
enum class Day {
    DAY1,
    DAY2,
    DAY3,
    DAY4,
    DAY5,
    DAY6,
}

fun main(args: Array<String>) {
    val day = Day.DAY6;

    val solutionPairs = arrayOf(
        Triple<Day, String, (a: List<String>) -> Unit>(
            Day.DAY1,
            "/home/nate/Code/aoc1kot/day1_puzzle_input.txt",
            { a -> day1Solution(a) }),
        Triple(Day.DAY2, "/home/nate/Code/aoc1kot/day2_puzzle_input.txt", { a -> day2Solution(a) }),
        Triple(Day.DAY3, "/home/nate/Code/aoc1kot/day3_puzzle_input.txt", { a -> day3Solution(a) }),
        Triple(Day.DAY4, "/home/nate/Code/aoc1kot/day4_puzzle_input.txt", { a -> day4Solution(a) }),
        Triple(Day.DAY5, "/home/nate/Code/aoc1kot/day5_puzzle_input.txt", { a -> day5Solution(a) }),
        Triple(Day.DAY6, "/home/nate/Code/aoc1kot/day6_puzzle_input.txt", { a -> day6Solution(a) })
    );

    val acceptedSolution = solutionPairs.firstOrNull { (givenDay, _) -> day == givenDay } ?: return

    val (_, filePath, solution) = acceptedSolution;

    val linesOfFile = File(filePath).bufferedReader().readLines();

    solution(linesOfFile);
}
