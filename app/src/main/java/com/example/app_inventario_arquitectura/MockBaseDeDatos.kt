package com.example.app_inventario_arquitectura

class MockBaseDeDatos : BaseDeDatos {

    override fun buscarEquipoPorNumeroSerie(
        numeroSerie: String,
        onSuccess: (Equipo) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        // Simular datos de un equipo
        val equipo = Equipo(
            numeroSerie = numeroSerie,
            tipoEquipo = "Simulado",
            modelo = "Modelo X",
            fechaAdquisicion = "01/01/2021",
            fechaCertificacion = "01/01/2022",
            vigencia = "5 años",
            urlImagen = "url_de_imagen"
        )
        onSuccess(equipo) // Llama a onSuccess para simular una consulta exitosa
    }

    override fun guardarEquipo(
        equipo: Equipo,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        // Simula una operación de guardado
        onSuccess() // Llama a onSuccess para simular una operación exitosa
    }
}
