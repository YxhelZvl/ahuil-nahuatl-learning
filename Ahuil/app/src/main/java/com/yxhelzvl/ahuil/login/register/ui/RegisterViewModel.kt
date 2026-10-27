package com.yxhelzvl.ahuil.login.register.ui

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.yxhelzvl.ahuil.login.login.data.AuthManager
import com.yxhelzvl.ahuil.login.login.data.AuthRes


class RegisterViewModel(val authManager: AuthManager): ViewModel(){
    var state by mutableStateOf(RegisterState())
        private set

    suspend fun signUp(email: String,password: String, auth: AuthManager,navController: NavController,context: Context ){
        if (state.email.isNotEmpty() && state.contrasena.isNotEmpty()){
            when(val result = auth.createUserWithEmailAndPassword(email, password)){
                is AuthRes.Success -> {
                    Toast.makeText(context, "Resgistro Existoso", Toast.LENGTH_SHORT).show()
                    navController.popBackStack()
                }
                is AuthRes.Error -> {
                    Toast.makeText(context, "Error al Resgistrarse ${result.erroMsg}", Toast.LENGTH_SHORT).show()
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
}