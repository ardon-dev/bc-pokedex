package com.ardondev.bc_pokedex.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ardondev.bc_pokedex.domain.model.pokemon.Pokemon
import com.ardondev.bc_pokedex.domain.usecase.pokemon.GetPokemonListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getPokemonListUseCase: GetPokemonListUseCase
) : ViewModel() {

    private val _pokemonListState = MutableStateFlow<PagingData<Pokemon>>(PagingData.empty())
    val pokemonListState: MutableStateFlow<PagingData<Pokemon>> = _pokemonListState

    suspend fun getPokemonList() {
        getPokemonListUseCase(0, 10)
            .distinctUntilChanged()
            .cachedIn(viewModelScope)
            .collect {
                _pokemonListState.value = it
            }
    }

    init {
        viewModelScope.launch { getPokemonList() }
    }

}