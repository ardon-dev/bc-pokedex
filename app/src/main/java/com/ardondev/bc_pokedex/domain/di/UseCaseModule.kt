package com.ardondev.bc_pokedex.domain.di

import com.ardondev.bc_pokedex.domain.repository.PokemonRepository
import com.ardondev.bc_pokedex.domain.usecase.pokemon.GetPokemonListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Provides
    fun provideGetPokemonListUseCase(
        repository: PokemonRepository,
    ): GetPokemonListUseCase {
        return GetPokemonListUseCase(repository)
    }

}