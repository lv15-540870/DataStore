package com.atom.myapp.views

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun RegistroScreen(onRegistroGuardado: (String, String, String) -> Unit) {
    var nombre by remember { mutableStateOf("") }
    var usuario by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().padding(32.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("NOMBRE:") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = usuario,
            onValueChange = { usuario = it },
            label = { Text("USUARIO:") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("PASSWORD:") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = { onRegistroGuardado(nombre, usuario, password) },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0070C0)),
            shape = MaterialTheme.shapes.extraSmall,
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("GUARDAR REGISTRO", color = Color.White)
        }
    }
}