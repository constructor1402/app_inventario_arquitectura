package com.example.app_inventario_arquitectura

data class Equipo(
    val numeroSerie: String,
    val tipoEquipo: String,
    val modelo: String,
    val fechaAdquisicion: String,
    val fechaCertificacion: String,
    val vigencia: String,
    val urlImagen: String? = null
)