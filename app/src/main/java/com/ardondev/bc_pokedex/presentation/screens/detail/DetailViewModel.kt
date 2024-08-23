package com.ardondev.bc_pokedex.presentation.screens.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ardondev.bc_pokedex.domain.model.pokemon.Pokemon
import com.ardondev.bc_pokedex.domain.usecase.pokemon.GetPokemonDetailUseCase
import com.ardondev.bc_pokedex.presentation.util.UiState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getPokemonDetailUseCase: GetPokemonDetailUseCase,
) : ViewModel() {

    val pokemonId = savedStateHandle["pokemon_id"] ?: -1

    /** Detail ui state **/

    private val _uiState = MutableStateFlow<UiState<Pokemon>>(UiState.Loading)
    val uiState: StateFlow<UiState<Pokemon>> = _uiState

    /** Get pokemon detail **/

    fun getPokemonDetail() {
        viewModelScope.launch {
            val result = getPokemonDetailUseCase(pokemonId)
            if (result.isSuccess) {
                result.getOrNull()?.let { data ->
                    _uiState.value = UiState.Success(data)
                }
            } else {
                result.exceptionOrNull()?.let { e ->
                    _uiState.value = UiState.Error(e)
                }
            }
        }
    }

    init {
        getPokemonDetail()
    }

}