package com.atom.myapp.views

import androidx.compose.runtime.*
import com.atom.myapp.data.DaTarea

@Composable
fun ControlTareaScreen() {
    val listaTareas = remember {
        mutableStateListOf(
            DaTarea(1, "Estudiar para el examen de Conmutacion para el viernes", "Escuela", true, false),
            DaTarea(2, "Hacer avance del Cierre de Sprint 08", "Escuela", false, false),
            DaTarea(3, "Comprar materiales para hacer pays", "Personal", false, true),
            DaTarea(4, "Avanzarle al proyecto de la unidad 3 de LyA2", "Escuela", true, false)
        )
    }

    var pantallaActual by remember { mutableStateOf("lista") }

    if (pantallaActual == "lista") {
        ListaTareaScreen(
            listaTareas = listaTareas,
            onNavegarAgregar = { pantallaActual = "agregar" },
            onCambiarEstadoTarea = { tareaModificada, estaTerminada ->
                val index = listaTareas.indexOfFirst { it.id == tareaModificada.id }
                if (index != -1) {
                    listaTareas[index] = listaTareas[index].copy(terminada = estaTerminada)
                }
            }
        )
    } else {
        AgregarTareaScreen(
            onGuardarTarea = { nuevaTarea ->
                listaTareas.add(nuevaTarea)
                pantallaActual = "lista"
            },
            onCancelar = {
                pantallaActual = "lista"
            }
        )
    }
}