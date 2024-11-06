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
import com.google.firebase.firestore.SetOptions

class go_editar_Fragment : Fragment(R.layout.go_editar_fragment) {

    private lateinit var db: FirebaseFirestore
    private var documentoId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = FirebaseFirestore.getInstance()  // Inicializar Firestore
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.go_editar_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tituloTextView: TextView = view.findViewById(R.id.editar_obras)
        tituloTextView.text = "Editar o Modificar Obra"

        val etCodigoObra: EditText = view.findViewById(R.id.etCodigoObra)
        val etNombreObra: EditText = view.findViewById(R.id.etNombreObra)
        val etClienteObra: EditText = view.findViewById(R.id.etClienteObra)
        val etDepartamentoObra: EditText = view.findViewById(R.id.etDepartamentoObra)
        val etCiudad: EditText = view.findViewById(R.id.etCiudad)
        val btnBuscar: MaterialButton = view.findViewById(R.id.btnBuscar)
        val btnGuardar: MaterialButton = view.findViewById(R.id.btnGuardar)
        val btnCancelar: MaterialButton = view.findViewById(R.id.btnCancelarEditar_go)

        btnBuscar.setOnClickListener {
            val codigoObra = etCodigoObra.text.toString()

            if (codigoObra.isNotEmpty()) {
                db.collection("obras")
                    .whereEqualTo("codigo", codigoObra)
                    .get()
                    .addOnSuccessListener { documents ->
                        if (documents.isEmpty) {
                            Toast.makeText(context, "Obra no encontrada", Toast.LENGTH_SHORT).show()
                        } else {
                            for (document in documents) {
                                documentoId = document.id
                                etNombreObra.setText(document.getString("nombre"))
                                etClienteObra.setText(document.getString("cliente"))
                                etDepartamentoObra.setText(document.getString("departamento"))
                                etCiudad.setText(document.getString("ciudad"))
                                Toast.makeText(context, "Obra cargada para editar", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                    .addOnFailureListener {
                        Toast.makeText(context, "Error al buscar la obra", Toast.LENGTH_SHORT).show()
                    }
            } else {
                Toast.makeText(context, "Ingresa un código de obra para buscar", Toast.LENGTH_SHORT).show()
            }
        }

        btnGuardar.setOnClickListener {
            val nombreObra = etNombreObra.text.toString()
            val clienteObra = etClienteObra.text.toString()
            val departamentoObra = etDepartamentoObra.text.toString()
            val ciudadObra = etCiudad.text.toString()

            val obraData = hashMapOf(
                "nombre" to nombreObra,
                "cliente" to clienteObra,
                "departamento" to departamentoObra,
                "ciudad" to ciudadObra
            )

            if (documentoId != null) {
                // Actualizar documento existente
                db.collection("obras").document(documentoId!!)
                    .set(obraData, SetOptions.merge())
                    .addOnSuccessListener {
                        Toast.makeText(context, "Obra actualizada con éxito", Toast.LENGTH_SHORT).show()
                        limpiarCampos()
                    }
                    .addOnFailureListener {
                        Toast.makeText(context, "Error al actualizar la obra", Toast.LENGTH_SHORT).show()
                    }
            } else {
                // Guardar nuevo documento si no existe uno previo
                db.collection("obras")
                    .add(obraData)
                    .addOnSuccessListener {
                        Toast.makeText(context, "Obra guardada con éxito", Toast.LENGTH_SHORT).show()
                        limpiarCampos()
                    }
                    .addOnFailureListener {
                        Toast.makeText(context, "Error al guardar la obra", Toast.LENGTH_SHORT).show()
                    }
            }
        }

        btnCancelar.setOnClickListener {
            limpiarCampos()
        }
    }

    private fun limpiarCampos() {
        view?.findViewById<EditText>(R.id.etCodigoObra)?.text?.clear()
        view?.findViewById<EditText>(R.id.etNombreObra)?.text?.clear()
        view?.findViewById<EditText>(R.id.etClienteObra)?.text?.clear()
        view?.findViewById<EditText>(R.id.etDepartamentoObra)?.text?.clear()
        view?.findViewById<EditText>(R.id.etCiudad)?.text?.clear()
        documentoId = null
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
