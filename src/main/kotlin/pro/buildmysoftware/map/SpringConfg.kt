package pro.buildmysoftware.map

import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate
import java.util.*

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
    fun restTemplate(builder: RestTemplateBuilder): RestTemplate {
        return builder.rootUri(
            "https://private-2e8649-advapi.apiary-mock" +
                    ".com"
        ).build()
    }

    /**
     * Returns a reference to the conversion function strategy currently used
     * in the application. This strategy uses a [convert] function supplied
     * with [UUID] island id generator.
     */
    @Bean
    fun mapConverter(): (ApiaryMap) -> Map {
        return { apiaryMap ->
            convert(
                apiaryMap,
                { UUID.randomUUID().toString() })
        }
    }
}