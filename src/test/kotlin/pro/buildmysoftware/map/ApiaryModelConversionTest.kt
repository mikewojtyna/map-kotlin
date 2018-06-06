package pro.buildmysoftware.map

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class ApiaryModelConversionTest {
    @DisplayName(
        "should convert Apiary map model with single land tile to " +
                "internal map representation containing single island with just one " +
                "tile"
    )
    @Test
    fun landOnly0() {
        // given
        val landTile = landTile(0, 0)
        val apiaryMap = ApiaryMap(setOf(landTile))

        // when
        val internalMap = convert(apiaryMap)

        // then
        assertThat(internalMap.tiles).containsOnly(landTile)
        val singleTileIsland = Island(setOf(landTile))
        assertThat(internalMap.islands).containsOnly(singleTileIsland)
    }

    @DisplayName(
        "should convert Apiary model with two land tiles to internal map " +
                "containing single island with two tiles"
    )
    @Test
    fun landOnly1() {
        // given
        val landTile0 = landTile(0, 0)
        val landTile1 = landTile(0, 1)
        val apiaryMap = ApiaryMap(setOf(landTile0, landTile1))

        // when
        val internalMap = convert(apiaryMap)

        // then
        assertThat(internalMap.tiles).containsOnly(landTile0, landTile1)
        val twoTilesIsland = Island(setOf(landTile0, landTile1))
        assertThat(internalMap.islands).containsOnly(twoTilesIsland)
    }

    @DisplayName(
        "should convert Apiary model with land and water tiles to " +
                "internal map containing single island and one water tile"
    )
    @Test
    fun landAndWater0() {
        // given
        val landTile = landTile(0, 0)
        val waterTile = waterTile(0, 1)
        val apiaryMap = ApiaryMap(setOf(landTile, waterTile))

        // when
        val internalMap = convert(apiaryMap)

        // then
        assertThat(internalMap.tiles).containsOnly(landTile, waterTile)
        val singleTileIsland = Island(setOf(landTile))
        assertThat(internalMap.islands).containsOnly(singleTileIsland)
    }

    @DisplayName(
        "should convert Apiary model with two land tiles separated " +
                "by water tile to internal map containing two single-tile islands" +
                " and one water tile"
    )
    @Test
    fun landAndWater1() {
        // given
        val landTile0 = landTile(0, 0)
        val waterTile = waterTile(1, 0)
        val landTile1 = landTile(2, 0)
        val apiaryMap = ApiaryMap(setOf(landTile0, waterTile, landTile1))

        // when
        val internalMap = convert(apiaryMap)

        // then
        assertThat(internalMap.tiles).containsOnly(
            landTile0,
            waterTile,
            landTile1
        )
        val singleTileIsland0 = Island(setOf(landTile0))
        val singleTileIsland1 = Island(setOf(landTile1))
        assertThat(internalMap.islands).containsOnly(
            singleTileIsland0,
            singleTileIsland1
        )
    }

    private fun waterTile(x: Int, y: Int): Tile {
        return Tile(x, y, Tile.WATER)
    }

    private fun landTile(x: Int, y: Int): Tile {
        return Tile(x, y, Tile.LAND)
    }
}