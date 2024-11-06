package com.example.app_inventario_arquitectura

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import com.example.androidmaster.R
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView

class au_AdministradorUsuariosFragment : Fragment(R.layout.au_administrador_usuarios_fragment) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configura la toolbar con un botón de navegación de regreso
        val toolbar = view.findViewById<MaterialToolbar>(R.id.topAppBar_adminisuser)
        toolbar.setNavigationIcon(R.drawable.ic_back)
        toolbar.navigationIcon?.setTint(resources.getColor(R.color.colortexto, null))

        // Configura el botón de retroceso para regresar al panel de control
        toolbar.setNavigationOnClickListener {
            parentFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            parentFragmentManager.commit {
                replace(R.id.fragmentContainer, panelDeControlFragment())
            }
        }

        // Configura el BottomNavigationView para las opciones del administrador de usuarios
        val bottomNavigationView = view.findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        // Ajusta la navegación según la opción seleccionada
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.ic_crear -> {
                    cargarFragmento(au_crearFragment.newInstance(""))
                    true
                }
                R.id.ic_editar -> {
                    cargarFragmento(au_editarFragment.newInstance(""))
                    true
                }
                else -> false
            }
        }
        // Establece "crear" como la opción seleccionada por defecto
        bottomNavigationView.selectedItemId = R.id.ic_crear
    }

    private fun cargarFragmento(fragment: Fragment) {
        childFragmentManager.beginTransaction()
            .replace(R.id.au_contenedor_fragment, fragment)
            .commit()
    }

    companion object {
        fun newInstance() = au_AdministradorUsuariosFragment()
    }
}
