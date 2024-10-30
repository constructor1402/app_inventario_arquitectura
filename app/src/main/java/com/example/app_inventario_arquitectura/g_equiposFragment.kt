package com.example.app_inventario_arquitectura


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.androidmaster.R
import com.google.android.material.bottomnavigation.BottomNavigationView


class g_equiposFragment : Fragment(R.layout.ge_equipos_fragment) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bottomNavigationView = view.findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        // Ajusta según el tipo de gestión
        val tipoGestion = "Equipos"

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.ic_crear -> {

                    cargarFragmento(ge_crear_Fragment.newInstance(""))
                    true

                }
                R.id.ic_editar -> {
                    cargarFragmento(ge_Editar_Fragment.newInstance(""))
                    true
                }

                R.id.ic_eliminar -> {
                    cargarFragmento(ge_Eliminar_Fragment.newInstance(""))
                    true
                }
                else -> false
            }
        }
        bottomNavigationView.selectedItemId = R.id.ic_crear
    }

    private fun cargarFragmento(fragment: Fragment) {
        childFragmentManager.beginTransaction()
            .replace(R.id.contenedor_fragment, fragment)
            .commit()
    }

}