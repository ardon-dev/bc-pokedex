package com.ardondev.bc_pokedex.data.source.remote.response.pokemon

import com.ardondev.bc_pokedex.domain.model.pokemon.Pokemon
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PokemonListResult(
    @Json(name = "name")
    val name: String?,
    @Json(name = "url")
    val url: String?,
) {

    fun getId(): String {
        return url?.substringBefore("/")?.substringAfterLast("/") ?: "0"
    }

}

fun PokemonListResult.toModel(): Pokemon {
    return Pokemon(
        name = this.name,
        url = this.url,
        id = this.getId()
    )
}
