package com.example.app_inventario_arquitectura

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.androidmaster.R
import com.google.android.material.button.MaterialButton
import com.google.firebase.firestore.FirebaseFirestore

class go_crear_Fragment : Fragment(R.layout.go_crear_fragment) {

    private var tipoGestion: String? = null
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            tipoGestion = it.getString(ARG_TIPO_GESTION)
        }
        db = FirebaseFirestore.getInstance()  // Inicializar Firestore
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.go_crear_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tituloTextView: TextView = view.findViewById(R.id.crear_obras)
        tituloTextView.text = "Crear $tipoGestion"

        val etCodigoObra: EditText = view.findViewById(R.id.etCodigoObra)
        val etNombreObra: EditText = view.findViewById(R.id.etNombreObra)
        val etClienteObra: EditText = view.findViewById(R.id.etClienteObra)
        val etDepartamentoObra: EditText = view.findViewById(R.id.etDepartamentoObra)
        val etCiudad: EditText = view.findViewById(R.id.etCiudad)
        val btnGuardar: MaterialButton = view.findViewById(R.id.btnGuardar)
        val btnCancelar: MaterialButton = view.findViewById(R.id.btnCancelar)

        btnCancelar.setOnClickListener {
            cancelarFormulario()
        }

        btnGuardar.setOnClickListener {
            val codigoObra = etCodigoObra.text.toString()
            val nombreObra = etNombreObra.text.toString()
            val clienteObra = etClienteObra.text.toString()
            val departamentoObra = etDepartamentoObra.text.toString()
            val ciudadObra = etCiudad.text.toString()

            if (codigoObra.isNotEmpty() && nombreObra.isNotEmpty()) {
                // Crear un mapa de datos
                val obraData = hashMapOf(
                    "codigo" to codigoObra,
                    "nombre" to nombreObra,
                    "cliente" to clienteObra,
                    "departamento" to departamentoObra,
                    "ciudad" to ciudadObra
                )

                // Guardar datos en Firestore
                db.collection("obras")
                    .add(obraData)
                    .addOnSuccessListener {
                        Toast.makeText(context, "Obra guardada con éxito", Toast.LENGTH_SHORT).show()
                        limpiarCampos()
                    }
                    .addOnFailureListener {
                        Toast.makeText(context, "Error al guardar la obra", Toast.LENGTH_SHORT).show()
                    }
            } else {
                Toast.makeText(context, "Por favor, completa los campos obligatorios", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun limpiarCampos() {
        view?.findViewById<EditText>(R.id.etCodigoObra)?.text?.clear()
        view?.findViewById<EditText>(R.id.etNombreObra)?.text?.clear()
        view?.findViewById<EditText>(R.id.etClienteObra)?.text?.clear()
        view?.findViewById<EditText>(R.id.etDepartamentoObra)?.text?.clear()
        view?.findViewById<EditText>(R.id.etCiudad)?.text?.clear()
    }

    private fun cancelarFormulario() {
        limpiarCampos()
        Toast.makeText(requireContext(), "Formulario cancelado", Toast.LENGTH_SHORT).show()
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
