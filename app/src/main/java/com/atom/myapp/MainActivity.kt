package com.atom.myapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalProvider
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.atom.myapp.data.StoreDarkMode
import com.atom.myapp.data.StoreUserMail
import com.atom.myapp.ui.theme.MyAppTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val vm: SensorViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                /*
                /*INTERFAZ JETPACK COMPOSE DE LAS TEMPERATURAS*/
                Pantalla(vm)
                */

                /*DARKMODE*/

                val darkModeStore = StoreDarkMode(this)
                val darkMode = darkModeStore.getDarkMode.collectAsState(false)
                MyAppTheme (
                    darkTheme = darkMode.value
                ){
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ){
                        Greeting(darkModeStore, darkMode.value)
                    }
                }


            }
        }
    }
}


@Composable
fun Pantalla(vm: SensorViewModel) {
    val estado by vm.uiState.collectAsStateWithLifecycle()
    val contador by vm.contador.collectAsStateWithLifecycle()
    val snackbar = remember { SnackbarHostState() }

    // Colectar eventos Shared Flow para las alertas
    LaunchedEffect(Unit) {
        vm.alertas.collect { mensaje ->
            snackbar.showSnackbar(mensaje)
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbar) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                "Temperatura actual: ${estado.actual} °C",
                style = MaterialTheme.typography.headlineSmall
            )
            Text("Promedio (últimas 10): ${"%.1f".format(estado.promedio)} °C")
            Text("Lecturas (>25°C filtradas): ${estado.lecturas.joinToString()}")

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(onClick = vm::iniciar, enabled = !estado.monitoreando) {
                    Text("Iniciar")
                }
                Button(onClick = vm::detener, enabled = estado.monitoreando) {
                    Text("Detener")
                }
            }

            HorizontalDivider()

            Button(onClick = { vm.iniciarContador(10) }) {
                Text("Cuenta regresiva 10s")
            }

            contador?.let {
                Text("Faltan: $it s", style = MaterialTheme.typography.titleLarge)
            }

            var umbralSlider by remember { mutableStateOf(35f) }

            Text("Umbral de alerta: ${umbralSlider.toInt()} °C")
            Slider(
                value = umbralSlider,
                onValueChange = { nuevoValor ->
                    umbralSlider = nuevoValor
                    vm.actualizarUmbral(nuevoValor.toDouble())
                },
                valueRange = 20f..40f // Rango del sensor
            )
        }
    }
}

/*INTERFAZ JETPACK COMPOSE CON LO PEDIDO EN EL PUNTO 5*/


/* Interfaz (Jetpack Compose)
import android.R.attr.label
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalProvider
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.atom.myapp.data.StoreUserMail
import kotlinx.coroutines.launch

@Composable
fun Pantalla(vm: SensorViewModel) {
    val estado by vm.uiState.collectAsStateWithLifecycle()
    val contador by vm.contador.collectAsStateWithLifecycle()
    val snackbar = remember { SnackbarHostState() }

    // Colectar eventos Shared Flow para las alertas
    LaunchedEffect(Unit) {
        vm.alertas.collect { mensaje ->
            snackbar.showSnackbar(mensaje)
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbar) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                "Temperatura actual: ${estado.actual} °C",
                style = MaterialTheme.typography.headlineSmall
            )
            Text("Promedio (últimas 10): ${"%.1f".format(estado.promedio)} °C")
            Text("Lecturas: ${estado.lecturas.joinToString()}")

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(onClick = vm::iniciar, enabled = !estado.monitoreando) {
                    Text("Iniciar")
                }
                Button(onClick = vm::detener, enabled = estado.monitoreando) {
                    Text("Detener")
                }
            }

            HorizontalDivider()

            Button(onClick = { vm.iniciarContador(10) }) {
                Text("Cuenta regresiva 10s")
            }

            contador?.let {
                Text("Faltan: $it s", style = MaterialTheme.typography.titleLarge)
            }
        }
    }
}
*/


@Composable
fun Greeting(darkModeStore: StoreDarkMode, darkMode: Boolean) {
    val context= LocalContext.current
    val scope = rememberCoroutineScope()
    val dataStore = StoreUserMail(context)
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ){
        var email by rememberSaveable {mutableStateOf("")}
        var userMail = dataStore.getMail.collectAsState(initial = "")
        TextField(
            value= email,
            onValueChange = {email = it},
            label={ Text(text="Email")},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)

        )
        Spacer(modifier=Modifier.height(10.dp))
        Button(onClick = {
            scope.launch {
                dataStore.saveMail(email)
            }
        }) {
            Text(text = "Guardar Email")
        }
        Spacer(modifier=Modifier.height(10.dp))
        Text(userMail.value.toString())
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            scope.launch {
                if(darkMode){
                    darkModeStore.saveDarkMode(false)
                }
                else{
                    darkModeStore.saveDarkMode(true)
                }
            }
        }){
            Text("Cambiar a modo oscuro")
        }
    }


}
