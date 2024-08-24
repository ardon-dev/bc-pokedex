package com.ardondev.bc_pokedex.data.source.remote

import com.ardondev.bc_pokedex.data.source.remote.response.pokemon.PokemonListResponse
import com.ardondev.bc_pokedex.data.source.remote.response.pokemon.PokemonResponse
import com.ardondev.bc_pokedex.data.source.remote.response.pokemon.SpeciesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): PokemonListResponse

    @GET("pokemon/{id}")
    suspend fun getPokemonDetail(
        @Path("id") id: Int
    ): PokemonResponse

    @GET("pokemon-species/{id}")
    suspend fun getPokemonSpecies(
        @Path("id") id: Int
    ): SpeciesResponse

}