package com.example.app_inventario_arquitectura

import android.os.Bundle
import android.view.View
import android.widget.Toast
import android.widget.EditText
import android.content.Context
import android.content.Intent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.view.inputmethod.InputMethodManager

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    fun showToast(mensaje:String){
        Toast.makeText(this,mensaje,Toast.LENGTH_LONG).show()
    }
    fun ingresar(view:View){
        val textUsuario = findViewById<EditText>(R.id.editTextUsuario)
        val textPassword = findViewById<EditText>(R.id.editTextPassword)
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
        val email = textUsuario.text.toString().trim()
        val password = textPassword.text.toString().trim()

        if(email.isEmpty() || password.isEmpty())  {
            showToast("El usuario y la contraseña no pueden estar vacíos")
        } else if  (!isValidEmail(email)) {
            showToast("Correo inválido.")
        } else {
            showToast("Puede Ingresar")
        }
    }
    private fun isValidEmail(email: String): Boolean { return email.contains("@")}
}