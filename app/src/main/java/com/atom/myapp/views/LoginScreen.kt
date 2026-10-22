package com.atom.myapp.views

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.atom.myapp.R

@Composable
fun LoginScreen(
    onNavigateToRegister: () -> Unit,
    onLoginClick: (String, String) -> Boolean // Devuelve true si el login es exitoso, false si falla
) {
    var nombre by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) } // Controla si mostramos el error

    Column(
        modifier = Modifier.fillMaxSize().padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.blacktriforce),
            contentDescription = "Logo Trifuerza",
            tint = Color.Unspecified,
            modifier = Modifier.size(150.dp)
        )

        Spacer(modifier = Modifier.height(40.dp))

        OutlinedTextField(
            value = nombre,
            onValueChange = {
                nombre = it
                showError = false // Oculta el error al volver a escribir
            },
            label = { Text("NOMBRE:") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                showError = false
            },
            label = { Text("PASSWORD:") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        // Mensaje de error condicional
        if (showError) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Datos incorrectos o usuario no registrado",
                color = Color.Red,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        // NUEVO BOTÓN PARA INGRESAR
        Button(
            onClick = {
                // Ejecutamos la validación que viene desde AppNavigation
                val loginExitoso = onLoginClick(nombre, password)
                if (!loginExitoso) {
                    showError = true
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0070C0)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("INGRESAR", color = Color.White)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onNavigateToRegister,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF002D72)),
            shape = MaterialTheme.shapes.extraSmall,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("¿NO TE HAS REGISTRADO? REGISTRATE AQUI", color = Color.White)
        }
    }
}