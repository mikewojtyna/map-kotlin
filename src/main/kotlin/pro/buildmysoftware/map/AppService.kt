package pro.buildmysoftware.map

import org.springframework.stereotype.Service

/**
 * A usual application layer service providing methods for all use cases.
 */
@Service
internal class AppService(
    /**
     * A [Map] repository that.
     */
    private val mapRepository: MapRepository,
    /**
     * Supplier of [ApiaryMap]s.
     */
    private val
    apiaryMapSupplier: () -> ApiaryMap,
    /**
     * A converter to translate [ApiaryMap] into internal [Map] representation.
     */
    private val mapConverter: (ApiaryMap) -> Map
) {
    /**
     * Creates a new map using [apiaryMapSupplier] as Apiary service map
     * source and saves it using the [mapRepository].
     */
    fun createMap() {
        mapRepository.save(mapConverter(apiaryMapSupplier()))
    }

    /**
     * Returns all islands of a [Map]. If map doesn't yet exist, an empty set
     * is returned instead.
     */
    fun islands(): Set<Island> = findMap()?.islands ?: emptySet()

    private fun findMap(): Map? = mapRepository.findAll().firstOrNull()

    /**
     * Returns an [Island] by [id]. If island with given [id] doesn't exist
     * or [Map] doesn't exist yet - a null value is returned instead.
     */
    fun islandById(id: String): Island? {
        return findMap()?.islands?.find { it.id == id }
    }

    /**
     * Returns an ascii-art representation of [Map]. If map doesn't exist
     * yet, an empty string is returned instead.
     */
    fun mapAsAsciiArt(): String {
        return findMap()?.toString() ?: ""
    }
}