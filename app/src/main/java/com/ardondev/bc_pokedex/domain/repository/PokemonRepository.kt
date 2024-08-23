package com.ardondev.bc_pokedex.domain.repository

import androidx.paging.PagingData
import com.ardondev.bc_pokedex.data.source.remote.response.pokemon.PokemonResponse
import com.ardondev.bc_pokedex.domain.model.pokemon.Pokemon
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {

    suspend fun getPokemonList(
        limit: Int,
        offset: Int
    ): Flow<PagingData<Pokemon>>

    suspend fun getPokemonDetail(id: Int): PokemonResponse

}