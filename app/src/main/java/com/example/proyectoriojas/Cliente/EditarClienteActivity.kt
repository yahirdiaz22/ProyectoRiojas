package com.example.proyectoriojas.Cliente

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectoriojas.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditarClienteActivity : AppCompatActivity() {
    private lateinit var editTextNombre: EditText
    private lateinit var editTextDireccion: EditText
    private lateinit var editTextCorreo: EditText
    private lateinit var editTextTelefono: EditText
    private lateinit var buttonGuardar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.editarcliente)

        editTextNombre = findViewById(R.id.editTextNombre)
        editTextDireccion = findViewById(R.id.editTextDireccion)
        editTextCorreo = findViewById(R.id.editTextCorreo)
        editTextTelefono = findViewById(R.id.editTextTelefono)
        buttonGuardar = findViewById(R.id.buttonActualizar)

        val idCliente = intent.getIntExtra("ID_CLIENTE", -1)
        val nombre = intent.getStringExtra("NOMBRE")
        val direccion = intent.getStringExtra("DIRECCION")
        val correo = intent.getStringExtra("CORREO")
        val telefono = intent.getStringExtra("TELEFONO")

        editTextNombre.setText(nombre)
        editTextDireccion.setText(direccion)
        editTextCorreo.setText(correo)
        editTextTelefono.setText(telefono)

        buttonGuardar.setOnClickListener {
            guardarCambios(idCliente)
        }
    }

    private fun guardarCambios(idCliente: Int) {
        val nombre = editTextNombre.text.toString()
        val direccion = editTextDireccion.text.toString()
        val correo = editTextCorreo.text.toString()
        val telefono = editTextTelefono.text.toString()

        val call = RetrofitClient.apiService.actualizarCliente(idCliente, nombre, direccion, correo, telefono)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@EditarClienteActivity, "Datos actualizados", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this@EditarClienteActivity, "Error al actualizar los datos", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@EditarClienteActivity, "Error al conectar con el servidor", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
