package com.yxhelzvl.ahuil.login.login.data

import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import com.yxhelzvl.ahuil.R

sealed class AuthRes<out T>{
    data class Success<T> (val data:T): AuthRes<T>()
    data class Error (val erroMsg:String): AuthRes<Nothing>()
}


class AuthManager(private val context: Context) {

    val auth: FirebaseAuth by lazy { Firebase.auth }
    private val signInClient = Identity.getSignInClient(context)
    suspend fun signInAnonymously(): AuthRes<FirebaseUser> {
        return try {
            val result = auth.signInAnonymously().await()
            AuthRes.Success(result.user ?: throw Exception("Error al iniciar sesion"))
        }catch (e:Exception){
            AuthRes.Error(e.message ?: "Error al iniciar sesion")
        }
    }
    suspend fun createUserWithEmailAndPassword(email:String,password:String):AuthRes<FirebaseUser?>{
        return try {
            val authResult = auth.createUserWithEmailAndPassword(email,password).await()
            AuthRes.Success(authResult.user)
        } catch (e: Exception){
            AuthRes.Error(e.message ?: "Error al crear usuario")
        }
    }
    suspend fun signInUserWithEmailAndPassword(email:String,password:String):AuthRes<FirebaseUser?>{
        return try {
            val authResult = auth.signInWithEmailAndPassword(email,password).await()
            AuthRes.Success(authResult.user)
        } catch (e: Exception){
            AuthRes.Error(e.message ?: "Error al iniciar sesión")
        }
    }

    fun signOut(){
        //cerrar sesion de firebase
        auth.signOut()
        //cerrar
        signInClient.signOut()


    }
    fun getCurrentUser(): FirebaseUser?{
        return auth.currentUser
    }

    fun handleSingInResult(task: Task<GoogleSignInAccount>): AuthRes<GoogleSignInAccount> {
        return try {
            val account = task.getResult(ApiException::class.java)
            AuthRes.Success(account)
        }catch (e:Exception){
            AuthRes.Error(e.message ?: "Google sign-in failed.")
        }
    }

    private val googleSignInClient:GoogleSignInClient by lazy {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        GoogleSignIn.getClient(context,gso)
    }

    suspend fun signInWithGoogleCredential(credential: AuthCredential):AuthRes<FirebaseUser>?{
        return try {
            val firebaseUser = auth.signInWithCredential(credential).await()
            firebaseUser.user?.let {
                AuthRes.Success(it)
            } ?: throw Exception("Sign in with google failed")
        }catch (e: Exception){

            AuthRes.Error(e.message ?: "Sign in with google failed")
        }
    }
    fun signInWithGoogle(googleSignInLauncher: ActivityResultLauncher<Intent>){
        val singInIntent = googleSignInClient.signInIntent
        googleSignInLauncher.launch(singInIntent)
    }




}