package com.yxhelzvl.ahuil.home.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.yxhelzvl.ahuil.core.data.PalabrasObject
import com.yxhelzvl.ahuil.navigation.AppScreens
import com.yxhelzvl.ahuil.ui.theme.AhuilTheme
import kotlin.math.log

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController,homeViewModel: HomeViewModel) {
    val state = homeViewModel.state
    val palabrasObject = PalabrasObject

    LaunchedEffect(Unit) {
        if (palabrasObject.palabras.isEmpty()){
            homeViewModel.setPalabrasAObject()
        }
        if(palabrasObject.palabrasEsp.isEmpty())
            homeViewModel.setPalabrasEspAObject()
        if(palabrasObject.email.isEmpty())
            homeViewModel.setPalabrasUidAObject()
    }
    Log.d("TAG", "HomeScreen: ${palabrasObject.palabras}")
    AhuilTheme {
       Scaffold (


           topBar = {
               CenterAlignedTopAppBar(
                   colors = TopAppBarDefaults.topAppBarColors(
                       containerColor = MaterialTheme.colorScheme.primaryContainer,
                       titleContentColor = MaterialTheme.colorScheme.primary,
                   ),
                   title = {
                       Text(
                           "",
                           maxLines = 1,
                           overflow = TextOverflow.Ellipsis
                       )
                   },
                   actions = {
                       IconButton(onClick = { navController.navigate(AppScreens.UsuarioInfScreen.route) }) {
                           Icon(
                               imageVector = Icons.Filled.AccountCircle,
                               contentDescription = "Localized description"
                           )
                       }
                   },

               )
           },
        ){ padding ->
            Column(
                modifier = Modifier.padding(padding)
            ) {
                Text(text = "Juegos Disponibles: ")
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.55f)){
                    LazyVerticalGrid( columns = GridCells.Fixed(3) ) {
                        items(state.listaJuegos.size) { index ->
                            val (juegoTitulo,imagen,route) = state.listaJuegos[index]
                            cardJuegos(juegoTitulo = juegoTitulo, imagen = imagen, route = route, navController = navController)
                        }
                    }

                }
                Spacer(modifier = Modifier.height(5.dp))
                Divider(Modifier.fillMaxWidth())
                Text(text = "Otras opciones: ")
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ){
                    OutlinedButton(onClick = { navController.navigate(AppScreens.PalabrasScreen.route) }) {
                        Text(text = "Listas de palabras")
                    }
                    OutlinedButton(onClick = { /*TODO*/ }) {
                        Text(text = "Agregar palabra")
                    }
                }

             }
        }
    }
}



@Composable
fun cardJuegos(juegoTitulo:String, imagen:Int,route:String,navController: NavController) {
    val context = LocalContext.current.applicationContext
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(5.dp),
        modifier = Modifier
            .size(120.dp, 170.dp)
            .padding(4.dp)
            .clickable {
                navController.navigate(route = route)
            }

    ) {
        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            Image(
                painter = painterResource(id = imagen),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp),
                contentScale = ContentScale.Fit
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = juegoTitulo,
                color = Color.Black,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}