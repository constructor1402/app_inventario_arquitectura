package com.example.app_inventario_arquitectura

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.androidmaster.R
import com.google.firebase.firestore.FirebaseFirestore

class go_eliminar_Fragment : Fragment() {

    private lateinit var nombreObra: EditText
    private lateinit var direccionObra: EditText
    private lateinit var ubicacionObra: EditText
    private lateinit var observaciones: EditText
    private lateinit var tvError: TextView
    private lateinit var progressBar: ProgressBar

    private lateinit var database: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.go_eliminar_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nombreObra = view.findViewById(R.id.etNombreObraEliminar)
        direccionObra = view.findViewById(R.id.etDireccionObraEliminar)
        ubicacionObra = view.findViewById(R.id.etUbicacionObraEliminar)
        observaciones = view.findViewById(R.id.etObservacionesEliminar)
        tvError = view.findViewById(R.id.tvError)
        progressBar = view.findViewById(R.id.progressBar)

        // Inicializar la base de datos de Firebase
        database = FirebaseFirestore.getInstance()

        view.findViewById<Button>(R.id.btnGuardar).setOnClickListener {
            eliminarRegistro()
        }

        view.findViewById<Button>(R.id.btnCancelar).setOnClickListener {
            cancelar()
        }
    }

    private fun eliminarRegistro() {
        val nombre = nombreObra.text.toString().trim()

        if (nombre.isNotEmpty()) {
            progressBar.visibility = View.VISIBLE
            tvError.visibility = View.GONE

            // Buscar el registro en Firestore por el nombre de la obra
            database.collection("obras")
                .whereEqualTo("codigo", nombre)
                .get()
                .addOnSuccessListener { snapshot ->
                    if (!snapshot.isEmpty) {
                        // Eliminar cada documento que coincide con el nombre
                        for (document in snapshot.documents) {
                            database.collection("obras").document(document.id)
                                .delete()
                                .addOnSuccessListener {
                                    Toast.makeText(context, "Registro eliminado exitosamente", Toast.LENGTH_SHORT).show()
                                    progressBar.visibility = View.GONE
                                    limpiarCampos()
                                }
                                .addOnFailureListener {
                                    Toast.makeText(context, "Error al eliminar el registro", Toast.LENGTH_SHORT).show()
                                    progressBar.visibility = View.GONE
                                }
                        }
                    } else {
                        Toast.makeText(context, "No se encontró ninguna obra con ese nombre", Toast.LENGTH_SHORT).show()
                        progressBar.visibility = View.GONE
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(context, "Error al buscar el registro", Toast.LENGTH_SHORT).show()
                    progressBar.visibility = View.GONE
                }
        } else {
            tvError.visibility = View.VISIBLE
            tvError.text = "Por favor, ingrese el nombre de la obra"
        }
    }

    private fun cancelar() {
        limpiarCampos()
        Toast.makeText(context, "Cancelar la eliminación", Toast.LENGTH_SHORT).show()
    }

    private fun limpiarCampos() {
        nombreObra.text.clear()
        direccionObra.text.clear()
        ubicacionObra.text.clear()
        observaciones.text.clear()
    }

    companion object {
        private const val ARG_TIPO_GESTION = "tipo_gestion"

        fun newInstance(tipoGestion: String): go_eliminar_Fragment {
            val fragment = go_eliminar_Fragment()
            val args = Bundle()
            args.putString(ARG_TIPO_GESTION, tipoGestion)
            fragment.arguments = args
            return fragment
        }
    }
}
