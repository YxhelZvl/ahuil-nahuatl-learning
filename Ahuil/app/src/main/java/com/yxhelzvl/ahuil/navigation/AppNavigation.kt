package com.yxhelzvl.ahuil.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseUser
import com.yxhelzvl.ahuil.core.data.PalabrasRepository
import com.yxhelzvl.ahuil.game.ahorcado.ui.AhorcadoScreen
import com.yxhelzvl.ahuil.game.ahorcado.ui.AhorcadoViewModel
import com.yxhelzvl.ahuil.game.memorama.ui.MemoramaScreen
import com.yxhelzvl.ahuil.game.memorama.ui.MemoramaViewModel
import com.yxhelzvl.ahuil.home.ui.HomeScreen
import com.yxhelzvl.ahuil.home.ui.HomeViewModel
import com.yxhelzvl.ahuil.login.login.data.AuthManager
import com.yxhelzvl.ahuil.login.login.ui.LoginScreen
import com.yxhelzvl.ahuil.login.login.ui.LoginViewModel
import com.yxhelzvl.ahuil.login.register.ui.RegisterScreen
import com.yxhelzvl.ahuil.login.register.ui.RegisterViewModel
import com.yxhelzvl.ahuil.palabras.ui.PalabrasScreen
import com.yxhelzvl.ahuil.palabras.ui.PalabrasViewModel
import com.yxhelzvl.ahuil.user.ui.UsuarioInfScreen
import com.yxhelzvl.ahuil.user.ui.UsuarioInfViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val context = LocalContext.current.applicationContext
    val authManager: AuthManager = AuthManager(context)
    val user:FirebaseUser? = authManager.getCurrentUser()
    NavHost(
        navController = navController,
        startDestination = if(user == null) AppScreens.LoginScreen.route else AppScreens.HomeScreen.route
        //startDestination = AppScreens.RegisterScreen.route
    ){
        composable(route = AppScreens.LoginScreen.route){
            LoginScreen(navController,loginViewModel = LoginViewModel(authManager = authManager))
        }
        composable(route = AppScreens.RegisterScreen.route){
            RegisterScreen(navController, registerViewModel = RegisterViewModel(authManager = authManager))
        }
        composable(route = AppScreens.HomeScreen.route){
            HomeScreen(navController, homeViewModel = HomeViewModel(PalabrasRepository()))
        }
        composable(route = AppScreens.AhorcadoScreen.route){
            AhorcadoScreen(navController, ahorcadoViewModel = AhorcadoViewModel())
        }
        composable(route = AppScreens.MemoramaScreen.route){
            MemoramaScreen(navController, memoramaViewModel = MemoramaViewModel())
        }
        composable(route = AppScreens.UsuarioInfScreen.route){
            UsuarioInfScreen(navController, usuarioInfViewModel = UsuarioInfViewModel(authManager = authManager))
        }
        composable(route = AppScreens.PalabrasScreen.route){
            PalabrasScreen(navController, palabrasViewModel = PalabrasViewModel(authManager = authManager))
        }

        /*composable(route = AppScreens.HomeScreen.route + "/{text}", arguments = listOf(navArgument(name="text"){
            type = NavType.StringType
        })){
            HomeScreen(navController,it.arguments?.getString("text"))
        }*/
    }
}