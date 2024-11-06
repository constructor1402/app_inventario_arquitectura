package com.example.app_inventario_arquitectura

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import com.example.androidmaster.R
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView



class go_obras_Fragment : Fragment(R.layout.go_obras_fragment) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        // Configura la toolbar con un botón de navegación de regreso
        val toolbar = view.findViewById<MaterialToolbar>(R.id.topAppBar_obras)
        toolbar.setNavigationIcon(R.drawable.ic_back)
        toolbar.navigationIcon?.setTint(resources.getColor(R.color.colortexto, null))

        // Configura el botón de retroceso para regresar al fragmento de ingreso
        toolbar.setNavigationOnClickListener {
            parentFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            parentFragmentManager.commit {
                replace(R.id.fragmentContainer, panelDeControlFragment())
            }
        }

        val bottomNavigationView = view.findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.ic_crear -> {
                    cargarFragmento(go_crear_Fragment.newInstance(""))
                    true
                }
                R.id.ic_editar -> {
                    cargarFragmento(go_editar_Fragment.newInstance(""))
                    true
                }

                else -> false
            }
        }
        bottomNavigationView.selectedItemId = R.id.ic_crear
    }

    private fun cargarFragmento(fragment: Fragment) {
        childFragmentManager.beginTransaction()
            .replace(R.id.go_contenedor_fragment, fragment)
            .commit()
    }
}
