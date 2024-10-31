package com.example.app_inventario_arquitectura

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.commit
import com.example.androidmaster.R

// TODO: Rename parameter arguments, choose names that match

class panelDeControlFragment : Fragment(R.layout.fragment_panel_de_control) {
    // TODO: Rename and change types of parameters

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cardViewOpcion1: CardView = view.findViewById(R.id.cardGestionEquipos)
        val cardViewOpcion2: CardView = view.findViewById(R.id.cardGestionObras)

        cardViewOpcion1.setOnClickListener{
            parentFragmentManager.commit {
                replace(R.id.fragmentContainer, ge_equiposFragment())
                addToBackStack(null)
            }
        }

        cardViewOpcion2.setOnClickListener{
            parentFragmentManager.commit {
                replace(R.id.fragmentContainer, go_obras_Fragment())
                addToBackStack(null)
            }
        }

    }
}