package pro.buildmysoftware.map

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.client.MockRestServiceServer
import org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo
import org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess
import org.springframework.web.client.RestTemplate

@RestClientTest(ApiaryMapRestSupplier::class)
@ExtendWith(SpringExtension::class)
internal class ApiaryMapSupplierIntegrationTest {

    @TestConfiguration
    class RestTemplateConfig {
        @Bean
        fun restTemplate(): RestTemplate {
            return RestTemplate()
        }
    }

    @DisplayName(
        "should GET '/map' endpoint and return deserialized " +
                "ApiaryMap instance"
    )
    @Test
    fun getMap(
        @Autowired supplier: () -> ApiaryMap, @Autowired restTemplate: RestTemplate
    ) {
        // given
        val server = MockRestServiceServer.createServer(restTemplate)
        server.expect(requestTo("/map"))
            .andRespond(
                withSuccess
                    (
                    apiaryJsonResponse(),
                    MediaType.asMediaType(MediaType.APPLICATION_JSON_UTF8)
                )
            )

        // when
        val apiaryMap = supplier.invoke()

        // then
        assertThat(apiaryMap.tiles).hasSize(30)
        assertThat(apiaryMap.tiles[0]).isEqualTo(landTile(1, 1))
        assertThat(apiaryMap.tiles[29]).isEqualTo(waterTile(6, 5))
    }

    private fun apiaryJsonResponse(): String {
        return pro.buildmysoftware.map.apiaryJsonResponse()
    }
}