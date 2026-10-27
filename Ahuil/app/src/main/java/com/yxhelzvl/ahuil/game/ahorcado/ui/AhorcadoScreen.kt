package com.yxhelzvl.ahuil.game.ahorcado.ui


import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.yxhelzvl.ahuil.R
import com.yxhelzvl.ahuil.core.data.PalabrasObject
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@SuppressLint("MutableCollectionMutableState", "RememberReturnType")
@Composable
fun AhorcadoScreen(navController: NavController,ahorcadoViewModel: AhorcadoViewModel) {
    Log.d("AhorcadoScreen", "Composable recomposed")
    val context = LocalContext.current.applicationContext
    val palabrasObject = PalabrasObject
    val state = ahorcadoViewModel.state
    if (state.palabras.isEmpty()){
        ahorcadoViewModel.setPalabras()
    }

    /*val configuration = LocalConfiguration.current

    val orientation = when (configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> "horizontal" // Horizontal
        Configuration.ORIENTATION_PORTRAIT -> "vertical"   // Vertical
        else -> "Undefined"
    }*/
    if (state.empezarJuego){

        Column(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .systemBarsPadding(),
                horizontalArrangement = Arrangement.SpaceBetween

            ){
                Text(text = "Intentos Restantes: ${state.intentosRestantes}")
                Text(text = "Score: 1")
            }
            Image(
                modifier = Modifier
                    .size(250.dp)
                    .padding(top = 100.dp),
                alignment = Alignment.TopCenter,
                painter = painterResource(id = state.ahorcadoImagen),
                contentDescription = "img_ahorcado"
            )



            Text(text = state.palabraAleatoria )
            Text(
                text = state.palabraAleatoriaCifrada.joinToString(" "),
                fontSize = 32.sp,
                textAlign = TextAlign.Center
            )
            if(state.victoria){
                Text(
                    text = "Ganaste",
                    color = Color.Green
                )
                Button(onClick = { ahorcadoViewModel.startNewGame() }) {
                    Text(text = "Volver a jugar")
                }
            }else if(state.juegoTerminado){
                Text(
                    text = "Perdiste",
                    color = Color.Red
                )
                Button(onClick = { ahorcadoViewModel.startNewGame() }) {
                    Text(text = "Volver a jugar")
                }
            }


        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding(),
            contentAlignment = Alignment.BottomCenter
        ){
            LazyVerticalGrid(
                columns = GridCells.Fixed(4)
            ) {
                state.alfabeto.forEach{letra->
                    item {

                        OutlinedButton(
                            onClick = {
                                ahorcadoViewModel.actualizarTeclado(letraTeclado = letra)
                                val res = ahorcadoViewModel.verificarLetra(letraTeclado = letra)
                                //Toast.makeText(context, "${res.encontro},${res.indice}", Toast.LENGTH_SHORT).show()
                                if(res.encontro){
                                    ahorcadoViewModel.colocarLetraPalabraCifrada(letra,res.indice)
                                    ahorcadoViewModel.palabraCorrecta(letra)
                                }else{
                                    ahorcadoViewModel.palabraIncorrecta(letra)
                                }
                            },
                            enabled = if(state.juegoTerminado || state.victoria)false else !state.letrasUsadas.contains(letra),
                            colors = ButtonDefaults.outlinedButtonColors(

                                containerColor = colorResource(id = R.color.colorTeclado),
                                disabledContainerColor =if(state.letrasCorrectas.contains(letra) && !state.letrasIncorrectas.contains(letra)){
                                    colorResource(id = R.color.palabraCorrecta)
                                }else if(!state.letrasCorrectas.contains(letra) && state.letrasIncorrectas.contains(letra)){
                                    colorResource(id = R.color.palabraIncorrecta)
                                }else if(state.juegoTerminado || state.victoria){
                                    colorResource(id = R.color.colorTecladoDesactivado)

                                }else{
                                    Color.Transparent
                                }

                            ),

                        ) {
                            Text(
                                text = letra
                            )
                            if (state.letrasUsadas.contains(letra)){
                                if (state.letrasCorrectas.contains(letra)){
                                    Icon(
                                        imageVector = Icons.Filled.Check,
                                        contentDescription = "Correcta Palabra"
                                    )
                                }else if(state.letrasIncorrectas.contains(letra)){
                                    Icon(imageVector = Icons.Filled.Clear, contentDescription = "Error Palabra")
                                }

                            }


                        }
                    }

                }
            }
        }
    }else{

        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center

        ) {

            Button(onClick = {
                ahorcadoViewModel.startNewGame()
                 }) {
                Text(text = "Iniciar juego")
            }
        }
        Log.d("TAG", "${state.palabras}")
    }

}



