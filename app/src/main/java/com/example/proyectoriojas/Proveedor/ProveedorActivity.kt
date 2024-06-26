package com.example.proyectoriojas
import Proveedor
import ProveedorAdapter
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectoriojas.Proveedor.InsertarProveedor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProveedorActivity : AppCompatActivity() {

    private lateinit var listViewProveedores: ListView
    private lateinit var buttonInsertar: Button
    private lateinit var buttonRegresar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mostrarproveedor)

        listViewProveedores = findViewById(R.id.listViewProveedores)
        buttonInsertar = findViewById(R.id.button)
        buttonRegresar = findViewById(R.id.btnRegresar)

        buttonInsertar.setOnClickListener {
            val intent = Intent(this, InsertarProveedor::class.java)
            startActivity(intent)
        }

        buttonRegresar.setOnClickListener {
            finish()
        }

        obtenerProveedoresDesdeAPI()
    }

    override fun onResume() {
        super.onResume()
        obtenerProveedoresDesdeAPI()
    }

    private fun obtenerProveedoresDesdeAPI() {
        val apiService = RetrofitClient.apiService
        apiService.obtenerProveedores().enqueue(object : Callback<List<Proveedor>> {
            override fun onResponse(call: Call<List<Proveedor>>, response: Response<List<Proveedor>>) {
                if (response.isSuccessful) {
                    val proveedores = response.body() ?: emptyList()
                    val adapter = ProveedorAdapter(this@ProveedorActivity, proveedores.toMutableList()) { proveedor ->
                        confirmarEliminarProveedor(proveedor)
                    }

                    listViewProveedores.adapter = adapter
                } else {
                }
            }

            override fun onFailure(call: Call<List<Proveedor>>, t: Throwable) {
            }
        })
    }

    private fun confirmarEliminarProveedor(proveedor: Proveedor) {
        AlertDialog.Builder(this).apply {
            setTitle("Confirmar eliminación")
            setMessage("¿Está seguro de que desea eliminar a ${proveedor.nombre}?")
            setPositiveButton("Sí") { _, _ ->
                eliminarProveedorEnAPI(proveedor)
            }
            setNegativeButton("No", null)
        }.show()
    }

    private fun eliminarProveedorEnAPI(proveedor: Proveedor) {
        val apiService = RetrofitClient.apiService
        apiService.eliminarProveedor(proveedor.idProveedor).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@ProveedorActivity, "Proveedor eliminado: ${proveedor.nombre}", Toast.LENGTH_SHORT).show()
                    obtenerProveedoresDesdeAPI()
                } else {
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
            }
        })
    }
}
