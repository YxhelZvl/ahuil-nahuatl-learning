package com.yxhelzvl.ahuil.home.ui

import androidx.compose.ui.graphics.painter.Painter
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import com.yxhelzvl.ahuil.R
import com.yxhelzvl.ahuil.navigation.AppScreens

data class HomeState(
    val listaJuegos: List<Triple<String, Int, String>> = listOf(
        Triple(
            "El ahorcado",
            R.drawable.juego_ahorcado,
            AppScreens.AhorcadoScreen.route
        ),
        Triple(
            "Memorama",
            R.drawable.juego_memorama,
            AppScreens.MemoramaScreen.route
        ),
    )
)

