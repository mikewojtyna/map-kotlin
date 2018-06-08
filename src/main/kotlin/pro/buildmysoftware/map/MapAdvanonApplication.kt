package pro.buildmysoftware.map

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
/**
 * Required by Spring to bootstrap the application.
 */
class MapAdvanonApplication

/**
 * Starts the Spring application.
 */
fun main(args: Array<String>) {
    runApplication<MapAdvanonApplication>(*args)
}
