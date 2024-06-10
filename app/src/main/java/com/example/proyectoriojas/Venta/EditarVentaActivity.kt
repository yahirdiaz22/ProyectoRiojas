package com.example.proyectoriojas.Venta

import android.os.Bundle
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
    private lateinit var buttonGuardar: Button
    private lateinit var buttonCancelar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.editarventa)

        editTextCantidadVendida = findViewById(R.id.editTextCantidad)
        editTextFecha = findViewById(R.id.editTextFecha)
        editTextNombre = findViewById(R.id.editTextNombreVenta)
        buttonGuardar = findViewById(R.id.buttonGuardar)
        buttonCancelar = findViewById(R.id.buttonCancelar)

        // Obtener datos pasados desde la actividad anterior
        val idVenta = intent.getIntExtra("idVenta", -1)
        val cantidadVendida = intent.getIntExtra("CANTIDAD", 0)
        val fecha = intent.getStringExtra("FECHA")
        val nombre = intent.getStringExtra("NOMBRE")

        // Mostrar los datos de la venta en los campos de edición
        editTextCantidadVendida.setText(cantidadVendida.toString())
        editTextFecha.setText(fecha)
        editTextNombre.setText(nombre)

        buttonGuardar.setOnClickListener {
            guardarCambios(idVenta)
        }

        buttonCancelar.setOnClickListener {
            finish()
        }
    }

    private fun guardarCambios(idVenta: Int) {
        val cantidadVendida = editTextCantidadVendida.text.toString().toInt()
        val fecha = editTextFecha.text.toString()
        val nombre = editTextNombre.text.toString()

        val call = RetrofitClient.apiService.actualizarVenta(idVenta, cantidadVendida, fecha,nombre)
        call.enqueue(object : Callback<Void> {
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
