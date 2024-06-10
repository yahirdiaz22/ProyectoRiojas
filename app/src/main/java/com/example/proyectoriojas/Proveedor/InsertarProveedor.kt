package com.example.proyectoriojas.Proveedor

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectoriojas.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InsertarProveedor : AppCompatActivity() {

    private lateinit var editTextNombre: EditText
    private lateinit var editTextDireccion: EditText
    private lateinit var editTextCorreo: EditText
    private lateinit var editTextTelefono: EditText
    private lateinit var buttonInsertar: Button
    private lateinit var buttonRegresar: Button

    private val apiService = RetrofitClient.apiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.insertarproveedor)

        editTextNombre = findViewById(R.id.editTextNombreProveedor)
        editTextDireccion = findViewById(R.id.editTextDireccionProveedor)
        editTextCorreo = findViewById(R.id.editTextCorreoProveedor)
        editTextTelefono = findViewById(R.id.editTextTelefonoProveedor)
        buttonInsertar = findViewById(R.id.buttonInsertarProveedor)
        buttonRegresar = findViewById(R.id.buttonRegresar)

        buttonInsertar.setOnClickListener {
            val nombre = editTextNombre.text.toString()
            val direccion = editTextDireccion.text.toString()
            val correoElectronico = editTextCorreo.text.toString()
            val telefono = editTextTelefono.text.toString()

            insertarNuevoProveedor(nombre, direccion, correoElectronico, telefono)
        }

        buttonRegresar.setOnClickListener {
            finish()
        }
    }

    private fun insertarNuevoProveedor(nombre: String, direccion: String, correoElectronico: String, telefono: String) {
        apiService.agregarProveedor(nombre, direccion, correoElectronico, telefono)
            .enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        mostrarMensaje("Proveedor agregado exitosamente")
                    } else {
                        mostrarMensaje("Error al agregar proveedor: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    mostrarMensaje("Error al agregar proveedor: ${t.message}")
                }
            })
    }

    private fun mostrarMensaje(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }
}
