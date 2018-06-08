package pro.buildmysoftware.map

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@DataMongoTest
@ExtendWith(SpringExtension::class)
internal class MapRepositoryIntegrationTest {
    @DisplayName("should save and find new map")
    @Test
    fun save(@Autowired repository: MapRepository) {
        // given
        val map = anyMap()

        // when
        val savedMap = repository.save(map)

        // then
        val foundMap = repository.findById(savedMap.id!!).get()
        assertThat(foundMap.tiles).isEqualTo(map.tiles)
        assertThat(foundMap.islands).isEqualTo(map.islands)
    }
}