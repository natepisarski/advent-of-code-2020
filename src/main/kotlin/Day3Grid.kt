/**
 * Tracks what kind of tile this is in the grid (e.g tree)
 */
enum class Tile {
    SNOW,
    TREE,
}

// Tracks an individual row of the grid.
class Day3Row(line: String) {
    val tiles: List<Tile>

    init {
        val parsedTiles = mutableListOf<Tile>()

        for (character in line) {
            if (character == '.') {
                parsedTiles.add(Tile.SNOW)
            }
            if (character == '#') {
                parsedTiles.add(Tile.TREE)
            }
        }
        this.tiles = parsedTiles
    }

    /**
     * Perform a horizontal step with wrap-around.
     */
    fun hasTree(currentPosition: Int): Boolean {
        var trackedPosition = 0;

        // We go from 1 to the current position because array indexes start at 0, so going to 0 would be 1 too many.
        for (i in 1..currentPosition) {

            // We want to wrap-around here
            if (trackedPosition + 1 == tiles.count()) {
                trackedPosition = 0;
            } else {
                // If this wasn't in the else then position 0 could only happen at the point of origin.
                trackedPosition++;
            }
        }

        val tileAtDestination = this.tiles[trackedPosition];
        
        return tileAtDestination == Tile.TREE
    }
}

// Tracks the entire grid. Simply a container for an array of rows with some checks and helpers.
class Day3Grid(lines: List<String>) {
    val rows: List<Day3Row> = lines.map { line -> Day3Row(line) }

    fun simulate(xVelocity: Int, yVelocity: Int): Long {
        var xPosition = 0;
        var yPosition = 0;
        var treeCount: Long = 0;

        // We start in the first row, at the first position.
        while (yPosition < rows.count() - 1) {
            xPosition += xVelocity;
            yPosition += yVelocity;

            // The proper row to get is the y position, but first we need to check to see if we're off the bottom of the map.
            if (yPosition > rows.count() - 1) {
                return treeCount;
            }

            val row = rows[yPosition];

            if (row.hasTree(xPosition)) {
                treeCount++;
            }
        }

        return treeCount;
    }
}
