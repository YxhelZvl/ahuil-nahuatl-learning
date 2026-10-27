package com.yxhelzvl.ahuil

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.yxhelzvl.ahuil.game.ahorcado.ui.AhorcadoScreen
import com.yxhelzvl.ahuil.game.ahorcado.ui.AhorcadoViewModel
import com.yxhelzvl.ahuil.navigation.AppNavigation
import com.yxhelzvl.ahuil.ui.theme.AhuilTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AhuilTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    AppNavigation()
                }

            }
        }
    }
}

