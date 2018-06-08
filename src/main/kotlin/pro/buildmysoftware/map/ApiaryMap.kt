package pro.buildmysoftware.map

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Apiary map data model.
 */
internal class ApiaryMap constructor(
    /**
     * Attributes field mapping. Contains "tiles" of this map.
     */
    @JsonProperty("attributes") attributes: Attributes
) {
    val tiles: List<Tile> = attributes.tiles

    class Attributes constructor(val tiles: List<Tile>)
}



