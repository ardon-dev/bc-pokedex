package com.ardondev.bc_pokedex.domain.usecase.pokemon

import androidx.paging.PagingData
import com.ardondev.bc_pokedex.domain.model.pokemon.Pokemon
import com.ardondev.bc_pokedex.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPokemonListUseCase @Inject constructor(
    private val repository: PokemonRepository,
) {

    suspend operator fun invoke(
        limit: Int,
        offset: Int,
    ): Flow<PagingData<Pokemon>> {
        return repository.getPokemonList(limit, offset)
    }

}