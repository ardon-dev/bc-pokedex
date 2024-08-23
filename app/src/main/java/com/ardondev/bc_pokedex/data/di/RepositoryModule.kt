package com.ardondev.bc_pokedex.data.di

import com.ardondev.bc_pokedex.data.repository.PokemonRepositoryImpl
import com.ardondev.bc_pokedex.data.source.paging.PokemonPagingSource
import com.ardondev.bc_pokedex.data.source.remote.ApiService
import com.ardondev.bc_pokedex.domain.repository.PokemonRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    fun providePokemonRepository(
        apiService: ApiService,
        pokemonPagingSource: PokemonPagingSource,
    ): PokemonRepository {
        return PokemonRepositoryImpl(apiService, pokemonPagingSource)
    }

}