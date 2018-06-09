package pro.buildmysoftware.map

/**
 * Internal map data model.
 */
internal class Map(
    /**
     * Tiles of this map.
     */
    val tiles: Set<Tile>,
    /**
     * All islands on this map.
     */
    val islands: Set<Island>,
    /**
     * Id used by repository layer.
     */
    val id: String? = null
) {
    override fun toString(): String {
        val border = "#"
        if (tiles.isEmpty()) return ""

        val stringBuilder = StringBuilder(border)
        val width = tiles.map { it.x }.max() ?: 0
        var processedTiles = 1
        val numberOfTiles = tiles.size
        tiles.forEach {
            val tileChar = if (it.type == Tile.LAND) "X" else "~"
            stringBuilder.append(tileChar)
            if (it.x == width) {
                if (processedTiles < numberOfTiles)
                    stringBuilder
                        .appendln(border).append(border)
                else
                    stringBuilder.append(border)
            }
            processedTiles++
        }
        return stringBuilder.toString()
    }
}