package com.ardondev.bc_pokedex.data.source.remote.response.pokemon

import com.ardondev.bc_pokedex.domain.model.pokemon.Pokemon
import com.ardondev.bc_pokedex.domain.model.pokemon.Type
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PokemonResponse(
    @Json(name = "base_experience")
    val baseExperience: Int?,
    @Json(name = "height")
    val height: Int?,
    @Json(name = "id")
    val id: Int?,
    @Json(name = "is_default")
    val isDefault: Boolean?,
    @Json(name = "location_area_encounters")
    val locationAreaEncounters: String?,
    @Json(name = "name")
    val name: String?,
    @Json(name = "order")
    val order: Int?,
    @Json(name = "species")
    val species: Species?,
    @Json(name = "stats")
    val stats: List<Stat>?,
    @Json(name = "weight")
    val weight: Int?,
    @Json(name = "types")
    val types: List<Type>?
) {

    @JsonClass(generateAdapter = true)
    data class Species(
        @Json(name = "name")
        val name: String?,
        @Json(name = "url")
        val url: String?,
    )

    @JsonClass(generateAdapter = true)
    data class Stat(
        @Json(name = "base_stat")
        val baseStat: Int?,
        @Json(name = "effort")
        val effort: Int?,
        @Json(name = "stat")
        val statX: StatX?
    ) {

        @JsonClass(generateAdapter = true)
        data class StatX(
            @Json(name = "name")
            val name: String?,
            @Json(name = "url")
            val url: String?,
        ) {

            fun getId(): Int? {
                url?.let {
                    val pattern = Regex(".*/stat/(\\d+)/")
                    return pattern.find(url)?.groupValues?.get(1)?.toInt()
                }
                return -1
            }

        }

        fun toModel(): com.ardondev.bc_pokedex.domain.model.pokemon.Stat {
            return com.ardondev.bc_pokedex.domain.model.pokemon.Stat(
                id = this.statX?.getId(),
                name = this.statX?.name,
                base = this.baseStat,
                effort = this.effort
            )
        }

    }

    @JsonClass(generateAdapter = true)
    data class Type(
        @Json(name = "slot")
        val slot: Int?,
        @Json(name = "type")
        val typeX: TypeX?,
    ) {

        @JsonClass(generateAdapter = true)
        data class TypeX(
            @Json(name = "name")
            val name: String?,
            @Json(name = "url")
            val url: String?,
        ) {

            fun getId(): Int? {
                url?.let {
                    val pattern = Regex(".*/type/(\\d+)/")
                    return pattern.find(url)?.groupValues?.get(1)?.toInt()
                }
                return -1
            }

        }

        fun toModel(): com.ardondev.bc_pokedex.domain.model.pokemon.Type {
            return Type(
                id = this.typeX?.getId(),
                name = this.typeX?.name,
            )
        }

    }

    fun toModel(): Pokemon {
        val types = this.types?.map { it.toModel() }
        val stats = this.stats?.map { it.toModel() }

        return Pokemon(
            id = this.id,
            name = this.name,
            url = null,
            weight = this.weight,
            height = this.height,
            types = types,
            stats = stats
        )
    }

}

