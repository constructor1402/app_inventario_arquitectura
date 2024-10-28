package com.example.app_inventario_arquitectura

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.commit
import androidx.navigation.fragment.findNavController
import com.example.androidmaster.R

class IngresoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_ingreso, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configurar el clic del botón para navegar al panel de control
        val botonIngresar: Button = view.findViewById(R.id.btnIngresar)
        botonIngresar.setOnClickListener {
            parentFragmentManager.commit {
                replace(R.id.fragmentContainer, panelDeControlFragment())
                addToBackStack(null)
            }
        }
    }
}
