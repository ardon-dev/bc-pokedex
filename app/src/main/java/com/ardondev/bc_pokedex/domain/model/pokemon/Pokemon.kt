package com.ardondev.bc_pokedex.domain.model.pokemon

data class Pokemon(
    val name: String?,
    val url: String?,
    val id: String?
) {

    fun getSprite(): String {
        return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/home/$id.png"
    }

}
