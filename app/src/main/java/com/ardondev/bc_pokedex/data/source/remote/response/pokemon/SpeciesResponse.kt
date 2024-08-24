package com.ardondev.bc_pokedex.data.source.remote.response.pokemon

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SpeciesResponse(
    @Json(name = "flavor_text_entries")
    val flavorTextEntries: List<FlavorTextEntry>?
) {

    @JsonClass(generateAdapter = true)
    data class FlavorTextEntry(
        @Json(name = "flavor_text")
        val flavorText: String?,
        @Json(name = "language")
        val language: Language?
    )

    @JsonClass(generateAdapter = true)
    data class Language(
        @Json(name = "name")
        val name: String?,
        @Json(name = "url")
        val url: String?,
    )

}

