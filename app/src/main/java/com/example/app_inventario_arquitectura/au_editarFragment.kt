package com.example.app_inventario_arquitectura

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.androidmaster.R


class au_editarFragment : Fragment() {


    companion object {
        private const val ARG_TIPO_GESTION = "tipo_gestion"

        fun newInstance(tipoGestion: String): au_editarFragment {
            val fragment = au_editarFragment()
            val args = Bundle()
            args.putString(ARG_TIPO_GESTION, tipoGestion)
            fragment.arguments = args
            return fragment
        }
    }



}