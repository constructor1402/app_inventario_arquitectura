package com.example.app_inventario_arquitectura

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.commit
import androidx.navigation.fragment.findNavController
import com.example.androidmaster.R
import com.google.firebase.auth.FirebaseAuth

class IngresoFragment : Fragment() {

    private var firebaseAuth: FirebaseAuth?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_ingreso, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firebaseAuth = FirebaseAuth.getInstance()

        // Configurar el clic del botón para navegar al panel de control
        val botonIngresar: Button = view.findViewById(R.id.btnIngresar)
        botonIngresar.setOnClickListener {
            val textUsuario = view.findViewById<EditText>(R.id.editTextUsuario)
            val textPassword = view.findViewById<EditText>(R.id.editTextPassword)
            val email = textUsuario.text.toString().trim()
            val password = textPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                showToast("El usuario y la contraseña no pueden estar vacíos")
            } else {
                autenticarUsuario(email, password)
            }
        }
    }

    // Función para autenticar al usuario
    private fun autenticarUsuario(email: String, password: String) {
        firebaseAuth?.signInWithEmailAndPassword(email, password)
            ?.addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Autenticación exitosa, navega al panel de control
                    parentFragmentManager.commit {
                        replace(R.id.fragmentContainer, panelDeControlFragment())
                        addToBackStack(null)
                    }
                }
            }?.addOnFailureListener{ e->
                showToast("No se pudo iniciar sesion debido a ${e.message}")

            }
    }

    // Función para mostrar mensajes en Toast
    private fun showToast(mensaje: String) {
        Toast.makeText(requireContext(), mensaje, Toast.LENGTH_LONG).show()
    }
}
