package com.ardondev.bc_pokedex.presentation.util

import okhttp3.Route

sealed class Routes(
    val route: String
) {

    object HomeScreen: Routes("home")

    object DetailScreen: Routes("detail/{pokemon_id}") {
        fun createRoute(pokemonId: Int) = "detail/$pokemonId"
    }
}