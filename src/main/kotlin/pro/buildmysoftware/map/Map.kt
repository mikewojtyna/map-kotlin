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
)