package com.example.app_inventario_arquitectura

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.androidmaster.R

class ge_crear_Fragment : Fragment() {

    private var tipoGestion: String? = null

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
        return inflater.inflate(R.layout.ge_crear_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tituloTextView: TextView = view.findViewById(R.id.crear_gestion)
        tituloTextView.text = "Crear $tipoGestion"
    }

    companion object {
        private const val ARG_TIPO_GESTION = "tipo_gestion"

        fun newInstance(tipoGestion: String): ge_crear_Fragment {
            val fragment = ge_crear_Fragment()
            val args = Bundle()
            args.putString(ARG_TIPO_GESTION, tipoGestion)
            fragment.arguments = args
            return fragment
        }
    }
}
