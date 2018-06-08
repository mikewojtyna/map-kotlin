package pro.buildmysoftware.map

import org.springframework.data.mongodb.repository.MongoRepository

/**
 * [Map] repository.
 */
internal interface MapRepository : MongoRepository<Map, String>