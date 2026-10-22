package com.atom.myapp.views

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun RegistroScreen(onRegistroGuardado: (String, String, String) -> Unit) {
    // 1. Declaramos el contexto aquí para que el Toast pueda funcionar
    val context = LocalContext.current

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
            label = { Text("Nombre:") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = usuario,
            onValueChange = { usuario = it },
            label = { Text("Usuario:") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password:") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = {
                if (nombre.isNotBlank() && usuario.isNotBlank() && password.isNotBlank()) {
                    onRegistroGuardado(nombre.trim(), usuario.trim(), password.trim())
                } else {

                    android.widget.Toast.makeText(context, "Favor de llenar todos los campos", android.widget.Toast.LENGTH_SHORT).show()
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
            shape = MaterialTheme.shapes.extraSmall,
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Guardar Registro", color = Color.White)
        }
    }
}