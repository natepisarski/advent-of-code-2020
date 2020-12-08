class Day6Group(line: String) {
    var uniqueLetters: MutableSet<Char> = mutableSetOf()

    init {
        // Line should be all the questions for one grup combined into one string with no spaces at this point.
        line
            .replace(" ", "")
            .forEach { c -> uniqueLetters.add(c) }

    }
}

class Day6GroupPartTwo(lines: List<String>) {
    val numberOfQuestionsAnsweredByEverybody: Int

    init {
        val allAnsweredLetters: MutableSet<Char> = mutableSetOf()
        val badAnsweredLetters: MutableSet<Char> = mutableSetOf()

        for ((index, line) in lines.withIndex()) {
            for (char in line) {
                if (allAnsweredLetters.contains(char) || badAnsweredLetters.contains(char)) {
                    continue;
                }

                var linesThatHaveThisCharacter: Int = 0

                for ((otherIndex, otherLine) in lines.withIndex()) {
                    if (index == otherIndex) {
                        continue;
                    }
                    if (otherLine.contains(char)) {
                        linesThatHaveThisCharacter++
                    } else {
                        // The first line we find that DOESN'T have this character lets us break out of the loop
                        badAnsweredLetters.add(char)
                        break;
                    }
                }

                if (linesThatHaveThisCharacter == lines.count() - 1) {
                    allAnsweredLetters.add(char)
                }
            }
        }

        numberOfQuestionsAnsweredByEverybody = allAnsweredLetters.count()
    }
}

class Day6Quiz(lines: List<String>) {
    var groups: List<Day6GroupPartTwo>

    init {
        val localListOfGroups = mutableListOf<Day6GroupPartTwo>()
        var buffer: MutableList<String> = mutableListOf()

        for (line in lines) {
            if (line.isNotEmpty()) {
                buffer.add(line)
            } else {

                localListOfGroups.add(Day6GroupPartTwo(buffer))
                buffer = mutableListOf()
            }
        }

        localListOfGroups.add(Day6GroupPartTwo(buffer))
        groups = localListOfGroups
    }

    fun getAllQuestionsAnsweredByGroups(): Int {
        var count = 0
        for (group in groups) {
            count += group.numberOfQuestionsAnsweredByEverybody
        }

        return count
    }
}
