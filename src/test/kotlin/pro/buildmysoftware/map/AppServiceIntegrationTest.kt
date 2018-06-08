package pro.buildmysoftware.map

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.web.client.RestTemplate

@SpringBootTest
@ExtendWith(SpringExtension::class)
internal class AppServiceIntegrationTest {
    @Autowired
    private lateinit var appService: AppService
    @Autowired
    private lateinit var mapRepository: MapRepository

    @AfterEach
    private fun cleanDb() {
        mapRepository.deleteAll()
    }

    @TestConfiguration
    class RestTemplateConfig {
        @Bean
        @Primary
        fun restTemplate(): RestTemplate {
            return testMapRestTemplate()
        }
    }

    @DisplayName("should create new map and save it in repository")
    @Test
    fun createMap() {
        // given
        assertThat(mapRepository.count()).isEqualTo(0)

        // when
        appService.createMap()

        // then
        val mapsFromRepo = mapRepository.findAll()
        assertThat(mapsFromRepo).hasSize(1)
    }

    @DisplayName("should return all islands when map exists")
    @Test
    fun getIslands0() {
        // given
        createMap()

        // when
        val islands = appService.islands()

        // then
        assertThat(islands).isNotEmpty
    }

    @DisplayName("should return empty set when map doesn't exist")
    @Test
    fun getIslands1() {
        // when
        val islands = appService.islands()

        // then
        assertThat(islands).isEmpty()
    }
}