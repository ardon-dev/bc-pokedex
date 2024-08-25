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

    fun getId(): Int? {
        url?.let {
            val pattern = Regex(".*/pokemon/(\\d+)/")
            return pattern.find(url)?.groupValues?.get(1)?.toInt()
        }
        return -1
    }

    fun toModel(): Pokemon {
        return Pokemon(
            name = this.name,
            url = this.url,
            id = this.getId()
        )
    }

}


