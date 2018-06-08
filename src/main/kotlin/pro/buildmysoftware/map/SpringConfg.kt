package pro.buildmysoftware.map

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

/**
 * Spring beans configuration.
 */
@Configuration
internal class SpringConfg {
    /**
     * Creates a configured [RestTemplate] which is used to communicate with
     * the Apiary REST service.
     */
    @Bean
    fun restTemplate(): RestTemplate {
        return RestTemplate()
    }

    /**
     * Returns a reference to the conversion function strategy currently used
     * in the application.
     */
    @Bean
    fun mapConverter(): (ApiaryMap) -> Map {
        return ::convert
    }
}