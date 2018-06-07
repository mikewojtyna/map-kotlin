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
        /*
            #X
        */
        val apiaryMap = apiaryMap(landTile)

        // when
        val internalMap = convert(apiaryMap)

        // then
        assertThat(internalMap.tiles).containsOnly(landTile)
        /*
            #X
        */
        val singleTileIsland = island(landTile)
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
        /*
            #X
            #X
        */
        val apiaryMap = apiaryMap(landTile0, landTile1)

        // when
        val internalMap = convert(apiaryMap)

        // then
        assertThat(internalMap.tiles).containsOnly(landTile0, landTile1)
        /*
            #X
            #X
        */
        val twoTilesIsland = island(landTile0, landTile1)
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
        /*
            #X
            #~
        */
        val apiaryMap = apiaryMap(landTile, waterTile)

        // when
        val internalMap = convert(apiaryMap)

        // then
        assertThat(internalMap.tiles).containsOnly(landTile, waterTile)
        /*
            #X
        */
        val singleTileIsland = island(landTile)
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
        /*
            #X~X
        */
        val apiaryMap = apiaryMap(landTile0, waterTile, landTile1)

        // when
        val internalMap = convert(apiaryMap)

        // then
        assertThat(internalMap.tiles).containsOnly(
            landTile0,
            waterTile,
            landTile1
        )
        /*
            #X
        */
        val singleTileIsland0 = island(landTile0)
        /*
            #  X
        */
        val singleTileIsland1 = island(landTile1)
        assertThat(internalMap.islands).containsOnly(
            singleTileIsland0,
            singleTileIsland1
        )
    }

    @DisplayName(
        "should convert Apiary model with two connected land rows containing " +
                "single tile each to internal map containing single two-row " +
                "island"
    )
    @Test
    fun landMultiline0() {
        // given
        val row0LandTile = landTile(0, 0)
        val row1LandTile = landTile(0, 1)
        /*
            #X
            #X
        */
        val apiaryMap = apiaryMap(row0LandTile, row1LandTile)

        // when
        val internalMap = convert(apiaryMap)

        // then
        assertThat(internalMap.tiles).containsOnly(row0LandTile, row1LandTile)
        /*
            #X
            #X
        */
        val twoRowIsland = island(row0LandTile, row1LandTile)
        assertThat(internalMap.islands).containsOnly(twoRowIsland)
    }

    @DisplayName(
        "should convert Apiary model with three land tiles connected " +
                "vertically in different rows separated by water to internal map " +
                "containing single three-tile island"
    )
    @Test
    fun landMultiline1() {
        // given
        val row0LandTile0 = landTile(0, 0)
        val row0LandTile1 = landTile(1, 0)
        val row1WaterTile = waterTile(0, 1)
        val row1LandTile = landTile(1, 1)
        /*
            #XX
            #~X
        */
        val apiaryMap = apiaryMap(
            row0LandTile0, row0LandTile1,
            row1WaterTile, row1LandTile
        )

        // when
        val internalMap = convert(apiaryMap)

        // then
        /*
            #XX
            # X
        */
        val island = island(row0LandTile0, row0LandTile1, row1LandTile)
        assertThat(internalMap.islands).containsOnly(island)
    }

    private fun island(vararg tiles: Tile): Island {
        return Island(tiles.toSet())
    }

    private fun apiaryMap(vararg tiles: Tile): ApiaryMap {
        return ApiaryMap(tiles.toList())
    }

    private fun waterTile(x: Int, y: Int): Tile {
        return Tile(x, y, Tile.WATER)
    }

    private fun landTile(x: Int, y: Int): Tile {
        return Tile(x, y, Tile.LAND)
    }
}