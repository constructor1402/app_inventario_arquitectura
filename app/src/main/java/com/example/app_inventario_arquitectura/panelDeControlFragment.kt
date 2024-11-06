package com.example.app_inventario_arquitectura

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import com.example.androidmaster.R
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton

class panelDeControlFragment : Fragment(R.layout.fragment_panel_de_control) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configura la toolbar con un botón de navegación de regreso
        val toolbar = view.findViewById<MaterialToolbar>(R.id.topAppBar_panel)
        toolbar.setNavigationIcon(R.drawable.ic_back)
        toolbar.navigationIcon?.setTint(resources.getColor(R.color.colortexto, null))

        // Configura el botón de retroceso para regresar al fragmento de ingreso
        toolbar.setNavigationOnClickListener {
            parentFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            parentFragmentManager.commit {
                replace(R.id.fragmentContainer, IngresoFragment())
            }
        }

        // Configuración de los MaterialButtons en lugar de CardView
        val btnGestionEquipos: MaterialButton = view.findViewById(R.id.btnGestionEquipos)
        val btnGestionObras: MaterialButton = view.findViewById(R.id.btnGestionObras)
        val btnControlEntradasSalidas: MaterialButton = view.findViewById(R.id.btnControlEntradasSalidas)
        val btnAdminUsuarios: MaterialButton = view.findViewById(R.id.btnAdminUsuarios)

        // Navegación para cada botón
        btnGestionEquipos.setOnClickListener {
            parentFragmentManager.commit {
                replace(R.id.fragmentContainer, ge_equiposFragment())
                addToBackStack(null)
            }
        }

        btnGestionObras.setOnClickListener {
            parentFragmentManager.commit {
                replace(R.id.fragmentContainer, go_obras_Fragment())
                addToBackStack(null)
            }
        }

        btnControlEntradasSalidas.setOnClickListener {
            parentFragmentManager.commit {
                replace(R.id.fragmentContainer, me_movEquiposFragment())
                addToBackStack(null)
            }
        }

        /*btnAdminUsuarios.setOnClickListener {
            parentFragmentManager.commit {
                replace(R.id.fragmentContainer, AdminUsuariosFragment())
                addToBackStack(null)
            }
        }*/
    }
}
