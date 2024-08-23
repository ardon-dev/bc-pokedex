package com.ardondev.bc_pokedex.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ardondev.bc_pokedex.data.source.paging.PokemonPagingSource
import com.ardondev.bc_pokedex.data.source.remote.ApiService
import com.ardondev.bc_pokedex.domain.model.pokemon.Pokemon
import com.ardondev.bc_pokedex.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PokemonRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val pokemonPagingSource: PokemonPagingSource,
) : PokemonRepository {

    override suspend fun getPokemonList(limit: Int, offset: Int): Flow<PagingData<Pokemon>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                prefetchDistance = 2
            ),
            pagingSourceFactory = { pokemonPagingSource }
        ).flow
    }

}