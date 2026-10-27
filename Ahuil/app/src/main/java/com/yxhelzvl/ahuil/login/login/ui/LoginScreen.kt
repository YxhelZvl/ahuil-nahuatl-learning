package com.yxhelzvl.ahuil.login.login.ui

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import com.yxhelzvl.ahuil.navigation.AppScreens
import com.yxhelzvl.ahuil.R
import com.yxhelzvl.ahuil.login.login.data.AuthRes
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(navController: NavController,loginViewModel: LoginViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center

    ) {
        Encabezado()
        Formulario(navController,loginViewModel)
        Pieformulario(navController,loginViewModel)
    }
}

@Composable
fun Encabezado() {
    Image(
        painter = painterResource(id = R.drawable.logologin),
        contentDescription = "Logo Login",
        modifier = Modifier
            .size(200.dp, 250.dp)
            .fillMaxWidth()
    )
}

@Composable
fun Formulario(
    navController: NavController,
    loginViewModel: LoginViewModel
) {
    val state = loginViewModel.state
    val scope = rememberCoroutineScope()
    val context = LocalContext.current.applicationContext
    OutlinedTextField(
        value = state.email,
        onValueChange = {loginViewModel.onChangeEmail(it)},
        modifier = Modifier.fillMaxWidth(),
        label = { Text(text = "Email") }
    )
    OutlinedTextField(
        value = state.contrasena,
        onValueChange = {loginViewModel.onChangePwd(it)},
        visualTransformation = if(state.mostrarPwd)
        {
            VisualTransformation.None
        }else
        {
            PasswordVisualTransformation()
        },
        modifier = Modifier.fillMaxWidth(),
        label = { Text(text = "Contraseña") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            IconButton(onClick = { loginViewModel.onClickMostrarPwd()}) {
                Icon( // Update icon based on state.mostrarPwd
                    imageVector = if (state.mostrarPwd) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                    contentDescription = if (state.mostrarPwd) "Ocultar contraseña" else "Mostrar contraseña"
                )
            }
        }
    )
    Row(modifier = Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.End) {
        Button(onClick = {
            scope.launch {
                loginViewModel.emailAndPasswordSignIn(email = state.email, password = state.contrasena, auth = loginViewModel.authManager, navController = navController, context = context)
            }
        }) {
            Text(text = "Iniciar Sesion")
        }
    }

}
//navController.popBackStack()
@Composable
fun Pieformulario(navController: NavController,loginViewModel: LoginViewModel
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current.applicationContext
    val googleSingInLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) {result->
        when(val account = loginViewModel.authManager.handleSingInResult(GoogleSignIn.getSignedInAccountFromIntent(result.data))){
            is AuthRes.Success -> {
                val credential = GoogleAuthProvider.getCredential(account.data.idToken,null)
                scope.launch {
                    val fireUser = loginViewModel.authManager.signInWithGoogleCredential(credential)
                    if(fireUser != null){
                        Toast.makeText(context, "Bienvenidx", Toast.LENGTH_SHORT).show()
                        navController.navigate(route = AppScreens.HomeScreen.route){
                            popUpTo(route = AppScreens.LoginScreen.route){
                                inclusive = true
                            }
                        }
                    }
                }
            }
            is AuthRes.Error -> {
                Toast.makeText(context,"Error: ${account.erroMsg}", Toast.LENGTH_SHORT).show()
            }
            else -> {
                Toast.makeText(context,"Error desconocido", Toast.LENGTH_SHORT).show()
            }
        }

    }
    /*val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
        try {
            val account = task.getResult(ApiException::class.java)
            val credential = GoogleAuthProvider.getCredential(account.idToken,null)
            loginViewModel.signInWithGoogleCredential(credential, context = context){
                navController.navigate(route = AppScreens.HomeScreen.route)
            }
        }catch (e :Exception){
            Toast.makeText(context, "Error loguear con google:${e.message}", Toast.LENGTH_SHORT).show()
        }
    }*/
    Text(
        text = "Ingresar Como Invitado", modifier = Modifier
            .clickable {
                scope.launch() {
                    loginViewModel.incognitoSignIn(navController, loginViewModel.authManager )
                }
            },
        color = Color(
            0xFF4D4D4D
        )
    )
    Spacer(modifier = Modifier.height(16.dp))
    Row {
        Text(text ="No tienes Cuenta? " )
        Text(text = "Registrate", modifier = Modifier
            .clickable {
                navController.navigate(route = AppScreens.RegisterScreen.route)
            },
            color = Color(0xFF006BA0))
    }

    Text(text = "Ingresar Con: ")
    Row {
        Image(
            painter = painterResource(id = R.drawable.googlelogin),
            contentDescription = "Login google",
            modifier = Modifier
                .size(40.dp, 40.dp)
                .clip(shape = CircleShape)
                .clickable(
                    onClick = {
                        loginViewModel.authManager.signInWithGoogle(googleSingInLauncher)
                        /*Toast
                            .makeText(context, "LOGIN GOOGLE", Toast.LENGTH_SHORT)
                            .show()
                        val opciones = GoogleSignInOptions.Builder(
                            GoogleSignInOptions.DEFAULT_SIGN_IN
                        ).requestIdToken("677013560695-3fmtpo05bpiudmgug39jdi2iim5u67ig.apps.googleusercontent.com")
                            .requestEmail()
                            .build()
                        val googleSignInClient = GoogleSignIn.getClient(context,opciones)
                        launcher.launch(googleSignInClient.signInIntent)*/
                    }
                )
        )
    }



}
