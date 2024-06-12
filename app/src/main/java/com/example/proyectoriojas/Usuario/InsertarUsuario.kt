package com.example.proyectoriojas.Usuario

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectoriojas.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InsertarUsuario : AppCompatActivity() {

    private lateinit var editTextNombre: EditText
    private lateinit var editTextCorreo: EditText
    private lateinit var editTextContraseña: EditText
    private lateinit var buttonInsertar: Button
    private lateinit var buttonRegresar: Button

    private val apiService = RetrofitClient.apiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.insertarusuario)

        editTextNombre = findViewById(R.id.editTextNombreUsuario)
        editTextCorreo = findViewById(R.id.editTextCorreoUsuario)
        editTextContraseña = findViewById(R.id.editTextContraseña)
        buttonInsertar = findViewById(R.id.buttonRegistrar)
        buttonRegresar = findViewById(R.id.buttonRegresar)

        buttonInsertar.setOnClickListener {
            val nombre = editTextNombre.text.toString()
            val correoElectronico = editTextCorreo.text.toString()
            val password = editTextContraseña.text.toString()

            verificarCorreoYAgregarUsuario(nombre, correoElectronico, password)
        }

        buttonRegresar.setOnClickListener {
            finish()
        }
    }

    private fun verificarCorreoYAgregarUsuario(nombre: String, correoElectronico: String, password: String) {
        apiService.verificarCorreoExistente(correoElectronico)
            .enqueue(object : Callback<Boolean> {
                override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                    if (response.isSuccessful) {
                        val correoEnUso = response.body() ?: false
                        if (correoEnUso) {
                            mostrarMensaje("Error: El correo electrónico ya está en uso")
                        } else {
                            insertarNuevoUsuario(nombre, correoElectronico, password)
                        }
                    } else {
                        mostrarMensaje("Error al verificar correo: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<Boolean>, t: Throwable) {
                    mostrarMensaje("Error al verificar correo: ${t.message}")
                }
            })
    }

    private fun insertarNuevoUsuario(nombre: String, correoElectronico: String, password: String) {
        apiService.agregarUsuario(nombre, correoElectronico, password)
            .enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        mostrarMensaje("Usuario agregado exitosamente")
                    } else {
                        mostrarMensaje("Error al agregar usuario: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    mostrarMensaje("Error al agregar usuario: ${t.message}")
                }
            })
    }

    private fun mostrarMensaje(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }
}
