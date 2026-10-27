package com.yxhelzvl.ahuil.login.register.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.yxhelzvl.ahuil.R
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(navController: NavController,registerViewModel: RegisterViewModel) {
    val state = registerViewModel.state
    val scope = rememberCoroutineScope()
    val context = LocalContext.current.applicationContext
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.logologin),
            contentDescription = "Logo Login",
            modifier = Modifier
                .size(200.dp, 250.dp)
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = state.email,
            onValueChange = {email -> registerViewModel.onChangeEmail(email)},
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 5.dp, end = 5.dp),
            label = { Text(text = "Introduce Email") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = state.contrasena,
            onValueChange = {registerViewModel.onChangePwd(it)},
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 5.dp, end = 5.dp),
            visualTransformation = if(state.mostrarPwd)
            {
                VisualTransformation.None
            }else
            {
                PasswordVisualTransformation()
            },
            label = { Text(text = "Introduce Contraseña") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                IconButton(onClick = { registerViewModel.onClickMostrarPwd()}) {
                    Icon( // Update icon based on state.mostrarPwd
                        imageVector = if (state.mostrarPwd) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                        contentDescription = if (state.mostrarPwd) "Ocultar contraseña" else "Mostrar contraseña"
                    )
                }
            }
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(

        ) {
            Button(modifier = Modifier.padding(5.dp),onClick = { navController.popBackStack() }) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Regresar")
                Text(text = "Regresar")
            }
            Button(modifier = Modifier
                                    .padding(5.dp),
                    onClick = {
                        scope.launch {
                            registerViewModel.signUp(email = state.email, password = state.contrasena, auth = registerViewModel.authManager, navController = navController, context = context)
                        }
                    }) {
                Text(text = "Registrarse")
            }
        }


    }
}