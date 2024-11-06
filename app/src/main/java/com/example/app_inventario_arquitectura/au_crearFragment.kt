package com.example.app_inventario_arquitectura

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.androidmaster.R
import com.google.firebase.firestore.FirebaseFirestore

class au_crearFragment : Fragment() {

    private lateinit var etIdentificacion: EditText
    private lateinit var etNombre: EditText
    private lateinit var etApellidos: EditText
    private lateinit var etTipoUsuario: EditText
    private lateinit var etCargo: EditText
    private lateinit var etCorreo: EditText
    private lateinit var etContrasena: EditText
    private lateinit var btnGuardar: Button
    private lateinit var btnCancelar: Button

    private val firestore = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.au_crear_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicializa las vistas
        etIdentificacion = view.findViewById(R.id.etIdentificacion)
        etNombre = view.findViewById(R.id.etNombre)
        etApellidos = view.findViewById(R.id.etApellidos)
        etTipoUsuario = view.findViewById(R.id.etTipoUsuario)
        etCargo = view.findViewById(R.id.etCargo)
        etCorreo = view.findViewById(R.id.etCorreo)
        etContrasena = view.findViewById(R.id.etContrasena)
        btnGuardar = view.findViewById(R.id.btnGuardar)
        btnCancelar = view.findViewById(R.id.btnCancelar)

        btnGuardar.setOnClickListener {
            validarCampos()
        }

        btnCancelar.setOnClickListener {
            cancelarFormulario()
        }
    }

    private fun validarCampos() {
        val identificacion = etIdentificacion.text.toString().trim()
        val nombre = etNombre.text.toString().trim()
        val apellidos = etApellidos.text.toString().trim()
        val tipoUsuario = etTipoUsuario.text.toString().trim()
        val cargo = etCargo.text.toString().trim()
        val correo = etCorreo.text.toString().trim()
        val contrasena = etContrasena.text.toString().trim()

        if (identificacion.isEmpty() || nombre.isEmpty() || apellidos.isEmpty() ||
            tipoUsuario.isEmpty() || cargo.isEmpty() || correo.isEmpty() || contrasena.isEmpty()) {
            Toast.makeText(requireContext(), "Por favor, complete todos los campos.", Toast.LENGTH_SHORT).show()
        } else {
            guardarUsuario(identificacion, nombre, apellidos, tipoUsuario, cargo, correo, contrasena)
        }
    }

    private fun guardarUsuario(
        identificacion: String,
        nombre: String,
        apellidos: String,
        tipoUsuario: String,
        cargo: String,
        correo: String,
        contrasena: String
    ) {
        val usuario = hashMapOf(
            "identificacion" to identificacion,
            "nombre" to nombre,
            "apellidos" to apellidos,
            "tipoUsuario" to tipoUsuario,
            "cargo" to cargo,
            "correo" to correo,
            "contrasena" to contrasena
        )

        firestore.collection("usuarios")
            .add(usuario)
            .addOnSuccessListener {
                Toast.makeText(context, "Usuario guardado correctamente", Toast.LENGTH_SHORT).show()
                cancelarFormulario()
            }
            .addOnFailureListener { e ->
                Toast.makeText(context, "Error al guardar usuario: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun cancelarFormulario() {
        etIdentificacion.text.clear()
        etNombre.text.clear()
        etApellidos.text.clear()
        etTipoUsuario.text.clear()
        etCargo.text.clear()
        etCorreo.text.clear()
        etContrasena.text.clear()

        Toast.makeText(requireContext(), "Formulario cancelado", Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val ARG_TIPO_GESTION = "tipo_gestion"

        fun newInstance(tipoGestion: String): au_crearFragment {
            val fragment = au_crearFragment()
            val args = Bundle()
            args.putString(ARG_TIPO_GESTION, tipoGestion)
            fragment.arguments = args
            return fragment
        }
    }
}
