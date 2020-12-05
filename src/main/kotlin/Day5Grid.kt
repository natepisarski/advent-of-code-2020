private class Day5Row {

}

class Day5Grid {

}

class Day5BoardingPass(pass: String) {
    private val boardingPass: String = pass

    fun getPassId(): Pair<Int, Int> {
        var rowUpperBound = 128
        var rowLowerBound = 0
        var columnUpperBound = 8
        var columnLowerBound = 0

        for (letter in boardingPass) {
            // Here the letter can be either: F,B (for rows) or R,L (for columns)
            when (letter) {
                'F' -> {
                    rowUpperBound -= (rowUpperBound - rowLowerBound) / 2
                }
                'B' -> {
                    rowLowerBound += (rowUpperBound - rowLowerBound) / 2
                }
                'L' -> {
                    columnUpperBound -= (columnUpperBound - columnLowerBound) / 2
                }
                'R' -> {
                    columnLowerBound += (columnUpperBound - columnLowerBound) / 2
                }
                else -> {
                    println("Bad letter detected: $letter")
                }
            }
        }

//        return rowLowerBound * 8 + columnLowerBound // Problem One Solution
        return Pair(rowLowerBound, columnLowerBound)
    }
}
