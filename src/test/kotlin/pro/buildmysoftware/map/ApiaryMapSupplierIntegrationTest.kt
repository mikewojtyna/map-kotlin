package pro.buildmysoftware.map

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.web.client.RestTemplate

@RestClientTest(ApiaryMapRestSupplier::class)
@ExtendWith(SpringExtension::class)
internal class ApiaryMapSupplierIntegrationTest {
    @TestConfiguration
    class RestTemplateConfig {
        @Bean
        fun restTemplate(): RestTemplate {
            return testMapRestTemplate()
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
        // when
        val apiaryMap = supplier.invoke()

        // then
        assertThat(apiaryMap.tiles).hasSize(30)
        assertThat(apiaryMap.tiles[0]).isEqualTo(landTile(1, 1))
        assertThat(apiaryMap.tiles[29]).isEqualTo(waterTile(6, 5))
    }
}