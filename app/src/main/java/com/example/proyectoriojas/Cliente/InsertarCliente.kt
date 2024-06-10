package com.example.proyectoriojas

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectoriojas.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InsertarCliente : AppCompatActivity() {

    private lateinit var editTextNombre: EditText
    private lateinit var editTextDireccion: EditText
    private lateinit var editTextCorreo: EditText
    private lateinit var editTextTelefono: EditText
    private lateinit var buttonInsertar: Button
    private lateinit var buttonRegresar: Button

    private val apiService = RetrofitClient.apiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.insertarcliente)

        editTextNombre = findViewById(R.id.editTextNombre)
        editTextDireccion = findViewById(R.id.editTextDireccion)
        editTextCorreo = findViewById(R.id.editTextCorreo)
        editTextTelefono = findViewById(R.id.editTextTelefono)
        buttonInsertar = findViewById(R.id.buttonInsertar)
        buttonRegresar = findViewById(R.id.buttonRegresar)

        buttonInsertar.setOnClickListener {
            val nombre = editTextNombre.text.toString()
            val direccion = editTextDireccion.text.toString()
            val correoElectronico = editTextCorreo.text.toString()
            val telefono = editTextTelefono.text.toString()

            insertarNuevoCliente(nombre, direccion, correoElectronico, telefono)
        }

        buttonRegresar.setOnClickListener {
            finish()
        }
    }

    private fun insertarNuevoCliente(nombre: String, direccion: String, correoElectronico: String, telefono: String) {
        apiService.agregarCliente(nombre, direccion, correoElectronico, telefono)
            .enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        mostrarMensaje("Cliente agregado exitosamente")
                    } else {
                        mostrarMensaje("Error al agregar cliente: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    mostrarMensaje("Error al agregar cliente: ${t.message}")
                }
            })
    }

    private fun mostrarMensaje(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }
}
