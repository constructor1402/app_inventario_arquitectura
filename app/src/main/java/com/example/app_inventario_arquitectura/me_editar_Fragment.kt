package com.example.app_inventario_arquitectura

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.androidmaster.R

// TODO: Rename parameter arguments, choose names that match

class me_editar_Fragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.me_editar_fragment, container, false)
    }

    companion object {
        private const val ARG_TIPO_GESTION = "tipo_gestion"

        fun newInstance(tipoGestion: String): me_editar_Fragment {
            val fragment = me_editar_Fragment()
            val args = Bundle()
            args.putString(ARG_TIPO_GESTION, tipoGestion)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tipoGestion = arguments?.getString(ARG_TIPO_GESTION)
        // Cambia el título o contenido según el tipo de gestión
        val tituloTextView: TextView = view.findViewById(R.id.me_editar)
        tituloTextView.text = "Editar o Modificar $tipoGestion"
    }

}