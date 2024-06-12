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

class EditarProductoActivity : AppCompatActivity() {
    private lateinit var editTextNombre: EditText
    private lateinit var editTextPrecio: EditText
    private lateinit var editTextCantidad: EditText
    private lateinit var buttonGuardar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.editarproducto)

        editTextNombre = findViewById(R.id.editTextNombreProducto)
        editTextPrecio = findViewById(R.id.editTextPrecioProducto)
        editTextCantidad = findViewById(R.id.editTextCantidadProducto)
        buttonGuardar = findViewById(R.id.buttonActualizarProducto)

        val idProducto = intent.getIntExtra("idProducto", -1)
        val nombre = intent.getStringExtra("nombre")
        val precio = intent.getDoubleExtra("precio", 0.0)
        val cantidad = intent.getIntExtra("cantidad", 0)
        editTextNombre.setText(nombre)
        editTextPrecio.setText(precio.toString())
        editTextCantidad.setText(cantidad.toString())
        buttonGuardar.setOnClickListener {
            guardarCambios(idProducto)
        }
    }

    private fun guardarCambios(idProducto: Int) {
        val nombre = editTextNombre.text.toString()
        val precio = editTextPrecio.text.toString().toDouble()
        val cantidad = editTextCantidad.text.toString().toInt()

        val call = RetrofitClient.apiService.editarProducto(idProducto, nombre, precio, cantidad)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(
                        this@EditarProductoActivity,
                        "Datos actualizados",
                        Toast.LENGTH_SHORT
                    ).show()
                    setResult(RESULT_OK)
                    finish()
                } else {
                    Toast.makeText(
                        this@EditarProductoActivity,
                        "Error al actualizar los datos",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(
                    this@EditarProductoActivity,
                    "Error al conectar con el servidor",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}
