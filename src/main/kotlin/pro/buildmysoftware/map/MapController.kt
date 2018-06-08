package pro.buildmysoftware.map

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
    @PostMapping
    fun createMap() {
        appService.createMap()
    }
}