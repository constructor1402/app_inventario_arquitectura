package com.example.app_inventario_arquitectura

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.androidmaster.R

class go_editar_Fragment : Fragment(R.layout.go_editar_fragment) {

    private lateinit var etCodigoObra: EditText
    private lateinit var etNombreObra: EditText
    private lateinit var etClienteObra: EditText
    private lateinit var etDepartamentoObra: EditText
    private lateinit var etCiudad: EditText

    private var tipoGestion: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            tipoGestion = it.getString(ARG_TIPO_GESTION)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        etCodigoObra = view.findViewById(R.id.etCodigoObra)
        etNombreObra = view.findViewById(R.id.etNombreObra)
        etClienteObra = view.findViewById(R.id.etClienteObra)
        etDepartamentoObra = view.findViewById(R.id.etDepartamentoObra)
        etCiudad = view.findViewById(R.id.etCiudad)

        view.findViewById<View>(R.id.btnBuscar).setOnClickListener {
            buscarObra()
        }

        view.findViewById<View>(R.id.btnGuardar).setOnClickListener {
            guardarEdicion()
        }

        view.findViewById<View>(R.id.btnCancelarEditar).setOnClickListener {
            cancelarEdicion()
        }

        view.findViewById<View>(R.id.btnEliminar).setOnClickListener {
            eliminarObra()
        }
    }

    private fun buscarObra() {
        val nombreObra = etNombreObra.text.toString().trim()
        if (nombreObra.isNotEmpty()) {
            etCodigoObra.setText("Código encontrado") // Simulación de búsqueda
            etClienteObra.setText("Cliente de prueba")
            etDepartamentoObra.setText("Departamento de prueba")
            etCiudad.setText("Ciudad de prueba")
            habilitarCampos(true)
            Toast.makeText(requireContext(), "Obra encontrada", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), "Ingrese el nombre de la obra para buscar", Toast.LENGTH_SHORT).show()
        }
    }

    private fun guardarEdicion() {
        if (etCodigoObra.text.toString().isNotEmpty()) {
            val clienteObra = etClienteObra.text.toString().trim()
            val departamentoObra = etDepartamentoObra.text.toString().trim()
            val ciudad = etCiudad.text.toString().trim()

            if (clienteObra.isNotEmpty() && departamentoObra.isNotEmpty() && ciudad.isNotEmpty()) {
                Toast.makeText(requireContext(), "Obra actualizada exitosamente", Toast.LENGTH_SHORT).show()
                limpiarCampos()
                habilitarCampos(false)
            } else {
                Toast.makeText(requireContext(), "Complete todos los campos", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(requireContext(), "Primero busque la obra", Toast.LENGTH_SHORT).show()
        }
    }

    private fun cancelarEdicion() {
        limpiarCampos()
        habilitarCampos(false)
        Toast.makeText(requireContext(), "Edición cancelada", Toast.LENGTH_SHORT).show()
    }

    private fun eliminarObra() {
        if (etCodigoObra.text.toString().isNotEmpty()) {
            limpiarCampos()
            habilitarCampos(false)
            Toast.makeText(requireContext(), "Obra eliminada exitosamente", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), "Primero busque la obra", Toast.LENGTH_SHORT).show()
        }
    }

    private fun limpiarCampos() {
        etCodigoObra.text.clear()
        etNombreObra.text.clear()
        etClienteObra.text.clear()
        etDepartamentoObra.text.clear()
        etCiudad.text.clear()
    }

    private fun habilitarCampos(habilitar: Boolean) {
        etCodigoObra.isEnabled = false
        etClienteObra.isEnabled = habilitar
        etDepartamentoObra.isEnabled = habilitar
        etCiudad.isEnabled = habilitar
        view?.findViewById<View>(R.id.btnGuardar)?.isEnabled = habilitar
        view?.findViewById<View>(R.id.btnEliminar)?.isEnabled = habilitar
    }

    companion object {
        private const val ARG_TIPO_GESTION = "tipo_gestion"

        fun newInstance(tipoGestion: String): go_editar_Fragment {
            val fragment = go_editar_Fragment()
            val args = Bundle()
            args.putString(ARG_TIPO_GESTION, tipoGestion)
            fragment.arguments = args
            return fragment
        }
    }
}
