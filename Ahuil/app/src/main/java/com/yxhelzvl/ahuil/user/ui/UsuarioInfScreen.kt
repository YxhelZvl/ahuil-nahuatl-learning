package com.yxhelzvl.ahuil.user.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.yxhelzvl.ahuil.R
import com.yxhelzvl.ahuil.navigation.AppScreens


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsuarioInfScreen(navController: NavController,usuarioInfViewModel: UsuarioInfViewModel) {
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
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Atras al home"
                        )
                    }
                },
                /*navigationIcon = {
                    IconButton(onClick = { if(user != null){
                        authManager.signOut()
                        navController.navigate(route = AppScreens.LoginScreen.route)
                    }
                    }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Localized description"
                        )
                    }
                },*/
                actions = {
                    IconButton(
                        onClick = { /* No hace nada*/ }) {
                        Icon(
                            imageVector = Icons.Filled.AccountCircle,
                            contentDescription = "Localized description"
                        )
                    }
                },

                )
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,

        ) {

            if(usuarioInfViewModel.user?.photoUrl != null){
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(usuarioInfViewModel.user.photoUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = "Imagen Del Perfil",
                    placeholder = painterResource(id = R.drawable.user_profile),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(200.dp)
                    )
            }else{
                Image(
                    painter = painterResource(id = R.drawable.user_profile) ,
                    contentDescription ="Imagen del usuario",
                    modifier = Modifier
                        .size(200.dp)
                        .clip(shape = CircleShape)
                )
            }
            if (!usuarioInfViewModel.user?.displayName.isNullOrEmpty()){
                Text(text = "${usuarioInfViewModel.user?.displayName}")
            }else if (!usuarioInfViewModel.user?.email.isNullOrEmpty()){
                Text(text = "${usuarioInfViewModel.user?.email}")
            }else{
                Text(text = "Anonimo")
            }
            
            IconButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp),
                onClick = {
                    usuarioInfViewModel.authManager.signOut()
                    navController.navigate(route = AppScreens.LoginScreen.route){
                        popUpTo(route = AppScreens.HomeScreen.route){
                            inclusive = true
                        }
                    }
                }) {
                Row {
                    Text(text = "Cerrar Sesion")
                    Icon(imageVector = Icons.Filled.Logout, contentDescription = "Cerrar sesión" )
                }
                
            }
        }
    }
}