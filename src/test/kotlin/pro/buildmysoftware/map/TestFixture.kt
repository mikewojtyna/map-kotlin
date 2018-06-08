package pro.buildmysoftware.map

internal fun anyMap(): Map {
    return Map(randomTiles().toSet(), setOf(randomIsland()))
}

private fun randomIsland(): Island {
    return island(*randomTiles().toTypedArray())
}

internal fun apiaryMapWithRandomTiles(): ApiaryMap {
    return ApiaryMap(randomTiles())
}

private fun randomTiles(): List<Tile> {
    val tiles = mutableListOf<Tile>()
    for (y in 0..10) {
        for (x in 0..10) {
            tiles.add(
                Tile(
                    x, y, if ((x + y) % 2 == 0) Tile.LAND else
                        Tile.WATER
                )
            )
        }
    }
    return tiles
}

internal fun island(vararg tiles: Tile): Island {
    return Island(tiles.toSet())
}

internal fun apiaryMap(vararg tiles: Tile): ApiaryMap {
    return ApiaryMap(tiles.toList())
}

internal fun waterTile(x: Int, y: Int): Tile {
    return Tile(x, y, Tile.WATER)
}

internal fun landTile(x: Int, y: Int): Tile {
    return Tile(x, y, Tile.LAND)
}