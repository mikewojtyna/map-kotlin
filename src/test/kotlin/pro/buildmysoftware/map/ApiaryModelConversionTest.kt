package pro.buildmysoftware.map

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class ApiaryModelConversionTest {
    @DisplayName("should converted map contain all tiles of original map")
    @Test
    fun mapTiles() {
        // given
        val apiaryMap = apiaryMapWithRandomTiles()

        // when
        val internalMap = convert(apiaryMap)

        // then
        assertThat(internalMap.tiles).containsOnlyElementsOf(apiaryMap.tiles)
    }

    @DisplayName(
        "should extract single-tile island when given land-only map"
    )
    @Test
    fun landOnly0() {
        // given
        val landTile = landTile(0, 0)
        /*
            #X#
        */
        val apiaryMap = apiaryMap(landTile)

        // when
        val internalMap = convert(apiaryMap)

        // then
        assertThat(internalMap.tiles).containsOnly(landTile)
        /*
            #X#
        */
        val singleTileIsland = island(landTile)
        assertThat(internalMap.islands).containsOnly(singleTileIsland)
    }

    @DisplayName(
        "should extract single-tile island when given land and water map"
    )
    @Test
    fun landAndWater0() {
        // given
        val landTile = landTile(0, 0)
        val waterTile = waterTile(0, 1)
        /*
            #X#
            #~#
        */
        val apiaryMap = apiaryMap(landTile, waterTile)

        // when
        val internalMap = convert(apiaryMap)

        // then
        assertThat(internalMap.tiles).containsOnly(landTile, waterTile)
        /*
            #X#
        */
        val singleTileIsland = island(landTile)
        assertThat(internalMap.islands).containsOnly(singleTileIsland)
    }

    @DisplayName(
        "should extract two single-tile islands when separated by water in a " +
                "single line"
    )
    @Test
    fun landAndWater1() {
        // given
        val landTile0 = landTile(0, 0)
        val waterTile = waterTile(1, 0)
        val landTile1 = landTile(2, 0)
        /*
            #X~X#
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
            #X  #
        */
        val singleTileIsland0 = island(landTile0)
        /*
            #  X#
        */
        val singleTileIsland1 = island(landTile1)
        assertThat(internalMap.islands).containsOnly(
            singleTileIsland0,
            singleTileIsland1
        )
    }

    @DisplayName(
        "should extract island with two tiles connected vertically when given" +
                " land-only map"
    )
    @Test
    fun landMultiline0() {
        // given
        val row0LandTile = landTile(0, 0)
        val row1LandTile = landTile(0, 1)
        /*
            #X#
            #X#
        */
        val apiaryMap = apiaryMap(row0LandTile, row1LandTile)

        // when
        val internalMap = convert(apiaryMap)

        // then
        assertThat(internalMap.tiles).containsOnly(row0LandTile, row1LandTile)
        /*
            #X#
            #X#
        */
        val twoRowIsland = island(row0LandTile, row1LandTile)
        assertThat(internalMap.islands).containsOnly(twoRowIsland)
    }

    @DisplayName(
        "should extract one three-tile island"
    )
    @Test
    fun landMultiline1() {
        // given
        val row0LandTile0 = landTile(0, 0)
        val row0LandTile1 = landTile(1, 0)
        val row1WaterTile = waterTile(0, 1)
        val row1LandTile = landTile(1, 1)
        /*
            #XX#
            #~X#
        */
        val apiaryMap = apiaryMap(
            row0LandTile0, row0LandTile1,
            row1WaterTile, row1LandTile
        )

        // when
        val internalMap = convert(apiaryMap)

        // then
        /*
            #XX#
            # X#
        */
        val island = island(row0LandTile0, row0LandTile1, row1LandTile)
        assertThat(internalMap.islands).containsOnly(island)
    }

    @DisplayName("should extract two three-tile islands")
    @Test
    fun complex0() {
        // given
        // row 0
        val row0LandTile0 = landTile(0, 0)
        val row0LandTile1 = landTile(1, 0)
        // row 1
        val row1WaterTile = waterTile(0, 1)
        val row1LandTile = landTile(1, 1)
        // row 2
        val row2WaterTile0 = waterTile(0, 2)
        val row2WaterTile1 = waterTile(1, 2)
        // row 3
        val row3WaterTile = waterTile(0, 3)
        val row3LandTile = landTile(1, 3)
        // row 4
        val row4LandTile0 = landTile(0, 4)
        val row4LandTile1 = landTile(1, 4)
        /*
            #XX#
            #~X#
            #~~#
            #~X#
            #XX#
        */
        val apiaryMap = apiaryMap(
            // row 0
            row0LandTile0, row0LandTile1,
            // row 1
            row1WaterTile, row1LandTile,
            // row 2
            row2WaterTile0, row2WaterTile1,
            // row 3
            row3WaterTile, row3LandTile,
            // row 4
            row4LandTile0, row4LandTile1
        )

        // when
        val internalMap = convert(apiaryMap)

        // then
        /*
            #XX#
            # X#
            #  #
            #  #
            #  #
        */
        val island0 = island(row0LandTile0, row0LandTile1, row1LandTile)
        /*
            #  #
            #  #
            #  #
            # X#
            #XX#
        */
        val island1 = island(row3LandTile, row4LandTile0, row4LandTile1)
        assertThat(internalMap.islands).containsOnly(island0, island1)
    }

    @DisplayName("should extract one big island")
    @Test
    fun complex1() {
        // given
        // row 0
        val row0WaterTile0 = waterTile(0, 0)
        val row0WaterTile1 = waterTile(1, 0)
        val row0WaterTile2 = waterTile(2, 0)
        val row0LandTile = landTile(3, 0)
        val row0WaterTile4 = waterTile(4, 0)
        val row0WaterTile5 = waterTile(5, 0)
        // row 1
        val row1WaterTile0 = waterTile(0, 1)
        val row1WaterTile1 = waterTile(1, 1)
        val row1LandTile0 = landTile(2, 1)
        val row1LandTile1 = landTile(3, 1)
        val row1LandTile2 = landTile(4, 1)
        val row1WaterTile2 = waterTile(5, 1)
        // row 2
        val row2WaterTile0 = waterTile(0, 2)
        val row2WaterTile1 = waterTile(1, 2)
        val row2WaterTile2 = waterTile(2, 2)
        val row2WaterTile3 = waterTile(3, 2)
        val row2LandTile = landTile(4, 2)
        val row2WaterTile4 = waterTile(5, 2)
        /*
            #~~~X~~#
            #~~XXX~#
            #~~~~X~#
        */
        val apiaryMap = apiaryMap(
            // row 0
            row0WaterTile0, row0WaterTile1, row0WaterTile2, row0LandTile,
            row0WaterTile4, row0WaterTile5,
            // row 1
            row1WaterTile0, row1WaterTile1, row1LandTile0, row1LandTile1,
            row1LandTile2, row1WaterTile2,
            // row 2
            row2WaterTile0, row2WaterTile1, row2WaterTile2, row2WaterTile3,
            row2LandTile, row2WaterTile4
        )

        // when
        val internalMap = convert(apiaryMap)

        // then
        /*
            #   X  #
            #  XXX #
            #    X #
        */
        val bigIsland = island(
            row0LandTile, row1LandTile0, row1LandTile1,
            row1LandTile2, row2LandTile
        )
        assertThat(internalMap.islands).containsOnly(bigIsland)
    }

    @DisplayName("should extract six small scattered islands")
    @Test
    fun complex2() {
        // given
        // row 0
        val row0LandTile0 = landTile(0, 0)
        val row0WaterTile0 = waterTile(1, 0)
        val row0LandTile1 = landTile(2, 0)
        val row0WaterTile1 = waterTile(3, 0)
        // row 1
        val row1WaterTile0 = waterTile(0, 1)
        val row1LandTile0 = landTile(1, 1)
        val row1WaterTile1 = waterTile(2, 1)
        val row1LandTile1 = landTile(3, 1)
        // row 2
        val row2LandTile0 = landTile(0, 2)
        val row2WaterTile0 = waterTile(1, 2)
        val row2LandTile1 = landTile(2, 2)
        val row2WaterTile1 = waterTile(3, 2)
        /*
            #X~X~#
            #~X~X#
            #X~X~#
        */
        val apiaryMap = apiaryMap(
            // row 0
            row0LandTile0, row0WaterTile0, row0LandTile1, row0WaterTile1,
            // row 1
            row1WaterTile0, row1LandTile0, row1WaterTile1, row1LandTile1,
            // row 2
            row2LandTile0, row2WaterTile0, row2LandTile1, row2WaterTile1
        )

        // when
        val internalMap = convert(apiaryMap)

        // then
        /*
            #X   #
            #    #
            #    #
        */
        val island0 = island(row0LandTile0)
        /*
            #  X #
            #    #
            #    #
        */
        val island1 = island(row0LandTile1)
        /*
            #    #
            # X  #
            #    #
        */
        val island2 = island(row1LandTile0)
        /*
            #    #
            #   X#
            #    #
        */
        val island3 = island(row1LandTile1)
        /*
            #    #
            #    #
            #X   #
        */
        val island4 = island(row2LandTile0)
        /*
            #    #
            #    #
            #  X #
        */
        val island5 = island(row2LandTile1)
        assertThat(internalMap.islands).containsOnly(
            island0, island1,
            island2, island3, island4, island5
        )
    }

    private fun apiaryMapWithRandomTiles(): ApiaryMap {
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
        return ApiaryMap(tiles)
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