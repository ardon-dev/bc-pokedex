package com.ardondev.bc_pokedex.presentation.util

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle

fun getSprite(id: Int): String {
    return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/home/$id.png"
}

fun getColorByType(id: Int, soft: Boolean = false): Color {
    return when (id) {
        TYPE_NORMAL -> if (soft) Color(0xFFfaf8e6) else Color(0xFFe6dc83)
        TYPE_FIGHTING -> if (soft) Color(0xFFf6d3d1) else Color(0xFFC22E28)
        TYPE_FLYING -> if (soft) Color(0xFFeee9fd) else Color(0xFFA98FF3)
        TYPE_POISON -> if (soft) Color(0xFFefd6ef) else Color(0xFFA33EA1)
        TYPE_GROUND -> if (soft) Color(0xFFf9f2e0) else Color(0xFFE2BF65)
        TYPE_ROCK -> if (soft) Color(0xFFf2eed5) else Color(0xFFB6A136)
        TYPE_BUG -> if (soft) Color(0xFFf2f8cb) else Color(0xFFA6B91A)
        TYPE_GHOST -> if (soft) Color(0xFFe3dceb) else Color(0xFF735797)
        TYPE_STEEL -> if (soft) Color(0xFFf1f1f5) else Color(0xFFB7B7CE)
        TYPE_FIRE -> if (soft) Color(0xFFfce6d6) else Color(0xFFEE8130)
        TYPE_WATER -> if (soft) Color(0xFFe0e9fc) else Color(0xFF6390F0)
        TYPE_GRASS -> if (soft) Color(0xFFe4f4db) else Color(0xFF7AC74C)
        TYPE_ELECTRIC -> if (soft) Color(0xFFfdf6d5) else Color(0xFFF7D02C)
        TYPE_PSYCHIC -> if (soft) Color(0xFFfedde7) else Color(0xFFF95587)
        TYPE_ICE -> if (soft) Color(0xFFeaf7f7) else Color(0xFF96D9D6)
        TYPE_DRAGON -> if (soft) Color(0xFFe2d7fe) else Color(0xFF6F35FC)
        TYPE_DARK -> if (soft) Color(0xFFe6ddd7) else Color(0xFF705746)
        TYPE_FAIRY -> if (soft) Color(0xFFf7e7ef) else Color(0xFFD685AD)
        TYPE_STELLAR-> Color(0xFFA8A77A)
        else -> Color(0xFFA8A77A)
    }
}

fun getNameByType(id: Int): String {
    return when (id) {
        TYPE_NORMAL -> "\uD83D\uDD18 Normal"
        TYPE_FIGHTING -> "\uD83E\uDD4A Lucha"
        TYPE_FLYING -> "\uD83E\uDEBD Volador"
        TYPE_POISON -> "☠\uFE0F Veneno"
        TYPE_GROUND -> "⛰\uFE0F Tierra"
        TYPE_ROCK -> "\uD83E\uDEA8 Roca"
        TYPE_BUG -> "\uD83E\uDEB2 Bicho"
        TYPE_GHOST -> "\uD83D\uDC7B Fantasma"
        TYPE_STEEL -> "⚙\uFE0F Acero"
        TYPE_FIRE -> "\uD83D\uDD25 Fuego"
        TYPE_WATER -> "\uD83C\uDF0A Agua"
        TYPE_GRASS -> "\uD83C\uDF3F Planta"
        TYPE_ELECTRIC -> "⚡ Eléctrico"
        TYPE_PSYCHIC -> "\uD83D\uDC41\uFE0F Psíquico"
        TYPE_ICE -> "❄\uFE0F Hielo"
        TYPE_DRAGON -> "\uD83D\uDC32 Dragón"
        TYPE_DARK -> "\uD83C\uDF11 Siniestro"
        TYPE_FAIRY -> "✨ Ada"
        TYPE_STELLAR-> "Estelar"
        else -> id.toString()
    }
}

fun getWelcomeText(): AnnotatedString {
    return buildAnnotatedString {
        withStyle(SpanStyle(fontWeight = FontWeight.Normal)) {
            append("¡Hola, ")
        }
        withStyle(SpanStyle(fontWeight = FontWeight.SemiBold)) {
            append("bienvenido")
        }
        withStyle(SpanStyle(fontWeight = FontWeight.Normal)) {
            append("!")
        }
    }
}

fun formatPokemonId(id: Int): String {
    return if (id >= 1000) {
        "#$id"
    } else {
        "#${id.toString().padStart(3, '0')}"
    }
}