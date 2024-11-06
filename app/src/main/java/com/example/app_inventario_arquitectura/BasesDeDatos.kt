package com.example.app_inventario_arquitectura

interface BaseDeDatos {
    fun buscarEquipoPorNumeroSerie(
        numeroSerie: String,
        onSuccess: (Equipo) -> Unit,
        onFailure: (Exception) -> Unit
    )

    fun guardarEquipo(
        equipo: Equipo,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    )
}
