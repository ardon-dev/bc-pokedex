package com.ardondev.bc_pokedex.presentation.util

sealed class Routes(
    val route: String
) {

    object HomeScreen: Routes("home")

}