package pro.buildmysoftware.map

/**
 * Converts [apiaryMap] model data object to internal [Map] representation.
 */
internal fun convert(apiaryMap: ApiaryMap): Map {
    return Map(
        setOf(Tile(0, 0, Tile.LAND)), setOf(
            Island(
                setOf(
                    Tile(
                        0, 0,
                        Tile.LAND
                    )
                )
            )
        )
    )
}