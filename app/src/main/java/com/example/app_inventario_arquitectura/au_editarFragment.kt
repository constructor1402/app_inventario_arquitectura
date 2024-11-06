package com.example.app_inventario_arquitectura

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.androidmaster.R
import com.google.firebase.firestore.FirebaseFirestore

class au_editarFragment : Fragment() {

    private lateinit var etCorreo: EditText
    private lateinit var etIdentificacion: EditText
    private lateinit var etNombre: EditText
    private lateinit var etApellidos: EditText
    private lateinit var etTipoUsuario: EditText
    private lateinit var etCargo: EditText
    private lateinit var etContrasena: EditText
    private lateinit var btnBuscar: Button
    private lateinit var btnGuardar: Button
    private lateinit var btnCancelar: Button
    private lateinit var btnEliminar: Button

    private val firestore = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.au_editar_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicialización de vistas
        etCorreo = view.findViewById(R.id.etCorreo)
        etIdentificacion = view.findViewById(R.id.etIdentificacion)
        etNombre = view.findViewById(R.id.etNombre)
        etApellidos = view.findViewById(R.id.etApellidos)
        etTipoUsuario = view.findViewById(R.id.etTipoUsuario)
        etCargo = view.findViewById(R.id.etCargo)
        etContrasena = view.findViewById(R.id.etContrasena)
        btnBuscar = view.findViewById(R.id.btnBuscar)
        btnGuardar = view.findViewById(R.id.btnGuardar)
        btnCancelar = view.findViewById(R.id.btnCancelarEditar)
        btnEliminar = view.findViewById(R.id.btnEliminar)

        // Deshabilitar campos de edición inicialmente
        setFieldsEnabled(false)

        btnBuscar.setOnClickListener {
            val correo = etCorreo.text.toString().trim()
            if (correo.isNotEmpty()) {
                buscarUsuarioPorCorreo(correo)
            } else {
                Toast.makeText(requireContext(), "Ingrese un correo electrónico para buscar", Toast.LENGTH_SHORT).show()
            }
        }

        btnGuardar.setOnClickListener {
            validarCampos()
        }

        btnCancelar.setOnClickListener {
            cancelarEdicion()
        }

        btnEliminar.setOnClickListener {
            eliminarUsuario()
        }
    }

    private fun setFieldsEnabled(enabled: Boolean) {
        etIdentificacion.isEnabled = enabled
        etNombre.isEnabled = enabled
        etApellidos.isEnabled = enabled
        etTipoUsuario.isEnabled = enabled
        etCargo.isEnabled = enabled
        etContrasena.isEnabled = enabled
        btnGuardar.isEnabled = enabled
    }

    private fun buscarUsuarioPorCorreo(correo: String) {
        firestore.collection("usuarios")
            .whereEqualTo("correo", correo)
            .get()
            .addOnSuccessListener { documents ->
                if (documents.isEmpty) {
                    Toast.makeText(context, "Usuario no encontrado", Toast.LENGTH_SHORT).show()
                } else {
                    for (document in documents) {
                        etIdentificacion.setText(document.getString("identificacion"))
                        etNombre.setText(document.getString("nombre"))
                        etApellidos.setText(document.getString("apellidos"))
                        etTipoUsuario.setText(document.getString("tipoUsuario"))
                        etCargo.setText(document.getString("cargo"))
                        etContrasena.setText(document.getString("contrasena"))

                        // Habilitar campos para edición
                        setFieldsEnabled(true)
                    }
                }
            }
            .addOnFailureListener {
                Toast.makeText(context, "Error al buscar usuario", Toast.LENGTH_SHORT).show()
            }
    }

    private fun validarCampos() {
        val identificacion = etIdentificacion.text.toString().trim()
        val nombre = etNombre.text.toString().trim()
        val apellidos = etApellidos.text.toString().trim()
        val tipoUsuario = etTipoUsuario.text.toString().trim()
        val cargo = etCargo.text.toString().trim()
        val contrasena = etContrasena.text.toString().trim()

        if (identificacion.isEmpty() || nombre.isEmpty() || apellidos.isEmpty() || tipoUsuario.isEmpty() || cargo.isEmpty() || contrasena.isEmpty()) {
            Toast.makeText(requireContext(), "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
        } else {
            guardarUsuario(identificacion, nombre, apellidos, tipoUsuario, cargo, contrasena)
        }
    }

    private fun guardarUsuario(
        identificacion: String,
        nombre: String,
        apellidos: String,
        tipoUsuario: String,
        cargo: String,
        contrasena: String
    ) {
        val usuario = hashMapOf(
            "identificacion" to identificacion,
            "nombre" to nombre,
            "apellidos" to apellidos,
            "tipoUsuario" to tipoUsuario,
            "cargo" to cargo,
            "contrasena" to contrasena
        )

        firestore.collection("usuarios")
            .document(etCorreo.text.toString().trim())
            .set(usuario)
            .addOnSuccessListener {
                Toast.makeText(context, "Datos actualizados correctamente", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(context, "Error al actualizar datos", Toast.LENGTH_SHORT).show()
            }
    }

    private fun eliminarUsuario() {
        val correo = etCorreo.text.toString().trim()
        if (correo.isNotEmpty()) {
            firestore.collection("usuarios")
                .document(correo)
                .delete()
                .addOnSuccessListener {
                    Toast.makeText(context, "Usuario eliminado correctamente", Toast.LENGTH_SHORT).show()
                    cancelarEdicion()
                }
                .addOnFailureListener {
                    Toast.makeText(context, "Error al eliminar usuario", Toast.LENGTH_SHORT).show()
                }
        } else {
            Toast.makeText(requireContext(), "Ingrese el correo del usuario a eliminar", Toast.LENGTH_SHORT).show()
        }
    }

    private fun cancelarEdicion() {
        etCorreo.text.clear()
        etIdentificacion.text.clear()
        etNombre.text.clear()
        etApellidos.text.clear()
        etTipoUsuario.text.clear()
        etCargo.text.clear()
        etContrasena.text.clear()
        setFieldsEnabled(false)

        Toast.makeText(requireContext(), "Edición cancelada", Toast.LENGTH_SHORT).show()
    }

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
