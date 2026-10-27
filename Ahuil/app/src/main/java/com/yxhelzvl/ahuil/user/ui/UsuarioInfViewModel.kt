package com.yxhelzvl.ahuil.user.ui

import androidx.lifecycle.ViewModel
import com.yxhelzvl.ahuil.login.login.data.AuthManager

class UsuarioInfViewModel(val authManager: AuthManager): ViewModel() {
    val user = authManager.getCurrentUser()

}