package com.example.app_inventario_arquitectura

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.androidmaster.R
import java.util.*

class me_crear_Fragment : Fragment(R.layout.me_crear_fragment) {

    private var tipoGestion: String? = null
    private lateinit var tvAutoID: TextView
    private lateinit var etTipoEquipo: EditText
    private lateinit var etNombreObra: EditText
    private lateinit var etFechaSalida: EditText
    private lateinit var etFechaIngreso: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            tipoGestion = it.getString(ARG_TIPO_GESTION)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.me_crear_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Asignar ID consecutivo al TextView
        tvAutoID = view.findViewById(R.id.tvAutoID)
        tvAutoID.text = "ID: ${getNextID()}"

        val tituloTextView: TextView = view.findViewById(R.id.crear_me)
        tituloTextView.text = "Crear $tipoGestion"

        // Resto de las referencias y configuraciones de botones
        val etNumeroSerie: EditText = view.findViewById(R.id.etNumeroSerie)
        val etCodigoObra: EditText = view.findViewById(R.id.etCodigoObra)
        etTipoEquipo = view.findViewById(R.id.etTipoEquipo)
        etNombreObra = view.findViewById(R.id.etNombreObra)
        etFechaSalida = view.findViewById(R.id.etFechaSalida)
        etFechaIngreso = view.findViewById(R.id.etFechaIngreso)

        // Botón Buscar
        val btnBuscar: Button = view.findViewById(R.id.btnBuscar)
        btnBuscar.setOnClickListener {
            val numeroSerie = etNumeroSerie.text.toString()
            val codigoObra = etCodigoObra.text.toString()
            fetchEquipoData(numeroSerie, codigoObra)
        }

        // Botones para seleccionar fechas
        val btnFechaSalida: Button = view.findViewById(R.id.btnFechaSalida)
        btnFechaSalida.setOnClickListener {
            showDatePickerDialog { selectedDate ->
                etFechaSalida.setText(selectedDate)
            }
        }

        val btnFechaIngreso: Button = view.findViewById(R.id.btnFechaIngreso)
        btnFechaIngreso.setOnClickListener {
            showDatePickerDialog { selectedDate ->
                etFechaIngreso.setText(selectedDate)
            }
        }
    }

    // Genera el próximo ID consecutivo y lo almacena
    private fun getNextID(): Int {
        val sharedPreferences = requireContext().getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val currentID = sharedPreferences.getInt("last_id", 0) + 1
        sharedPreferences.edit().putInt("last_id", currentID).apply()
        return currentID
    }

    private fun showDatePickerDialog(onDateSelected: (String) -> Unit) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
            val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
            onDateSelected(selectedDate)
        }, year, month, day)

        datePickerDialog.show()
    }

    private fun fetchEquipoData(numeroSerie: String, codigoObra: String) {
        etTipoEquipo.setText("Tipo de equipo de prueba")
        etNombreObra.setText("Nombre de obra de prueba")
    }

    companion object {
        private const val ARG_TIPO_GESTION = "tipo_gestion"

        fun newInstance(tipoGestion: String): me_crear_Fragment {
            val fragment = me_crear_Fragment()
            val args = Bundle()
            args.putString(ARG_TIPO_GESTION, tipoGestion)
            fragment.arguments = args
            return fragment
        }
    }
}
