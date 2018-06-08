package pro.buildmysoftware.map

import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

/**
 * Supplies [ApiaryMap] using REST endpoint. GET request is made to
 * harcoded "/map" endpoint. It's assumed proper root URI is configured in
 * passed [RestTemplate]. If map cannot be fetched (for any reason), a
 * default map instance is returned instead.
 */
@Service
internal class ApiaryMapRestSupplier constructor(
    private val restTemplate:
    RestTemplate
) :
        () -> ApiaryMap {
    override fun invoke(): ApiaryMap {
        return restTemplate.getForObject("/map", ApiaryMap::class.java)
                ?: ApiaryMap(ApiaryMap.Attributes(emptyList()))
    }
}