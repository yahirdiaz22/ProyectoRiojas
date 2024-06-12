package com.example.proyectoriojas.Compra

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectoriojas.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CompraEditarActivity : AppCompatActivity() {
    private lateinit var editTextCantidad: EditText
    private lateinit var editTextFecha: EditText
    private lateinit var editTextNombre: EditText
    private lateinit var editTextPrecioUnitario: EditText
    private lateinit var buttonActualizarCompra: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.editarcompra)

        editTextCantidad = findViewById(R.id.editTextCantidadComprada)
        editTextFecha = findViewById(R.id.editTextFecha)
        editTextNombre = findViewById(R.id.editTextNombre)  // Corregido
        editTextPrecioUnitario = findViewById(R.id.editTextPrecioUnitario)
        buttonActualizarCompra = findViewById(R.id.buttonActualizar)

        val idCompra = intent.getIntExtra("ID_COMPRA", -1)
        val cantidadComprada = intent.getIntExtra("CANTIDAD_COMPRADA", 0)
        val fecha = intent.getStringExtra("FECHA")
        val nombre = intent.getStringExtra("NOMBRE")
        val precioUnitario = intent.getIntExtra("PRECIO_UNITARIO", 0)

        editTextCantidad.setText(cantidadComprada.toString())
        editTextFecha.setText(fecha)
        editTextNombre.setText(nombre)  // Corregido
        editTextPrecioUnitario.setText(precioUnitario.toString())

        buttonActualizarCompra.setOnClickListener {
            guardarCambios(idCompra)
        }
    }

    private fun guardarCambios(idCompra: Int) {
        val cantidadComprada = editTextCantidad.text.toString().toInt()
        val fecha = editTextFecha.text.toString()
        val nombre = editTextNombre.text.toString()
        val precioUnitario = editTextPrecioUnitario.text.toString().toInt()

        val call = RetrofitClient.apiService.actualizarCompra(idCompra, cantidadComprada, fecha, precioUnitario, nombre)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@CompraEditarActivity, "Datos de compra actualizados", Toast.LENGTH_SHORT).show()
                    val intent = Intent()
                    setResult(RESULT_OK, intent)
                    finish()
                } else {
                    Toast.makeText(this@CompraEditarActivity, "Error al actualizar los datos de compra", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@CompraEditarActivity, "Error al conectar con el servidor", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
