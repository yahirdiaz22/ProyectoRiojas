package com.example.proyectoriojas.Venta

import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
    private lateinit var editTextPrecioUnitario: EditText
    private lateinit var editTextTotal: EditText
    private lateinit var editTextFecha: EditText
    private lateinit var editTextNombre: EditText
    private lateinit var buttonInsertar: Button
    private lateinit var buttonRegresar: Button

    private val apiService = RetrofitClient.apiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.insertarventa)

        editTextCantidadVendida = findViewById(R.id.editTextCantidadVenta)
        editTextPrecioUnitario = findViewById(R.id.editTextPrecioUnitario)
        editTextTotal = findViewById(R.id.editTextTotal)
        editTextFecha = findViewById(R.id.editTextFechaVenta)
        editTextNombre = findViewById(R.id.editTextNombreVenta)
        buttonInsertar = findViewById(R.id.buttonInsertarVenta)
        buttonRegresar = findViewById(R.id.buttonRegresar)

        val textWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                calcularTotal()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }

        editTextCantidadVendida.addTextChangedListener(textWatcher)
        editTextPrecioUnitario.addTextChangedListener(textWatcher)

        buttonInsertar.setOnClickListener {
            val cantidadVendida = editTextCantidadVendida.text.toString().toIntOrNull()
            val fecha = editTextFecha.text.toString()
            val nombre = editTextNombre.text.toString()
            val precioUnitario = editTextPrecioUnitario.text.toString().toIntOrNull()

            if (cantidadVendida != null && fecha.isNotEmpty() && nombre.isNotEmpty() && precioUnitario != null) {
                insertarNuevaVenta(cantidadVendida, fecha, nombre, precioUnitario)
            } else {
                mostrarMensaje("Por favor completa todos los campos")
            }
        }

        buttonRegresar.setOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }
    }

    private fun calcularTotal() {
        val cantidadVendida = editTextCantidadVendida.text.toString().toIntOrNull()
        val precioUnitario = editTextPrecioUnitario.text.toString().toIntOrNull()

        if (cantidadVendida != null && precioUnitario != null) {
            val total = cantidadVendida * precioUnitario
            editTextTotal.setText(total.toString())
        } else {
            editTextTotal.setText("")
        }
    }

    private fun insertarNuevaVenta(cantidadVendida: Int, fecha: String, nombre: String, precioUnitario: Int) {
        val total = (cantidadVendida * precioUnitario).toDouble()
        val status = true

        apiService.agregarVenta(cantidadVendida, fecha, nombre, precioUnitario, total, status)
            .enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        mostrarMensaje("Venta agregada exitosamente")
                        setResult(Activity.RESULT_OK)
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
