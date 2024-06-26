package com.example.proyectoriojas.Venta

import Venta
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectoriojas.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VentaActivity : AppCompatActivity() {

    private lateinit var listViewVentas: ListView
    private lateinit var buttonInsertar: Button
    private lateinit var buttonRegresar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mostrarventa)

        listViewVentas = findViewById(R.id.listViewVentas)
        buttonInsertar = findViewById(R.id.buttonInsertarVenta)
        buttonRegresar = findViewById(R.id.btnRegresar)

        buttonInsertar.setOnClickListener {
            val intent = Intent(this, InsertarVenta::class.java)
            startActivity(intent)
        }
        buttonRegresar.setOnClickListener {
            finish()
        }

        obtenerVentasActivasDesdeAPI()
    }

    override fun onResume() {
        super.onResume()
        obtenerVentasActivasDesdeAPI()
    }

    private fun obtenerVentasActivasDesdeAPI() {
        val apiService = RetrofitClient.apiService
        apiService.obtenerVentas().enqueue(object : Callback<List<Venta>> {
            override fun onResponse(call: Call<List<Venta>>, response: Response<List<Venta>>) {
                if (response.isSuccessful) {
                    val ventas = response.body() ?: emptyList()
                    val ventasActivas = ventas.filter { it.status == 1 }
                    val adapter = VentaAdapter(this@VentaActivity, ventasActivas.toMutableList()) { venta ->
                        confirmarEliminarVenta(venta)
                    }

                    listViewVentas.adapter = adapter
                } else {
                    Toast.makeText(this@VentaActivity, "Error al obtener ventas: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Venta>>, t: Throwable) {
                Toast.makeText(this@VentaActivity, "Error al conectar con el servidor: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun confirmarEliminarVenta(venta: Venta) {
        AlertDialog.Builder(this).apply {
            setTitle("Confirmar eliminación")
            setMessage("¿Está seguro de que desea eliminar la venta ${venta.idVenta}?")
            setPositiveButton("Sí") { _, _ ->
                eliminarVentaEnAPI(venta)
            }
            setNegativeButton("No", null)
        }.show()
    }

    private fun eliminarVentaEnAPI(venta: Venta) {
        val apiService = RetrofitClient.apiService
        apiService.eliminarVenta(venta.idVenta).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@VentaActivity, "Venta eliminada: ${venta.idVenta}", Toast.LENGTH_SHORT).show()
                    obtenerVentasActivasDesdeAPI()
                } else {
                    Toast.makeText(this@VentaActivity, "Error al eliminar venta: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@VentaActivity, "Error al conectar con el servidor: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
