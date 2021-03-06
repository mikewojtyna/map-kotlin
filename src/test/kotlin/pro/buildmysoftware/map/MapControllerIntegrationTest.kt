package pro.buildmysoftware.map

import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest
@ExtendWith(SpringExtension::class)
internal class MapControllerIntegrationTest {
    @MockBean
    private lateinit var appService: AppService
    @Autowired
    private lateinit var mockMvc: MockMvc

    @DisplayName(
        "should create new map using app-level service when POST is " +
                "made on '/api/maps' endpoint"
    )
    @Test
    fun createMap() {
        // when
        mockMvc.perform(post("/api/maps"))

            // then
            .andExpect(status().isOk)
        verify(appService, times(1)).createMap()
    }

    @DisplayName(
        "should get all islands using app-level service when GET is " +
                "made on '/api/islands'"
    )
    @Test
    fun islands() {
        // given
        `when`(appService.islands()).thenReturn(
            setOf(
                island
                    (
                    { "island-id" },
                    landTile(1, 2)
                )
            )
        )

        // when
        mockMvc.perform(get("/api/islands"))
            // then
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].id", `is`("island-id")))
            .andExpect(jsonPath("$[0].landTiles[0].x", `is`(1)))
            .andExpect(jsonPath("$[0].landTiles[0].y", `is`(2)))
        verify(appService, times(1)).islands()
    }

    @DisplayName(
        "should find island by id using app-level service when GET " +
                "is made on /api/islands/:id"
    )
    @Test
    fun islandById() {
        // given
        `when`(appService.islandById("island-id")).thenReturn(
            island
                (
                { "island-id" },
                landTile(1, 2)
            )
        )

        // when
        mockMvc.perform(get("/api/islands/{id}", "island-id"))
            // then
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id", `is`("island-id")))
            .andExpect(jsonPath("$.landTiles[0].x", `is`(1)))
            .andExpect(jsonPath("$.landTiles[0].y", `is`(2)))
        verify(appService, times(1)).islandById("island-id")
    }

    @DisplayName(
        "should return ascii-art map representation when GET with " +
                "Accept header 'text/plain' is made on /api/maps"
    )
    @Test
    fun asciiArt() {
        // given
        `when`(appService.mapAsAsciiArt()).thenReturn("#X~X#")

        // when
        mockMvc.perform(get("/api/maps").accept(MediaType.TEXT_PLAIN))

            // then
            .andExpect(status().isOk)
            .andExpect(content().string("#X~X#"))
        verify(appService, times(1)).mapAsAsciiArt()
    }
}