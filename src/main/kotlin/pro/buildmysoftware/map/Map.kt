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
    val islands: Set<Island>
)

/**
 * Island data model used in internal [Map] representation.
 */
internal class Island(
    /**
     * Land tiles which form this island.
     */
    val landTiles: Set<Tile>
)