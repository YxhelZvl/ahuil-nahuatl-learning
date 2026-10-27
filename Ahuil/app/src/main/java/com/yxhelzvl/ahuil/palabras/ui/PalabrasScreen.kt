package com.yxhelzvl.ahuil.palabras.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.yxhelzvl.ahuil.core.data.PalabrasObject
import com.yxhelzvl.ahuil.home.ui.HomeViewModel

var palabrasObject = PalabrasObject
@Composable
fun PalabrasScreen(navController: NavController, palabrasViewModel: PalabrasViewModel) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
    )
    {
        Cabecera()
        Divider(Modifier.fillMaxWidth())
        Contenido(palabrasuid = palabrasObject.email ,
            palabrasNah = palabrasObject.palabras,
            palabrasEsp = palabrasObject.palabrasEsp )

    }
}

@Composable
fun Cabecera() {
    Row (
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Text(text = "Usuario")
        Text(text = "Nahutl")
        Text(text = "Español")
    }
}

@Composable
fun Contenido(palabrasuid:List<String>,palabrasNah:List<String>,palabrasEsp:List<String>) {
    LazyColumn {
        for (indice in palabrasuid.indices){
            item {
                Row (
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Text(text = palabrasuid[indice])
                    Text(text = palabrasNah[indice])
                    Text(text = palabrasEsp[indice])
                }


            }
        }


    }
}