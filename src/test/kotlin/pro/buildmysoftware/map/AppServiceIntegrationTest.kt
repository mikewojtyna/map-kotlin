package pro.buildmysoftware.map

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess
import org.springframework.web.client.RestTemplate

@SpringBootTest
@ExtendWith(SpringExtension::class)
internal class AppServiceIntegrationTest {
    @TestConfiguration
    class RestTemplateConfig {
        @Bean
        @Primary
        fun restTemplate(): RestTemplate {
            val restTemplate = RestTemplate()
            val server =
                org.springframework.test.web.client.MockRestServiceServer.createServer(
                    restTemplate
                )
            server.expect(
                org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo(
                    "/map"
                )
            )
                .andRespond(
                    withSuccess
                        (
                        apiaryJsonResponse(),
                        MediaType.asMediaType(MediaType.APPLICATION_JSON_UTF8)
                    )
                )
            return restTemplate
        }
    }

    @DisplayName("should create new map and save it in repository")
    @Test
    fun createMap(
        @Autowired appService: AppService, @Autowired mapRepository:
        MapRepository
    ) {
        // given
        assertThat(mapRepository.findAll()).isEmpty()

        // when
        appService.createMap()

        // then
        val mapsFromRepo = mapRepository.findAll()
        assertThat(mapsFromRepo).hasSize(1)
    }
}