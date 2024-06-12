package com.example.proyectoriojas.Venta

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

class EditarVentaActivity : AppCompatActivity() {
    private lateinit var editTextCantidadVendida: EditText
    private lateinit var editTextFecha: EditText
    private lateinit var editTextNombre: EditText
    private lateinit var editTextPrecioUnitario: EditText
    private lateinit var editTextTotal: EditText
    private lateinit var buttonGuardar: Button
    private lateinit var buttonCancelar: Button

    private var precioUnitario: Int = 0

    private val apiService = RetrofitClient.apiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.editarventa)

        editTextCantidadVendida = findViewById(R.id.editTextCantidad)
        editTextFecha = findViewById(R.id.editTextFecha)
        editTextNombre = findViewById(R.id.editTextNombreVenta)
        editTextPrecioUnitario = findViewById(R.id.editTextPrecio)
        editTextTotal = findViewById(R.id.editTextTotal)
        buttonGuardar = findViewById(R.id.buttonGuardar)
        buttonCancelar = findViewById(R.id.buttonCancelar)

        val idVenta = intent.getIntExtra("idVenta", -1)
        val cantidadVendida = intent.getIntExtra("CANTIDAD", 0)
        val fecha = intent.getStringExtra("FECHA")
        val nombre = intent.getStringExtra("NOMBRE")
        precioUnitario = intent.getIntExtra("PRECIOUNITARIO", 0)

        editTextCantidadVendida.setText(cantidadVendida.toString())
        editTextFecha.setText(fecha)
        editTextNombre.setText(nombre)
        editTextPrecioUnitario.setText(precioUnitario.toString())
        editTextTotal.setText((cantidadVendida * precioUnitario).toString())

        val textWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                calcularTotal()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }

        editTextCantidadVendida.addTextChangedListener(textWatcher)
        editTextPrecioUnitario.addTextChangedListener(textWatcher)

        buttonGuardar.setOnClickListener {
            guardarCambios(idVenta)
        }

        buttonCancelar.setOnClickListener {
            finish()
        }
    }

    private fun calcularTotal() {
        val cantidadVendida = editTextCantidadVendida.text.toString().toIntOrNull() ?: 0
        val precioUnitarioInput = editTextPrecioUnitario.text.toString().toIntOrNull() ?: 0

        precioUnitario = precioUnitarioInput

        val total = cantidadVendida * precioUnitario.toDouble()
        editTextTotal.setText(total.toString())
    }

    private fun guardarCambios(idVenta: Int) {
        val cantidadVendida = editTextCantidadVendida.text.toString().toInt()
        val fecha = editTextFecha.text.toString()
        val nombre = editTextNombre.text.toString()

        apiService.actualizarVenta(idVenta, cantidadVendida, fecha, nombre, precioUnitario, (cantidadVendida * precioUnitario).toDouble())
            .enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@EditarVentaActivity, "Datos de la venta actualizados", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(this@EditarVentaActivity, "Error al actualizar los datos de la venta", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Toast.makeText(this@EditarVentaActivity, "Error al conectar con el servidor", Toast.LENGTH_SHORT).show()
                }
            })
    }
}
