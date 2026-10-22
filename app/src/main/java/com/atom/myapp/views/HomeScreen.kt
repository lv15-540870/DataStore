package com.atom.myapp.views

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.stringPreferencesKey
import com.atom.myapp.R
import com.atom.myapp.dataStore
import kotlinx.coroutines.flow.map

@Composable
fun HomeScreen() {
    val context = LocalContext.current

    val nombreFlow = context.dataStore.data.map { it[stringPreferencesKey("nombre")] ?: "N/A" }
    val usuarioFlow = context.dataStore.data.map { it[stringPreferencesKey("usuario")] ?: "N/A" }

    val nombre by nombreFlow.collectAsState(initial = "")
    val usuario by usuarioFlow.collectAsState(initial = "")

    Column(
        modifier = Modifier.fillMaxSize().padding(32.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(text = nombre.uppercase(), fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Text(text = usuario.uppercase(), fontSize = 14.sp)

        Spacer(modifier = Modifier.height(60.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("ESCANEA AQUÍ", fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Spacer(modifier = Modifier.height(16.dp))

            Icon(
                painter = painterResource(id = R.drawable.qr),
                contentDescription = "Código QR",
                tint = Color.Unspecified,
                modifier = Modifier.size(250.dp)
            )
        }
    }
}