package pro.buildmysoftware.map

/**
 * Island data model used in internal [Map] representation.
 */
internal data class Island(
    /**
     * Land tiles which form this island.
     */
    val landTiles: Set<Tile>,
    /**
     * Id of this island.
     */
    val id: String
)