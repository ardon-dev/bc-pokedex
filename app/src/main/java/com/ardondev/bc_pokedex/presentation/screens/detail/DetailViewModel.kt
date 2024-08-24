package com.ardondev.bc_pokedex.presentation.screens.detail

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ardondev.bc_pokedex.domain.model.pokemon.Pokemon
import com.ardondev.bc_pokedex.domain.usecase.pokemon.GetPokemonDetailUseCase
import com.ardondev.bc_pokedex.domain.usecase.pokemon.GetTextEntryUseCase
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
    private val getTextEntryUseCase: GetTextEntryUseCase
) : ViewModel() {

    val pokemonName = savedStateHandle["pokemon_name"] ?: ""
    val pokemonId = savedStateHandle["pokemon_id"] ?: -1

    /** Detail ui state **/

    private val _uiState = MutableStateFlow<UiState<Pokemon>>(UiState.Loading)
    val uiState: StateFlow<UiState<Pokemon>> = _uiState

    /** Get pokemon detail **/

    fun getPokemonDetail() {
        viewModelScope.launch {
            val result = getPokemonDetailUseCase(pokemonId)
            if (result.isSuccess) {
                getPokemonTextEntry()
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

    /** Get pokemon text entry **/

    private val _textEntry = MutableStateFlow("")
    val textEntry: StateFlow<String> = _textEntry

    fun getPokemonTextEntry() {
        viewModelScope.launch {
            val result = getTextEntryUseCase(pokemonId)
            if (result.isSuccess) {
                result.getOrNull()?.let { data ->
                    _textEntry.value = data
                }
            } else {
                Log.d("DetailViewModel", result.exceptionOrNull().toString())
            }
        }
    }

    init {
        getPokemonDetail()
    }

}