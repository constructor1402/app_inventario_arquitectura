package com.example.app_inventario_arquitectura

import android.app.Activity
import android.content.Intent
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

class ge_Eliminar_Fragment : Fragment() {

    private lateinit var etNumeroSerieEliminar: EditText
    private lateinit var btnEliminar: Button
    private lateinit var btnCancelar: Button
    private lateinit var etNumeroSerie: EditText
    private lateinit var etTipoEquipo: TextView
    private lateinit var etFechaAdquisicion: TextView
    private lateinit var etFechaCertificacion: TextView
    private lateinit var progressBar: ProgressBar
    private val db = FirebaseFirestore.getInstance()
    private var equipoId: String? = null


    private val firestore = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate el layout para este fragment
        return inflater.inflate(R.layout.ge_eliminar_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        etNumeroSerieEliminar = view.findViewById(R.id.etNumeroSerie)
        btnEliminar = view.findViewById(R.id.btnEliminar)
        btnCancelar = view.findViewById(R.id.btnCancelar)
        etTipoEquipo = view.findViewById(R.id.etTipoEquipo)
        etFechaAdquisicion = view.findViewById(R.id.etFechaAdquisicion)
        etFechaCertificacion = view.findViewById(R.id.etFechaCertificacion_Eliminar)
        progressBar = view.findViewById(R.id.progressBar)

        // Configura el botón Buscar
        val btnBuscar: Button = view.findViewById(R.id.btnBuscar)
        btnBuscar.setOnClickListener {
            val numeroSerie = etNumeroSerieEliminar.text.toString()
            if (numeroSerie.isNotEmpty()) {
                buscarEquipoPorNumeroSerie(numeroSerie)
            } else {
                Toast.makeText(context, "Ingrese un número de serie", Toast.LENGTH_SHORT).show()
            }
        }

        btnEliminar.setOnClickListener {
            eliminarRegistro()
        }

        btnCancelar.setOnClickListener {
            cancelarFormulario()
        }
    }

    private fun eliminarRegistro() {
        val numeroSerie = etNumeroSerieEliminar.text.toString().trim()

        if (numeroSerie.isEmpty()) {
            Toast.makeText(requireContext(), "Por favor, ingrese el número de serie.", Toast.LENGTH_SHORT).show()
            return
        }

        // Buscar el documento en Firestore con el número de serie proporcionado
        firestore.collection("equipos")
            .whereEqualTo("numeroSerie", numeroSerie)
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (!querySnapshot.isEmpty) {
                    // Si se encuentra el documento, eliminarlo
                    val document = querySnapshot.documents[0]
                    firestore.collection("equipos").document(document.id)
                        .delete()
                        .addOnSuccessListener {
                            Toast.makeText(context, "Equipo eliminado correctamente", Toast.LENGTH_SHORT).show()
                            cancelarFormulario()  // Limpiar el formulario después de eliminar
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(context, "Error al eliminar el equipo: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                } else {
                    Toast.makeText(context, "No se encontró el equipo con ese número de serie.", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(context, "Error al consultar la base de datos: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun cancelarFormulario() {
        etNumeroSerieEliminar.text.clear()
        etTipoEquipo.text = ""
        etFechaAdquisicion.text = ""
        etFechaCertificacion.text =""
        Toast.makeText(requireContext(), "Formulario cancelado", Toast.LENGTH_SHORT).show()
    }

    private fun buscarEquipoPorNumeroSerie(numeroSerie: String) {
        progressBar.visibility = View.VISIBLE  // Muestra el ProgressBar
        db.collection("equipos")
            .whereEqualTo("numeroSerie", numeroSerie)
            .get()
            .addOnSuccessListener { documents ->
                equipoId = null
                for (document in documents) {
                    equipoId = document.id
                    val equipo = document.toObject(Equipo::class.java)
                    etTipoEquipo.text = equipo.tipoEquipo
                    etFechaAdquisicion.text = equipo.fechaAdquisicion
                    etFechaCertificacion.text = equipo.fechaCertificacion
                    break
                }
                if (equipoId == null) {
                    Toast.makeText(context, "Equipo no encontrado", Toast.LENGTH_SHORT).show()
                }
                progressBar.visibility = View.INVISIBLE  // Oculta el ProgressBar
            }
            .addOnFailureListener {
                Toast.makeText(context, "Error al buscar equipo", Toast.LENGTH_SHORT).show()
                progressBar.visibility = View.INVISIBLE  // Oculta el ProgressBar en caso de error
            }
    }

    companion object {
        private const val ARG_TIPO_GESTION = "tipo_gestion"

        fun newInstance(tipoGestion: String): ge_Eliminar_Fragment {
            val fragment = ge_Eliminar_Fragment()
            val args = Bundle()
            args.putString(ARG_TIPO_GESTION, tipoGestion)
            fragment.arguments = args
            return fragment
        }
    }
}
