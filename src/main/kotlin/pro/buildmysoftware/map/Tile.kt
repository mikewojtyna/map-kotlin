package pro.buildmysoftware.map

/**
 * Represents a tile on a map. Tile can be can be either [Tile.LAND] or [Tile.WATER].
 */
internal data class Tile(
    /**
     * X coordinate on the map.
     */
    val x: Int,
    /**
     * Y coordinate on the map.
     */
    val y: Int,
    /**
     * Type of the tile.
     */
    val type: String
) {
    companion object {
        /**
         * Land tile type.
         */
        internal const val LAND = "land"
        /**
         * Water tile type.
         */
        internal const val WATER = "water"
    }
}