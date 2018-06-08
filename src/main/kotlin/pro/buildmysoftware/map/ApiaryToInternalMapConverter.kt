package pro.buildmysoftware.map

import org.jgrapht.Graph
import org.jgrapht.alg.connectivity.ConnectivityInspector
import org.jgrapht.graph.DefaultEdge
import org.jgrapht.graph.SimpleGraph
import kotlin.math.abs

/**
 * Converts [apiaryMap] model data object to internal [Map] representation.
 * An [islandIdGenerator] is used to create unique ids for each extracted
 * island.
 */
internal fun convert(apiaryMap: ApiaryMap, islandIdGenerator: () -> String):
        Map {
    fun findIslands(apiaryMap: ApiaryMap): Set<Island> {
        fun buildIslandsGraph(apiaryMap: ApiaryMap): Graph<Tile, DefaultEdge> {
            fun isAdjacentTo(source: Tile, target: Tile): Boolean {
                val connectedVertically =
                    (source.x == target.x) && (abs(source.y - target.y) == 1)
                val connectedHorizontally = (source.y == target.y) && (abs(
                    source
                        .x - target.x
                ) == 1)
                return connectedVertically || connectedHorizontally
            }

            val graph: SimpleGraph<Tile, DefaultEdge> = SimpleGraph(
                DefaultEdge::class.java
            )
            val landTiles = apiaryMap.tiles.filter { it.type == Tile.LAND }
            landTiles.forEach { graph.addVertex(it) }
            landTiles.forEach { sourceTile ->
                landTiles.forEach { targetTile ->
                    if (isAdjacentTo(sourceTile, targetTile))
                        graph.addEdge(sourceTile, targetTile)
                }
            }
            return graph
        }
        return ConnectivityInspector(buildIslandsGraph(apiaryMap))
            .connectedSets().map { Island(it, islandIdGenerator()) }
            .toSet()
    }
    return Map(
        apiaryMap.tiles.toSet(), findIslands(apiaryMap)
    )
}


