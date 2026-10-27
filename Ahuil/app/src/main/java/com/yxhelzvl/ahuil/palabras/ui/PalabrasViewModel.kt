package com.yxhelzvl.ahuil.palabras.ui

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.yxhelzvl.ahuil.core.data.PalabrasObject
import com.yxhelzvl.ahuil.login.login.data.AuthManager

class PalabrasViewModel(val authManager: AuthManager):ViewModel() {
    val user = authManager.getCurrentUser()
    var palabrasObject = PalabrasObject
    val auth: FirebaseAuth by lazy { Firebase.auth }
    fun setalgo(){
        for (indice in palabrasObject.email.indices){
            
        }
        Log.d("TAG", "setalgo: ")
    }
}