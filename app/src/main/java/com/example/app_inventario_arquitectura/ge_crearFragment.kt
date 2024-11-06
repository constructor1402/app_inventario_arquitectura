package com.example.app_inventario_arquitectura

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.androidmaster.R
import com.google.android.material.button.MaterialButton
import java.util.Calendar

class ge_crearFragment : Fragment() {

    private var tipoGestion: String? = null
    private lateinit var baseDeDatos: BaseDeDatos
    private lateinit var ivImagenEquipo: ImageView
    private lateinit var btnCargarImagen: Button
    private lateinit var etNumeroSerie: EditText
    private lateinit var etTipoEquipo: EditText
    private lateinit var etModelo: EditText
    private lateinit var etFechaAdquisicion: TextView
    private lateinit var btnFechaAdquisicion: MaterialButton
    private lateinit var etFechaCertificacion: TextView
    private lateinit var btnFechaCertificacion: MaterialButton
    private lateinit var etVigencia: EditText
    private lateinit var btnGuardar: Button
    private lateinit var btnCancelar: Button

    private val IMAGE_PICK_CODE = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            tipoGestion = it.getString(ARG_TIPO_GESTION)
        }
        baseDeDatos = MockBaseDeDatos() // Utiliza la implementación simulada
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.ge_crear_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tituloTextView: TextView = view.findViewById(R.id.crear_gestion)
        tituloTextView.text = "Crear $tipoGestion"

        ivImagenEquipo = view.findViewById(R.id.ivImagenEquipo)
        btnCargarImagen = view.findViewById(R.id.btnCargarImagen)
        etNumeroSerie = view.findViewById(R.id.etNumeroSerie)
        etTipoEquipo = view.findViewById(R.id.etTipoEquipo)
        etModelo = view.findViewById(R.id.etModelo)
        etFechaAdquisicion = view.findViewById(R.id.etFechaAdquisicion)
        btnFechaAdquisicion = view.findViewById(R.id.btnFechaAdquisicion)
        etFechaCertificacion = view.findViewById(R.id.etFechaCertificacion_Crear)
        btnFechaCertificacion = view.findViewById(R.id.btnFechaCertificacion_Crear)
        etVigencia = view.findViewById(R.id.etVigencia)
        btnGuardar = view.findViewById(R.id.btnGuardar)
        btnCancelar = view.findViewById(R.id.btnCancelar)

        btnCargarImagen.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, IMAGE_PICK_CODE)
        }

        btnGuardar.setOnClickListener {
            validarCampos()
        }

        btnCancelar.setOnClickListener {
            cancelarFormulario()
        }

        btnFechaAdquisicion.setOnClickListener {
            showDatePickerDialog { fechaSeleccionada ->
                etFechaAdquisicion.text = fechaSeleccionada
            }
        }

        btnFechaCertificacion.setOnClickListener {
            showDatePickerDialog { fechaSeleccionada ->
                etFechaCertificacion.text = fechaSeleccionada
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_PICK_CODE && resultCode == Activity.RESULT_OK) {
            val imageUri: Uri? = data?.data
            ivImagenEquipo.setImageURI(imageUri)
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
            guardarEquipo(numeroSerie, tipoEquipo, modelo, fechaAdquisicion, fechaCertificacion, vigencia)
        }
    }

    private fun guardarEquipo(numeroSerie: String, tipoEquipo: String, modelo: String, fechaAdquisicion: String, fechaCertificacion: String, vigencia: String) {
        val equipo = Equipo(
            numeroSerie = numeroSerie,
            tipoEquipo = tipoEquipo,
            modelo = modelo,
            fechaAdquisicion = fechaAdquisicion,
            fechaCertificacion = fechaCertificacion,
            vigencia = vigencia
        )

        baseDeDatos.guardarEquipo(equipo,
            onSuccess = {
                Toast.makeText(context, "Datos guardados correctamente", Toast.LENGTH_SHORT).show()
            },
            onFailure = {
                Toast.makeText(context, "Error al guardar datos", Toast.LENGTH_SHORT).show()
            }
        )
    }

    private fun cancelarFormulario() {
        etNumeroSerie.text.clear()
        etTipoEquipo.text.clear()
        etModelo.text.clear()
        etFechaAdquisicion.text = ""
        etFechaCertificacion.text = ""
        etVigencia.text.clear()

        Toast.makeText(requireContext(), "Datos no guardados", Toast.LENGTH_SHORT).show()
    }

    private fun showDatePickerDialog(onDateSelected: (String) -> Unit) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
            val fechaSeleccionada = "$selectedDay/${selectedMonth + 1}/$selectedYear"
            onDateSelected(fechaSeleccionada)
        }, year, month, day).show()
    }

    companion object {
        private const val ARG_TIPO_GESTION = "tipo_gestion"

        fun newInstance(tipoGestion: String): ge_crearFragment {
            val fragment = ge_crearFragment()
            val args = Bundle()
            args.putString(ARG_TIPO_GESTION, tipoGestion)
            fragment.arguments = args
            return fragment
        }
    }
}