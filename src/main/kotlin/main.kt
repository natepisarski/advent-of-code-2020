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

enum class Day {
    DAY1,
    DAY2,
}

fun main(args: Array<String>) {
    val day = Day.DAY2;

    val solutionPairs = arrayOf(
        Triple<Day, String, (a: List<String>) -> Unit>(Day.DAY1, "/home/nate/Code/aoc1kot/day1_puzzle_input.txt", { a -> day1Solution(a) }),
        Triple(Day.DAY2, "/home/nate/Code/aoc1kot/day2_puzzle_input.txt", { a -> day2Solution(a)}),
    );

    val acceptedSolution = solutionPairs.firstOrNull { (givenDay, _) -> day == givenDay } ?: return

    val (_, filePath, solution) = acceptedSolution;

    val linesOfFile = File(filePath).bufferedReader().readLines();

    solution(linesOfFile);
}
