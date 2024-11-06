package com.example.app_inventario_arquitectura

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.androidmaster.R

class go_crear_Fragment : Fragment(R.layout.go_crear_fragment) {

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

        view.findViewById<View>(R.id.btnGuardar).setOnClickListener {
            guardarObra()
        }

        view.findViewById<View>(R.id.btnCancelar).setOnClickListener {
            cancelarCreacion()
        }
    }

    private fun guardarObra() {
        val codigoObra = etCodigoObra.text.toString().trim()
        val nombreObra = etNombreObra.text.toString().trim()
        val clienteObra = etClienteObra.text.toString().trim()
        val departamentoObra = etDepartamentoObra.text.toString().trim()
        val ciudad = etCiudad.text.toString().trim()

        if (codigoObra.isNotEmpty() && nombreObra.isNotEmpty() && clienteObra.isNotEmpty() &&
            departamentoObra.isNotEmpty() && ciudad.isNotEmpty()) {
            Toast.makeText(requireContext(), "Obra guardada exitosamente", Toast.LENGTH_SHORT).show()
            limpiarCampos()
        } else {
            Toast.makeText(requireContext(), "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
        }
    }

    private fun cancelarCreacion() {
        limpiarCampos()
        Toast.makeText(requireContext(), "Creación cancelada", Toast.LENGTH_SHORT).show()
    }

    private fun limpiarCampos() {
        etCodigoObra.text.clear()
        etNombreObra.text.clear()
        etClienteObra.text.clear()
        etDepartamentoObra.text.clear()
        etCiudad.text.clear()
    }

    companion object {
        private const val ARG_TIPO_GESTION = "tipo_gestion"

        fun newInstance(tipoGestion: String): go_crear_Fragment {
            val fragment = go_crear_Fragment()
            val args = Bundle()
            args.putString(ARG_TIPO_GESTION, tipoGestion)
            fragment.arguments = args
            return fragment
        }
    }
}


