package com.example.app_inventario_arquitectura

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.androidmaster.R
import com.google.android.material.snackbar.Snackbar

class ge_crear_Fragment : Fragment() {

    private var tipoGestion: String? = null
    private lateinit var ivImagenEquipo: ImageView
    private lateinit var btnCargarImagen: Button

    // Lanzador para seleccionar imagen desde la galería
    private val selectImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            ivImagenEquipo.setImageURI(it)
        }
    }

    // Lanzador para solicitar permisos de almacenamiento
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            selectImageFromGallery()
        } else {
            // Mostrar Snackbar indicando que el permiso es necesario
            Snackbar.make(requireView(), "Permiso necesario para cargar una imagen", Snackbar.LENGTH_LONG)
                .setAction("Configuración") {
                    // Abre la configuración de la aplicación para conceder el permiso manualmente
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", requireContext().packageName, null)
                    intent.data = uri
                    startActivity(intent)
                }.show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            tipoGestion = it.getString(ARG_TIPO_GESTION)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.ge_crear_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tituloTextView: TextView = view.findViewById(R.id.crear_gestion)
        tituloTextView.text = "Crear $tipoGestion"

        ivImagenEquipo = view.findViewById(R.id.ivImagenEquipo)
        btnCargarImagen = view.findViewById(R.id.btnCargarImagen)

        // Configurar el botón para cargar la imagen
        btnCargarImagen.setOnClickListener {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
                selectImageFromGallery()
            } else {
                requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }
    }

    // Función para abrir la galería
    private fun selectImageFromGallery() {
        selectImageLauncher.launch("image/*")
    }

    companion object {
        private const val ARG_TIPO_GESTION = "tipo_gestion"

        fun newInstance(tipoGestion: String): ge_crear_Fragment {
            val fragment = ge_crear_Fragment()
            val args = Bundle()
            args.putString(ARG_TIPO_GESTION, tipoGestion)
            fragment.arguments = args
            return fragment
        }
    }
}
