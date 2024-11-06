package com.example.app_inventario_arquitectura

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.androidmaster.R
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Calendar

class ge_Editar_Fragment : Fragment() {

    private val db = FirebaseFirestore.getInstance()
    private lateinit var etNumeroSerie: EditText
    private lateinit var etTipoEquipo: EditText
    private lateinit var etModelo: EditText
    private lateinit var etFechaAdquisicion: TextView
    private lateinit var etFechaCertificacion: TextView
    private lateinit var etVigencia: EditText
    private lateinit var btnFechaAdquisicion: Button
    private lateinit var btnFechaCertificacion: Button
    private lateinit var btnGuardar: Button
    private lateinit var btnCancelar: Button
    private lateinit var btnBuscar: Button
    private lateinit var progressBar: ProgressBar
    private var equipoId: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.ge_editar_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tipoGestion = arguments?.getString(ARG_TIPO_GESTION)
        val tituloTextView: TextView = view.findViewById(R.id.edit_modif_gestion)
        tituloTextView.text = "Editar o Modificar $tipoGestion"

        etNumeroSerie = view.findViewById(R.id.etNumeroSerie)
        etTipoEquipo = view.findViewById(R.id.etTipoEquipo)
        etModelo = view.findViewById(R.id.etModelo)
        etFechaAdquisicion = view.findViewById(R.id.etFechaAdquisicion)
        etFechaCertificacion = view.findViewById(R.id.etFechaCertificacion)
        etVigencia = view.findViewById(R.id.etVigencia)
        btnFechaAdquisicion = view.findViewById(R.id.btnFechaAdquisicion)
        btnFechaCertificacion = view.findViewById(R.id.btnFechaCertificacion)
        btnGuardar = view.findViewById(R.id.btnGuardar)
        btnCancelar = view.findViewById(R.id.btnCancelarEditar)
        btnBuscar = view.findViewById(R.id.btnBuscar)
        progressBar = view.findViewById(R.id.progressBar)

        progressBar.visibility = View.INVISIBLE  // Oculta el ProgressBar inicialmente

        btnGuardar.setOnClickListener { validarCampos() }
        btnCancelar.setOnClickListener { cancelarEdicion() }

        btnFechaAdquisicion.setOnClickListener {
            showDatePickerDialog { date -> etFechaAdquisicion.text = date }
        }
        btnFechaCertificacion.setOnClickListener {
            showDatePickerDialog { date -> etFechaCertificacion.text = date }
        }

        btnBuscar.setOnClickListener {
            val numeroSerie = etNumeroSerie.text.toString().trim()
            if (numeroSerie.isNotEmpty()) {
                buscarEquipoPorNumeroSerie(numeroSerie)
            } else {
                Toast.makeText(context, "Ingrese el número de serie para buscar", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validarCampos() {
        val numeroSerie = etNumeroSerie.text.toString().trim()
        val tipoEquipo = etTipoEquipo.text.toString().trim()
        val modelo = etModelo.text.toString().trim()
        val fechaAdquisicion = etFechaAdquisicion.text.toString().trim()
        val fechaCertificacion = etFechaCertificacion.text.toString().trim()
        val vigencia = etVigencia.text.toString().trim()

        if (numeroSerie.isEmpty() || tipoEquipo.isEmpty() || modelo.isEmpty() ||
            fechaAdquisicion.isEmpty() || fechaCertificacion.isEmpty() || vigencia.isEmpty()) {
            Toast.makeText(requireContext(), "Por favor, complete todos los campos obligatorios.", Toast.LENGTH_SHORT).show()
        } else {
            actualizarEquipo(equipoId, numeroSerie, tipoEquipo, modelo, fechaAdquisicion, fechaCertificacion, vigencia)
        }
    }

    private fun actualizarEquipo(id: String?, numeroSerie: String, tipoEquipo: String, modelo: String, fechaAdquisicion: String, fechaCertificacion: String, vigencia: String) {
        if (id == null) return

        val equipo = hashMapOf(
            "numeroSerie" to numeroSerie,
            "tipoEquipo" to tipoEquipo,
            "modelo" to modelo,
            "fechaAdquisicion" to fechaAdquisicion,
            "fechaCertificacion" to fechaCertificacion,
            "vigencia" to vigencia
        )

        db.collection("equipos").document(id)
            .set(equipo)
            .addOnSuccessListener {
                Toast.makeText(context, "Datos actualizados correctamente", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(context, "Error al actualizar datos", Toast.LENGTH_SHORT).show()
            }
    }

    private fun cancelarEdicion() {
        etNumeroSerie.text.clear()
        etTipoEquipo.text.clear()
        etModelo.text.clear()
        etFechaAdquisicion.text = ""
        etFechaCertificacion.text = ""
        etVigencia.text.clear()
        Toast.makeText(requireContext(), "Edición cancelada", Toast.LENGTH_SHORT).show()
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
                    etTipoEquipo.setText(equipo.tipoEquipo)
                    etModelo.setText(equipo.modelo)
                    etFechaAdquisicion.text = equipo.fechaAdquisicion
                    etFechaCertificacion.text = equipo.fechaCertificacion
                    etVigencia.setText(equipo.vigencia)
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

    private fun showDatePickerDialog(onDateSet: (String) -> Unit) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, selectedYear, selectedMonth, selectedDay ->
                val formattedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                onDateSet(formattedDate)
            },
            year,
            month,
            day
        )
        datePickerDialog.show()
    }

    companion object {
        private const val ARG_TIPO_GESTION = "tipo_gestion"

        fun newInstance(tipoGestion: String): ge_Editar_Fragment {
            val fragment = ge_Editar_Fragment()
            val args = Bundle()
            args.putString(ARG_TIPO_GESTION, tipoGestion)
            fragment.arguments = args
            return fragment
        }
    }
}
