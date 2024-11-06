package com.example.app_inventario_arquitectura

data class Equipo(
    var numeroSerie: String = "",
    var tipoEquipo: String = "",
    var modelo: String = "",
    var fechaAdquisicion: String = "",
    var fechaCertificacion: String = "",
    var vigencia: String = "",
    var urlImagen: String? = null
)
