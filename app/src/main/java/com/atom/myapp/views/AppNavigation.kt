package com.atom.myapp.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.atom.myapp.dataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val nombreKey = stringPreferencesKey("nombre")
    val usuarioKey = stringPreferencesKey("usuario")
    val passwordKey = stringPreferencesKey("password")

    // Leemos los datos actuales en tiempo real para poder validar el login
    val savedNombre by context.dataStore.data.map { it[nombreKey] ?: "" }.collectAsState(initial = "")
    val savedPassword by context.dataStore.data.map { it[passwordKey] ?: "" }.collectAsState(initial = "")

    var startDestination by remember { mutableStateOf("login") }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        val currentUser = context.dataStore.data.map { it[usuarioKey] }.first()
        if (!currentUser.isNullOrEmpty()) {
            startDestination = "home"
        }
        isLoading = false
    }

    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        NavHost(navController = navController, startDestination = startDestination) {

            composable("login") {
                LoginScreen(
                    onNavigateToRegister = { navController.navigate("registro") },
                    onLoginClick = { nombreIngresado, passwordIngresada ->
                        // LÓGICA DE VALIDACIÓN
                        if (savedNombre.isEmpty() || savedPassword.isEmpty()) {
                            false // No hay nadie registrado aún en el LocalStorage
                        } else if (nombreIngresado == savedNombre && passwordIngresada == savedPassword) {
                            // Los datos coinciden, redirigimos a Home
                            navController.navigate("home") { popUpTo("login") { inclusive = true } }
                            true
                        } else {
                            false // Los datos no coinciden
                        }
                    }
                )
            }

            composable("registro") {
                RegistroScreen(
                    onRegistroGuardado = { nombre, usuario, password ->
                        coroutineScope.launch {
                            context.dataStore.edit { prefs ->
                                prefs[nombreKey] = nombre
                                prefs[usuarioKey] = usuario
                                prefs[passwordKey] = password
                            }
                            navController.navigate("home") { popUpTo("login") { inclusive = true } }
                        }
                    }
                )
            }

            composable("home") {
                HomeScreen()
            }
        }
    }
}