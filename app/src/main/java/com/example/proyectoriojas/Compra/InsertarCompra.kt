package com.example.proyectoriojas
import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectoriojas.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class InsertarCompra : AppCompatActivity() {

    private lateinit var editTextCantidad: EditText
    private lateinit var editTextFecha: EditText
    private lateinit var editTextPrecio: EditText
    private lateinit var editText: EditText
    private lateinit var buttonInsertar: Button
    private lateinit var buttonRegresar: Button

    private val apiService = RetrofitClient.apiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.insertarcompra)

        editTextCantidad = findViewById(R.id.editTextCantidadComprada)
        editTextFecha = findViewById(R.id.editTextFecha)
        editTextPrecio = findViewById(R.id.editTextPrecioUnitario)
        editText = findViewById(R.id.editTextNombre)
        buttonInsertar = findViewById(R.id.buttonInsertar)
        buttonRegresar = findViewById(R.id.buttonRegresar)

        editTextFecha.setOnClickListener {
            mostrarDatePickerDialog()
        }

        buttonInsertar.setOnClickListener {
            val cantidad = editTextCantidad.text.toString().toInt()
            val fecha = editTextFecha.text.toString()
            val nombre = editText.text.toString()
            val precio = editTextPrecio.text.toString().toDouble()

            insertarNuevaCompra(cantidad, fecha, precio, nombre)
        }

        buttonRegresar.setOnClickListener {
            finish()
        }
    }

    private fun mostrarDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(Calendar.YEAR, year)
                selectedDate.set(Calendar.MONTH, monthOfYear)
                selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                editTextFecha.setText(dateFormat.format(selectedDate.time))
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }

    private fun insertarNuevaCompra(cantidad: Int, fecha: String, precio: Double, nombre: String) {
        apiService.agregarCompra(cantidad, fecha, precio,nombre)
            .enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        mostrarMensaje("Compra agregada exitosamente")
                    } else {
                        mostrarMensaje("Error al agregar compra: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    mostrarMensaje("Error al agregar compra: ${t.message}")
                }
            })
    }

    private fun mostrarMensaje(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }
}
