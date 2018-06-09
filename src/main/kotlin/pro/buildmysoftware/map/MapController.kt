package pro.buildmysoftware.map

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

/**
 * Controller handling map API endpoints.
 */
@RestController
@RequestMapping("/api")
internal class MapController(
    /**
     * [AppService] that will handle all operations, so this controller can
     * stay "clean" and focus solely on the presentation layer.
     */
    private val appService: AppService
) {
    /**
     * Creates a new map using [appService].
     */
    @PostMapping("/maps")
    fun createMap() {
        appService.createMap()
    }

    /**
     * Returns ascii-art representation of map using [appService].
     */
    @GetMapping("/maps", produces = [MediaType.TEXT_PLAIN_VALUE])
    fun mapAsAsciiArt() = appService.mapAsAsciiArt()

    /**
     * Returns all islands on a map using [appService].
     */
    @GetMapping("/islands")
    fun islands(): Set<Island> {
        return appService.islands()
    }

    /**
     * Returns the island by [id].
     */
    @GetMapping("/islands/{id}")
    fun islandById(@PathVariable id: String): Island? {
        return appService.islandById(id)
    }
}