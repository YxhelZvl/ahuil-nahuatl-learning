package com.yxhelzvl.ahuil.game.ahorcado.ui

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.yxhelzvl.ahuil.R
import com.yxhelzvl.ahuil.core.data.PalabrasObject

class AhorcadoViewModel() : ViewModel() {
    var state by mutableStateOf(AhorcadoState())
        private set
    val palabrasObject = PalabrasObject

    fun setPalabras(){
        state = state.copy(
            palabras = palabrasObject.palabras
        )
    }

    fun startNewGame() {
        if (state.palabraAleatoria.isEmpty()) {
            setPalabraAleatoria()
            state = state.copy(
                empezarJuego = true,
            )
        }else if(state.juegoTerminado || state.victoria){
            setPalabraAleatoria()
            state = state.copy(
                empezarJuego = true,
                intentosRestantes = 6,
                letrasUsadas = emptyList(),
                letrasCorrectas = emptyList(),
                letrasIncorrectas = emptyList(),
                juegoTerminado = false,
                victoria = false,
                ahorcadoImagen = R.drawable.ahorcado_0
            )
        }
    }
    private fun setPalabraAleatoria() {
        val palabraAleatoria = state.palabras.random()
        state = state.copy(
            palabraAleatoria = palabraAleatoria,
            palabraAleatoriaCifrada = "_".repeat(palabraAleatoria.length).trim().toList()
        )
    }

    data class respuesta(val encontro:Boolean,val indice:List<Int>)
    fun verificarLetra(letraTeclado:String):respuesta{
        val indice:MutableList<Int> = mutableListOf()
        var encontro:Boolean = false
        val letra = letraTeclado.lowercase()
        val palabra = state.palabraAleatoria.lowercase()
        val letraTam = letra.length
        if (letraTam == 1){
            for (i in letra.indices){
                for (index in palabra.indices){
                    if (letra[i] == palabra[index]){
                        when(letra[i]){
                            'h' -> {
                                if (index>=1 && palabra[index-1] != 'c'){
                                    encontro = true
                                    indice.add(index)
                                }
                            }
                            'k' -> {
                                if (index<=palabra.length-2 && palabra[index+1] != 'u'){
                                    encontro = true
                                    indice.add(index)
                                }
                            }
                            'l' -> {
                                if (index>=1 && palabra[index-1] != 't'){
                                    encontro = true
                                    indice.add(index)
                                }
                            }
                            't' -> {
                                if (index<=palabra.length-2 && palabra[index+1] != 'l' && palabra[index+1] != 'z'){
                                    encontro = true
                                    indice.add(index)
                                }
                            }
                            else ->{
                                encontro = true
                                indice.add(index)
                            }
                        }


                    }
                }
            }
        }else if (letraTam == 2){
            for (index in palabra.indices){
                for (i in letra.indices){
                    if (palabra[index] == letra[i]){
                        if (i<letra.length-1 && index<= palabra.length-2&&palabra[index+1] == letra[i+1]){
                            encontro = true
                            indice.add(index)
                            indice.add(index+1)
                        }
                    }
                }
            }
        }
        return respuesta(encontro,indice)
    }

    fun colocarLetraPalabraCifrada(letraTeclado: String,indice: List<Int>){
        val nuevaPalabraCifrada = state.palabraAleatoriaCifrada.toMutableList()
        val letra = letraTeclado
        val letraTam = letraTeclado.length
        if (letraTam == 1){
            for (i in indice)
                for (l in letra)
                    nuevaPalabraCifrada[i] = l
        }else if (letraTam == 2){
            var contador = 0

            for (i in indice){
                if (contador == 2)
                    contador = 0
                nuevaPalabraCifrada[i] = letra[contador]
                contador +=1
            }

        }

        state = state.copy(
            palabraAleatoriaCifrada = nuevaPalabraCifrada
        )
    }
    fun actualizarTeclado(letraTeclado: String){
        val letrasUsadasActualizadas = state.letrasUsadas + letraTeclado
        state = state.copy(
            letrasUsadas = letrasUsadasActualizadas
        )
    }
    fun palabraCorrecta(letraTeclado: String){
        val letrasCorrectasActualizadas = state.letrasCorrectas + letraTeclado
        Log.d("pruba1", "palabraCorrecta:${state.palabraAleatoriaCifrada.toString().lowercase()} ")
        Log.d("pruba1", "palabraCorrecta:${state.palabraAleatoria.lowercase().toList()} ")
        Log.d("pruba1", "palabraCorrecta: ${state.palabraAleatoriaCifrada.toString().lowercase().toList().containsAll(state.palabraAleatoria.lowercase().toList()) }")
        state = state.copy(
            letrasCorrectas = letrasCorrectasActualizadas,
            victoria =
            if(state.palabraAleatoriaCifrada.toString().lowercase().toList().containsAll(state.palabraAleatoria.lowercase().toList()) ){
                !state.victoria
            }else{
                state.victoria
            }

        )
    }
    fun palabraIncorrecta(letraTeclado: String){
        val letrasIncorrectasActualizadas = state.letrasIncorrectas + letraTeclado
        var imagenAhorcado = state.ahorcadoImagen
        val actualizarIntentosRestantes = state.intentosRestantes -1
        var actualizarJuegoTerminado = state.juegoTerminado
        if (actualizarIntentosRestantes<1){
            actualizarJuegoTerminado = !actualizarJuegoTerminado
        }
        when(actualizarIntentosRestantes){
            6 -> { imagenAhorcado = R.drawable.ahorcado_0}
            5 -> { imagenAhorcado = R.drawable.ahorcado_1}
            4 -> { imagenAhorcado = R.drawable.ahorcado_2}
            3 -> { imagenAhorcado = R.drawable.ahorcado_3}
            2 -> { imagenAhorcado = R.drawable.ahorcado_4}
            1 -> { imagenAhorcado = R.drawable.ahorcado_5}
            0 -> { imagenAhorcado = R.drawable.ahorcado_6}
        }
        state = state.copy(
            letrasIncorrectas = letrasIncorrectasActualizadas,
            intentosRestantes = actualizarIntentosRestantes,
            ahorcadoImagen = imagenAhorcado,
            juegoTerminado = actualizarJuegoTerminado,
            //empezarJuego = if (actualizarJuegoTerminado) !state.empezarJuego else state.empezarJuego
        )
    }





}