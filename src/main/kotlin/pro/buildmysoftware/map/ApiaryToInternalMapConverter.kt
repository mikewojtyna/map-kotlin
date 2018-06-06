package pro.buildmysoftware.map

/**
 * Converts [apiaryMap] model data object to internal [Map] representation.
 */
internal fun convert(apiaryMap: ApiaryMap): Map {
    return Map(
        apiaryMap.tiles, findIslands(apiaryMap)
    )
}

private fun findIslands(apiaryMap: ApiaryMap): Set<Island> {
    val islands = mutableSetOf<Island>()
    var currentLandTiles = mutableSetOf<Tile>()
    fun addIsland() {
        islands.add(Island(currentLandTiles.toSet()))
    }
    apiaryMap.tiles.forEach {
        if (it.type == Tile.LAND) {
            currentLandTiles.add(it)
        } else {
            addIsland()
            currentLandTiles.clear()
        }
    }
    if (currentLandTiles.isNotEmpty())
        addIsland()
    return islands
}
