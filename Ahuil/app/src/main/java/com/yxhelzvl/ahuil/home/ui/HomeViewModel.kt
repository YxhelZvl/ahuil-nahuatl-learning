package com.yxhelzvl.ahuil.home.ui

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yxhelzvl.ahuil.core.data.PalabrasObject
import com.yxhelzvl.ahuil.core.data.PalabrasRepository
import kotlinx.coroutines.launch

class HomeViewModel(private val palabrasRepository: PalabrasRepository):ViewModel() {
    var state by mutableStateOf(HomeState())
        private set
    val palabrasObject = PalabrasObject



    fun setPalabrasAObject(){
        viewModelScope.launch {
            val result = palabrasRepository.getPalabras()
            palabrasObject.palabras = result
            Log.d("TAG", "setPalabrasAObject: ${palabrasObject.palabras} ")
        }

    }
    fun setPalabrasEspAObject(){
        viewModelScope.launch {
            val result = palabrasRepository.getPalabrasEsp()
            palabrasObject.palabrasEsp = result
            Log.d("TAG", "setPalabrasAObject: ${palabrasObject.palabrasEsp} ")
        }

    }
    fun setPalabrasUidAObject(){
        viewModelScope.launch {
            val result = palabrasRepository.getPalabrasUid()
            palabrasObject.email = result
            Log.d("TAG", "setPalabrasAObject: ${palabrasObject.email} ")
        }

    }

    /*listaJuegos = listOf(
    Triple( "El ahorcado" , painterResource(id = R.drawable.juego_ahorcado) , AppScreens.AhorcadoScreen.route),
    Triple("Memorama", painterResource(id = R.drawable.juego_memorama), AppScreens.LoginScreen.route),
    )*/
}