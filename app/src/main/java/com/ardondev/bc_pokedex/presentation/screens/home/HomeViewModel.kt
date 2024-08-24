package com.ardondev.bc_pokedex.presentation.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toLowerCase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.ardondev.bc_pokedex.domain.model.pokemon.Pokemon
import com.ardondev.bc_pokedex.domain.usecase.pokemon.GetPokemonListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPokemonListUseCase: GetPokemonListUseCase,
) : ViewModel() {

    /** Search query **/

    var query by mutableStateOf("")
        private set

    var searchActive by mutableStateOf(false)
        private set

    fun setQueryValue(value: String) {
        query = value
        searchActive = value.isNotEmpty()
    }

    /** Pokemon list **/

    private val _pokemonListState = MutableStateFlow<PagingData<Pokemon>>(PagingData.empty())
    val pokemonListState: MutableStateFlow<PagingData<Pokemon>> = _pokemonListState

    fun getPokemonList() {
        viewModelScope.launch {
            getPokemonListUseCase(0, 10)
                .distinctUntilChanged()
                .cachedIn(viewModelScope)
                .collect { data ->
                    if (query.isEmpty()) {
                        _pokemonListState.value = data
                    } else {
                        _pokemonListState.value = getFilteredPokemon(data)
                    }
                }
        }
    }

    private fun getFilteredPokemon(pokemonData: PagingData<Pokemon>): PagingData<Pokemon> {
        return pokemonData.filter {
            val name = it.name?.toLowerCase(Locale.current) ?: ""
            (name.contains(query.toLowerCase(Locale.current)) || it.id.toString() == query)
        }
    }

    init {
        getPokemonList()
    }

}