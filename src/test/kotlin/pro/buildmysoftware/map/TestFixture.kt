package pro.buildmysoftware.map

internal fun apiaryJsonResponse(): String {
    return """
        {
            "data": {
                "id": "imaginary",
                "type": "map",
                "links": {
                    "self": "https://private-2e8649-advapi.apiary-mock.com/map"
                }
            },
            "attributes": {
                "tiles": [
                    { "x": 1, "y": 1, "type": "land" },
                    { "x": 2, "y": 1, "type": "land" },
                    { "x": 3, "y": 1, "type": "water" },
                    { "x": 4, "y": 1, "type": "water" },
                    { "x": 5, "y": 1, "type": "land" },
                    { "x": 6, "y": 1, "type": "water" },
                    { "x": 1, "y": 2, "type": "water" },
                    { "x": 2, "y": 2, "type": "land" },
                    { "x": 3, "y": 2, "type": "water" },
                    { "x": 4, "y": 2, "type": "water" },
                    { "x": 5, "y": 2, "type": "water" },
                    { "x": 6, "y": 2, "type": "water" },
                    { "x": 1, "y": 3, "type": "water" },
                    { "x": 2, "y": 3, "type": "water" },
                    { "x": 3, "y": 3, "type": "water" },
                    { "x": 4, "y": 3, "type": "water" },
                    { "x": 5, "y": 3, "type": "land" },
                    { "x": 6, "y": 3, "type": "water" },
                    { "x": 1, "y": 4, "type": "water" },
                    { "x": 2, "y": 4, "type": "water" },
                    { "x": 3, "y": 4, "type": "land" },
                    { "x": 4, "y": 4, "type": "land" },
                    { "x": 5, "y": 4, "type": "land" },
                    { "x": 6, "y": 4, "type": "water" },
                    { "x": 1, "y": 5, "type": "water" },
                    { "x": 2, "y": 5, "type": "water" },
                    { "x": 3, "y": 5, "type": "water" },
                    { "x": 4, "y": 5, "type": "land" },
                    { "x": 5, "y": 5, "type": "water" },
                    { "x": 6, "y": 5, "type": "water" }
                ]
            }
        }
        """
}

internal fun anyMap(): Map {
    return Map(randomTiles().toSet(), setOf(randomIsland()))
}

private fun randomIsland(): Island {
    return island(*randomTiles().toTypedArray())
}

internal fun apiaryMapWithRandomTiles(): ApiaryMap {
    return ApiaryMap(ApiaryMap.Attributes(randomTiles()))
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
    return ApiaryMap(ApiaryMap.Attributes(tiles.toList()))
}

internal fun waterTile(x: Int, y: Int): Tile {
    return Tile(x, y, Tile.WATER)
}

internal fun landTile(x: Int, y: Int): Tile {
    return Tile(x, y, Tile.LAND)
}