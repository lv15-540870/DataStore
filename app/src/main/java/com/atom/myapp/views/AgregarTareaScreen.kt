package com.atom.myapp.views

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.atom.myapp.data.DaTarea

@Composable
fun AgregarTareaScreen(onGuardarTarea: (DaTarea) -> Unit, onCancelar: () -> Unit
) {
    var nombreTarea by remember { mutableStateOf("") }
    var categoriaSeleccionada by remember { mutableStateOf("Escuela") }
    var esPrioridadAlta by remember { mutableStateOf(false) }

    var menuExpandido by remember { mutableStateOf(false) }
    val listaCategorias = listOf("Escuela", "Trabajo", "Personal")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Nueva Tarea",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        OutlinedTextField(
            value = nombreTarea,
            onValueChange = { nombreTarea = it },
            label = { Text("Nombre de la tarea") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Text(
            text = "Selecciona la categoría:",
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(top = 8.dp)
        )

        Column {
            listaCategorias.forEach { categoria ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    RadioButton(
                        selected = (categoriaSeleccionada == categoria),
                        onClick = { categoriaSeleccionada = categoria }
                    )
                    Text(
                        text = categoria,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        }

        HorizontalDivider()

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Prioridad alta",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
            Switch(
                checked = esPrioridadAlta,
                onCheckedChange = { esPrioridadAlta = it }
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                if (nombreTarea.isNotBlank()) {
                    val nueva = DaTarea(
                        id = (System.currentTimeMillis() % 10000).toInt(),
                        nombre = nombreTarea,
                        categoria = categoriaSeleccionada,
                        prioridadAlta = esPrioridadAlta,
                        terminada = false
                    )
                    onGuardarTarea(nueva)
                }
            },
            enabled = nombreTarea.isNotBlank(),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(text = "Guardar tarea", fontSize = 16.sp)
        }

        OutlinedButton(
            onClick = onCancelar,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(text = "Cancelar", fontSize = 16.sp)
        }
    }
}