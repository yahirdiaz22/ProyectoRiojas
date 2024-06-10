package com.example.proyectoriojas

import Cliente
import ClienteAdapter
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ClienteActivity : AppCompatActivity() {

    private lateinit var listViewClientes: ListView
    private lateinit var buttonInsertar: Button
    private lateinit var buttonRegresar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mostrarcliente)

        listViewClientes = findViewById(R.id.listViewClientes)
        buttonInsertar = findViewById(R.id.button)
        buttonRegresar = findViewById(R.id.btnRegresar)

        buttonInsertar.setOnClickListener {
            // Abrir la actividad para insertar un nuevo cliente
            val intent = Intent(this, InsertarCliente::class.java)
            startActivity(intent)
        }

        buttonRegresar.setOnClickListener {
            // Finalizar la actividad actual y regresar a la anterior
            finish()
        }

        // Inicializar la carga de clientes
        obtenerClientesDesdeAPI()
    }

    override fun onResume() {
        super.onResume()
        // Volver a cargar los clientes cuando la actividad vuelva a estar en primer plano
        obtenerClientesDesdeAPI()
    }

    private fun obtenerClientesDesdeAPI() {
        val apiService = RetrofitClient.apiService
        apiService.obtenerClientes().enqueue(object : Callback<List<Cliente>> {
            override fun onResponse(call: Call<List<Cliente>>, response: Response<List<Cliente>>) {
                if (response.isSuccessful) {
                    val clientes = response.body() ?: emptyList()
                    val clientesConStatusUno = clientes.filter { it.status == 1 }

                    val adapter = ClienteAdapter(this@ClienteActivity, clientesConStatusUno.toMutableList()) { cliente ->
                        confirmarEliminarCliente(cliente)
                    }

                    listViewClientes.adapter = adapter
                } else {
                    // Manejar errores de la respuesta
                }
            }

            override fun onFailure(call: Call<List<Cliente>>, t: Throwable) {
                // Manejar errores de conexión
            }
        })
    }

    private fun confirmarEliminarCliente(cliente: Cliente) {
        AlertDialog.Builder(this).apply {
            setTitle("Confirmar eliminación")
            setMessage("¿Está seguro de que desea eliminar a ${cliente.nombre}?")
            setPositiveButton("Sí") { _, _ ->
                eliminarClienteEnAPI(cliente)
            }
            setNegativeButton("No", null)
        }.show()
    }

    private fun eliminarClienteEnAPI(cliente: Cliente) {
        val apiService = RetrofitClient.apiService
        apiService.eliminarCliente(cliente.idCliente).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@ClienteActivity, "Cliente eliminado: ${cliente.nombre}", Toast.LENGTH_SHORT).show()
                    obtenerClientesDesdeAPI() // Volver a cargar los clientes después de eliminar
                } else {
                    // Manejar errores de la respuesta
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                // Manejar errores de conexión
            }
        })
    }
}
