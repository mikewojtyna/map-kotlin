package pro.buildmysoftware.map

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

internal class MapAsciiArtTest {
    @DisplayName("should create ascii art when map has 1 row")
    @Test
    fun asciiArt0() {
        // given
        val map =
            mapWithTiles(landTile(0, 0), waterTile(1, 0), landTile(2, 0))

        // when
        val asciiArt = map.toString()

        // then
        assertThat(asciiArt).isEqualTo("#X~X#")
    }

    @DisplayName("should create ascii art when map has 2 rows")
    @Test
    fun asciiArt1() {
        // given
        val map =
            mapWithTiles(
                // row0
                landTile(0, 0), waterTile(1, 0), landTile(2, 0),
                // row 1
                waterTile(0, 1), landTile(1, 1), waterTile(2, 1)
            )

        // when
        val asciiArt = map.toString()

        // then
        assertThat(asciiArt).isEqualTo(
            """
                |#X~X#
                |#~X~#
            """.trimMargin()
        )
    }

    @DisplayName("should create ascii art when map is empty")
    @Test
    fun asciiArt2() {
        // given
        val map =
            mapWithTiles()

        // when
        val asciiArt = map.toString()

        // then
        assertThat(asciiArt).isEmpty()
    }
}