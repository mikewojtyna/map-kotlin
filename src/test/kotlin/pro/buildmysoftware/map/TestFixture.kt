package pro.buildmysoftware.map

import org.springframework.http.MediaType
import org.springframework.test.web.client.ExpectedCount.manyTimes
import org.springframework.test.web.client.MockRestServiceServer.createServer
import org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo
import org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess
import org.springframework.web.client.RestTemplate
import java.util.*

internal fun testMapRestTemplate(): RestTemplate {
    val restTemplate = org.springframework.web.client.RestTemplate()
    val server = createServer(
        restTemplate
    )
    server.expect(
        manyTimes(), requestTo(
            "/map"
        )
    ).andRespond(
        withSuccess(
            apiaryJsonResponse(), MediaType.APPLICATION_JSON_UTF8
        )
    )
    return restTemplate
}

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

internal fun mapWithTiles(vararg tiles: Tile): Map {
    return Map(tiles.toSet(), emptySet())
}

internal fun mapWithSingleIsland(): Map {
    return Map(randomTiles().toSet(), setOf(randomIsland()))
}

internal fun anyMap(): Map {
    return mapWithSingleIsland()
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

internal fun island(idGenerator: () -> String, vararg tiles: Tile): Island {
    return Island(tiles.toSet(), idGenerator())
}

internal fun island(vararg tiles: Tile): Island {
    return Island(tiles.toSet(), uniqueId())
}

private fun uniqueId(): String {
    return UUID.randomUUID().toString()
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