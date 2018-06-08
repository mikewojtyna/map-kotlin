package pro.buildmysoftware.map

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest
@ExtendWith(SpringExtension::class)
internal class MapControllerIntegrationTest {
    @MockBean
    private lateinit var appService: AppService

    @DisplayName(
        "should create new map using app-level service when POST is " +
                "made on '/map' endpoint"
    )
    @Test
    fun createMap(@Autowired mockMvc: MockMvc) {
        // when
        mockMvc.perform(post("/map"))

            // then
            .andExpect(status().isOk)
        verify(appService, times(1)).createMap()
    }
}