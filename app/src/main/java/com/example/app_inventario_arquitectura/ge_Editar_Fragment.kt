package com.example.app_inventario_arquitectura

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.androidmaster.R

// TODO: Rename parameter arguments, choose names that match

class ge_Editar_Fragment : Fragment() {
    // TODO: Rename and change types of parameters


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.ge_editar_fragment, container, false)
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tipoGestion = arguments?.getString(ARG_TIPO_GESTION)
        // Cambia el título o contenido según el tipo de gestión
        val tituloTextView: TextView = view.findViewById(R.id.edit_modif_gestion)
        tituloTextView.text = "Editar o Modificar $tipoGestion"
    }

}