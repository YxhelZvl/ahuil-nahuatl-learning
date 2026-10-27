package com.yxhelzvl.ahuil.navigation

sealed class AppScreens(val route:String){
    object LoginScreen: AppScreens("login_screen")
    object RegisterScreen: AppScreens("register_screen")
    object HomeScreen: AppScreens("home_screen")
    object UsuarioInfScreen: AppScreens("usuarioinf_screen")
    object AhorcadoScreen: AppScreens("ahorcado_screen")
    object MemoramaScreen: AppScreens("memorama_screen")
    object PalabrasScreen: AppScreens("palabras_screen")
}