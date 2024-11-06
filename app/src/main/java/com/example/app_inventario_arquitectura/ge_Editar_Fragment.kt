package com.example.app_inventario_arquitectura

import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.androidmaster.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.util.Calendar

class ge_Editar_Fragment : Fragment() {

    private val db = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance()
    private lateinit var etNumeroSerie: EditText
    private lateinit var etTipoEquipo: EditText
    private lateinit var etModelo: EditText
    private lateinit var etFechaAdquisicion: TextView
    private lateinit var etFechaCertificacion: TextView
    private lateinit var etVigencia: EditText
    private lateinit var ivImagenEquipo: ImageView
    private lateinit var btnFechaAdquisicion: Button
    private lateinit var btnFechaCertificacion: Button
    private lateinit var btnGuardar: Button
    private lateinit var btnCancelar: Button
    private lateinit var btnBuscar: Button
    private lateinit var btnCargarImagen: Button
    private lateinit var progressBar: ProgressBar
    private var equipoId: String? = null
    private var imageUri: Uri? = null

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
        ivImagenEquipo = view.findViewById(R.id.ivImagenEquipo)
        btnFechaAdquisicion = view.findViewById(R.id.btnFechaAdquisicion)
        btnFechaCertificacion = view.findViewById(R.id.btnFechaCertificacion)
        btnGuardar = view.findViewById(R.id.btnGuardar)
        btnCancelar = view.findViewById(R.id.btnCancelarEditar)
        btnBuscar = view.findViewById(R.id.btnBuscar)
        btnCargarImagen = view.findViewById(R.id.btnCargarImagen)
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

        btnCargarImagen.setOnClickListener { seleccionarImagen() }
    }

    private fun seleccionarImagen() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_IMAGE_PICK)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_PICK && data != null && data.data != null) {
            imageUri = data.data
            ivImagenEquipo.setImageURI(imageUri)
        }
    }

    private fun subirImagenYGuardarDatos(id: String, equipoData: Map<String, Any>) {
        imageUri?.let { uri ->
            val storageRef = storage.reference.child("imagenes/$id.jpg")
            storageRef.putFile(uri)
                .addOnSuccessListener {
                    storageRef.downloadUrl.addOnSuccessListener { url ->
                        val equipoConImagen = equipoData + mapOf("urlImagen" to url.toString())
                        actualizarEquipo(id, equipoConImagen)
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(context, "Error al subir la imagen", Toast.LENGTH_SHORT).show()
                }
        } ?: actualizarEquipo(id, equipoData)
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
            val equipoData = mapOf(
                "numeroSerie" to numeroSerie,
                "tipoEquipo" to tipoEquipo,
                "modelo" to modelo,
                "fechaAdquisicion" to fechaAdquisicion,
                "fechaCertificacion" to fechaCertificacion,
                "vigencia" to vigencia
            )
            subirImagenYGuardarDatos(equipoId ?: "", equipoData)
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

    private fun actualizarEquipo(id: String, equipoData: Map<String, Any>) {
        db.collection("equipos").document(id)
            .set(equipoData)
            .addOnSuccessListener {
                Toast.makeText(context, "Datos actualizados correctamente", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(context, "Error al actualizar datos", Toast.LENGTH_SHORT).show()
            }
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

                    equipo.urlImagen?.let { url ->
                        // Cargar imagen desde URL en Firebase Storage
                        Glide.with(this).load(url).into(ivImagenEquipo)
                    }
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
        private const val REQUEST_IMAGE_PICK = 1

        fun newInstance(tipoGestion: String): ge_Editar_Fragment {
            val fragment = ge_Editar_Fragment()
            val args = Bundle()
            args.putString(ARG_TIPO_GESTION, tipoGestion)
            fragment.arguments = args
            return fragment
        }
    }
}
