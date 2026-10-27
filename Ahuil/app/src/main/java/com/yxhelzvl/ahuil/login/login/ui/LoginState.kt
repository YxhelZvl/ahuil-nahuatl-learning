package com.yxhelzvl.ahuil.login.login.ui

data class LoginState(
    val email:String = "",
    val contrasena:String = "",
    val mostrarPwd:Boolean = false,
    val estaCargando:Boolean = false,
    val msgError:String = ""
)