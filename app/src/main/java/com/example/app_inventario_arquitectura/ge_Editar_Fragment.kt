package com.example.app_inventario_arquitectura

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.androidmaster.R
import java.util.Calendar

class ge_Editar_Fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.ge_editar_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tipoGestion = arguments?.getString(ARG_TIPO_GESTION)
        // Cambia el título o contenido según el tipo de gestión
        val tituloTextView: TextView = view.findViewById(R.id.edit_modif_gestion)
        tituloTextView.text = "Editar o Modificar $tipoGestion"

        // Inicializa los elementos y asigna los listeners para seleccionar la fecha
        val etFechaAdquisicion: EditText = view.findViewById(R.id.etFechaAdquisicion)
        val etFechaCertificacion: EditText = view.findViewById(R.id.etFechaCertificacion)
        val btnFechaAdquisicion: Button = view.findViewById(R.id.btnFechaAdquisicion)
        val btnFechaCertificacion: Button = view.findViewById(R.id.btnFechaCertificacion)

        // Muestra el DatePickerDialog y establece la fecha en el EditText correspondiente
        btnFechaAdquisicion.setOnClickListener {
            showDatePickerDialog { date ->
                etFechaAdquisicion.setText(date)
            }
        }

        btnFechaCertificacion.setOnClickListener {
            showDatePickerDialog { date ->
                etFechaCertificacion.setText(date)
            }
        }
    }

    // Función para mostrar el DatePickerDialog
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
