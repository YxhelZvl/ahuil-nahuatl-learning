package com.yxhelzvl.ahuil.game.ahorcado.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.ui.graphics.vector.ImageVector
import com.yxhelzvl.ahuil.R


data class AhorcadoState(
    val palabraAleatoria: String = "",
    val palabraAleatoriaCifrada: List<Char> = emptyList(),
    val alfabeto: List<String> = listOf(
        "A",
        "W",
        "Ch",
        "E",
        "H",
        "I",
        "K",
        "Ku",
        "L",
        "M",
        "N",
        "O",
        "P",
        "S",
        "T",
        "Tl",
        "Tz",
        "X",
        "Y"
    ),
    val palabras: List<String> = emptyList(),
    val letrasUsadas: List<String> = emptyList(),
    val intentosRestantes: Int = 6,
    val juegoTerminado: Boolean = false,
    val empezarJuego: Boolean = false,
    val victoria: Boolean = false,
    val letrasCorrectas: List<String> = emptyList(),
    val letrasIncorrectas: List<String> = emptyList(),
    val ahorcadoImagen: Int = R.drawable.ahorcado_0
)