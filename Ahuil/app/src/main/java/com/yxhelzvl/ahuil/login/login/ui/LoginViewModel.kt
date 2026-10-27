package com.yxhelzvl.ahuil.login.login.ui

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.google.firebase.auth.AuthCredential
import com.yxhelzvl.ahuil.login.login.data.AuthManager
import com.yxhelzvl.ahuil.login.login.data.AuthRes
import com.yxhelzvl.ahuil.navigation.AppScreens
import kotlinx.coroutines.launch

class LoginViewModel(val authManager: AuthManager): ViewModel(){
    var state by mutableStateOf(LoginState())
        private set
    suspend fun incognitoSignIn(navController: NavController,authManager: AuthManager) {
        when(val result = authManager.signInAnonymously()){
            is AuthRes.Success ->{
                navController.navigate(route = AppScreens.HomeScreen.route){
                    popUpTo(AppScreens.LoginScreen.route){
                        inclusive = true
                    }
                }
            }
            is AuthRes.Error -> {
                Log.i("Invitado", "incognitoSignIn: ${result.erroMsg}")
            }
        }
    }

    suspend fun emailAndPasswordSignIn(email: String,password: String, auth: AuthManager,navController: NavController,context: Context){
        if (state.email.isNotEmpty() && state.contrasena.isNotEmpty()){
            when(val result = auth.signInUserWithEmailAndPassword(email, password)){
                is AuthRes.Success -> {
                    navController.navigate(route = AppScreens.HomeScreen.route){
                        popUpTo(route = AppScreens.LoginScreen.route){
                            inclusive = true
                        }
                    }
                }
                is AuthRes.Error -> {
                    Toast.makeText(context, "Error SignIn ${result.erroMsg}", Toast.LENGTH_SHORT).show()
                }
            }
        }else{
            Toast.makeText(context, "Existen campos vacios", Toast.LENGTH_SHORT).show()
        }
    }

    fun onClickMostrarPwd(){
        state = state.copy(
            mostrarPwd = !state.mostrarPwd
        )
    }
    fun onChangeEmail(email:String){
        state = state.copy(
            email = email
        )
    }

    fun onChangePwd(contrasena:String){
        state = state.copy(
            contrasena = contrasena
        )
    }


    fun signInWithGoogleCredential(credential: AuthCredential,context: Context, home:() -> Unit)
            = viewModelScope.launch {
        try {
            authManager.auth.signInWithCredential(credential)
                .addOnCompleteListener { task ->
                    Toast.makeText(context, "Logueado con exito", Toast.LENGTH_SHORT).show()
                    home()
                }
                .addOnFailureListener {
                    Toast.makeText(context, "Error: ${it.message}", Toast.LENGTH_SHORT)
                        .show()
                }
        }catch (e : Exception){
            Toast.makeText(context, "Exception: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

}