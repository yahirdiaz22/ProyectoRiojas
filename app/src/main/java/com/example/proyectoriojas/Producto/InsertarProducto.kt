package com.example.proyectoriojas.Producto

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectoriojas.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InsertarProducto : AppCompatActivity() {

    private lateinit var editTextNombre: EditText
    private lateinit var editTextPrecio: EditText
    private lateinit var editTextCantidad: EditText
    private lateinit var buttonInsertar: Button
    private lateinit var buttonRegresar: Button

    private val apiService = RetrofitClient.apiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.insertarproducto)

        editTextNombre = findViewById(R.id.editTextNombreProducto)
        editTextPrecio = findViewById(R.id.editTextPrecioProducto)
        editTextCantidad = findViewById(R.id.editTextCantidadProducto)
        buttonInsertar = findViewById(R.id.buttonInsertarProducto)
        buttonRegresar = findViewById(R.id.buttonRegresar)

        buttonInsertar.setOnClickListener {
            val nombre = editTextNombre.text.toString()
            val precioString = editTextPrecio.text.toString()
            val cantidadString = editTextCantidad.text.toString()

            if (nombre.isNotBlank() && precioString.isNotBlank() && cantidadString.isNotBlank()) {
                val precio = precioString.toDoubleOrNull()
                val cantidad = cantidadString.toIntOrNull()

                if (precio != null && cantidad != null) {
                    insertarNuevoProducto(nombre, precio, cantidad)
                } else {
                    mostrarMensaje("Por favor ingrese un precio y cantidad válidos.")
                }
            } else {
                mostrarMensaje("Por favor complete todos los campos.")
            }
        }

        buttonRegresar.setOnClickListener {
            finish()
        }
    }

    private fun insertarNuevoProducto(nombre: String, precio: Double, cantidad: Int) {
        apiService.agregarProducto(nombre, precio, cantidad)
            .enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        mostrarMensaje("Producto agregado exitosamente")
                    } else {
                        mostrarMensaje("Error al agregar producto: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    mostrarMensaje("Error al agregar producto: ${t.message}")
                }
            })
    }

    private fun mostrarMensaje(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }
}
