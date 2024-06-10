package com.example.proyectoriojas.Usuario

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectoriojas.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditarUsuarioActivity : AppCompatActivity() {
    private lateinit var editTextNombre: EditText
    private lateinit var editTextCorreo: EditText
    private lateinit var buttonGuardar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.editarusuario)

        editTextNombre = findViewById(R.id.editTextNuevoNombre)
        editTextCorreo = findViewById(R.id.editTextNuevoCorreo)
        buttonGuardar = findViewById(R.id.buttonGuardarCambios)

        // Obtener datos pasados desde la actividad anterior
        val idUsuario = intent.getIntExtra("idUsuario", -1)
        val nombre = intent.getStringExtra("NOMBRE")
        val correo = intent.getStringExtra("CORREO")

        // Mostrar los datos del usuario en los campos de edición
        editTextNombre.setText(nombre)
        editTextCorreo.setText(correo)

        buttonGuardar.setOnClickListener {
            guardarCambios(idUsuario)
        }
    }

    private fun guardarCambios(idUsuario: Int) {
        val nombre = editTextNombre.text.toString()
        val correo = editTextCorreo.text.toString()

        val call = RetrofitClient.apiService.actualizarUsuario(idUsuario, nombre, correo)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@EditarUsuarioActivity, "Datos actualizados", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this@EditarUsuarioActivity, "Error al actualizar los datos", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@EditarUsuarioActivity, "Error al conectar con el servidor", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
