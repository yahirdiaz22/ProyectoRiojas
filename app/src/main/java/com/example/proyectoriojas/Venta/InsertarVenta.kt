package com.example.proyectoriojas.Venta

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectoriojas.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InsertarVenta : AppCompatActivity() {

    private lateinit var editTextCantidadVendida: EditText
    private lateinit var editTextFecha: EditText
    private lateinit var editText: EditText
    private lateinit var buttonInsertar: Button
    private lateinit var buttonRegresar: Button

    private val apiService = RetrofitClient.apiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.insertarventa)

        editTextCantidadVendida = findViewById(R.id.editTextCantidadVenta)
        editTextFecha = findViewById(R.id.editTextFechaVenta)
        editText = findViewById(R.id.editTextNombreVenta)
        buttonInsertar = findViewById(R.id.buttonInsertarVenta)
        buttonRegresar = findViewById(R.id.buttonRegresar)

        buttonInsertar.setOnClickListener {
            val cantidadVendida = editTextCantidadVendida.text.toString().toIntOrNull()
            val fecha = editTextFecha.text.toString()
            val nombre = editText.text.toString()

            if (cantidadVendida != null && fecha.isNotEmpty()) {
                insertarNuevaVenta(cantidadVendida, fecha,nombre)
            } else {
                mostrarMensaje("Por favor completa todos los campos")
            }
        }

        buttonRegresar.setOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }
    }

    private fun insertarNuevaVenta(cantidadVendida: Int, fecha: String,nombre:String) {
        apiService.agregarVenta(cantidadVendida, fecha,nombre)
            .enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        mostrarMensaje("Venta agregada exitosamente")
                        setResult(Activity.RESULT_OK) // Informar que la venta se agregó correctamente
                        finish()
                    } else {
                        mostrarMensaje("Error al agregar venta: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    mostrarMensaje("Error al agregar venta: ${t.message}")
                }
            })
    }

    private fun mostrarMensaje(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }
}
