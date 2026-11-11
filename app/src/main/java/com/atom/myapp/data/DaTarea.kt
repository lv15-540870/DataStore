package com.atom.myapp.data

data class DaTarea(
    val id: Int,
    var nombre: String,
    var categoria: String,
    var prioridadAlta: Boolean,
    var terminada: Boolean
)