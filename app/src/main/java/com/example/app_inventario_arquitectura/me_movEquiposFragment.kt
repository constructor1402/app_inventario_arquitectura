package com.example.app_inventario_arquitectura

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import com.example.androidmaster.R
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView


class me_movEquiposFragment : Fragment(R.layout.me_mov_equipos_fragment) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configura la toolbar con un botón de navegación de regreso
        val toolbar = view.findViewById<MaterialToolbar>(R.id.topAppBar_mov_equipos)
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

        // Ajusta según el tipo de gestión
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.ic_crear -> {
                    cargarFragmento(me_crear_Fragment.newInstance(""))
                    true
                }
                R.id.ic_editar -> {
                    cargarFragmento(me_editar_Fragment.newInstance(""))
                    true
                }
                else -> false
            }
        }
        bottomNavigationView.selectedItemId = R.id.ic_crear


    }

    private fun cargarFragmento(fragment: Fragment) {
        childFragmentManager.beginTransaction()
            .replace(R.id.me_contenedor_fragment, fragment)
            .commit()
    }







}