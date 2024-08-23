package com.ardondev.bc_pokedex.data.source.remote

import com.ardondev.bc_pokedex.data.source.remote.response.pokemon.PokemonListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): PokemonListResponse

}