package pro.buildmysoftware.map

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Controller handling map API endpoints.
 */
@RestController
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
    @PostMapping
    fun createMap() {
        appService.createMap()
    }

    /**
     * Returns all islands on a map using [appService].
     */
    @GetMapping
    fun islands(): Set<Island> {
        return appService.islands()
    }
}