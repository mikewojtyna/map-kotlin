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
    fun convertModel() {
        // given
        val landTile = Tile(0, 0, Tile.LAND)
        val singleTileIsland = Island(setOf(landTile))
        val apiaryMap = ApiaryMap(setOf(landTile))

        // when
        val internalMap = convert(apiaryMap)

        // then
        assertThat(internalMap.tiles).containsOnly(landTile)
        assertThat(internalMap.islands).containsOnly(singleTileIsland)
    }
}