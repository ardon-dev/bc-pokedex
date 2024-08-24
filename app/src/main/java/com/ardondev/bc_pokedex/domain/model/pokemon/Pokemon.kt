package com.ardondev.bc_pokedex.domain.model.pokemon

data class Pokemon(
    val name: String?,
    val url: String?,
    val id: Int?,
    val weight: Int? = null,
    val height: Int? = null,
    val types: List<Type>? = null,
    val stats: List<Stat>? = null,
) {

    fun getSprite(): String {
        return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/home/$id.png"
    }

}
