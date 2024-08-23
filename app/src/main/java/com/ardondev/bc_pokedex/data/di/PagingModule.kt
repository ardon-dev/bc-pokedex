package com.ardondev.bc_pokedex.data.di

import com.ardondev.bc_pokedex.data.source.paging.PokemonPagingSource
import com.ardondev.bc_pokedex.data.source.remote.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class PagingModule {

    @Provides
    fun providePokemonPagingSource(apiService: ApiService): PokemonPagingSource {
        return PokemonPagingSource(apiService)
    }

}