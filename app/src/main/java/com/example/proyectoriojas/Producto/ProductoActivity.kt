package com.example.proyectoriojas.Producto

import Producto
import android.content.Intent
import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectoriojas.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductoActivity : AppCompatActivity() {
    private lateinit var listViewProductos: ListView
    private lateinit var productoAdapter: ProductoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mostrarproducto)

        listViewProductos = findViewById(R.id.listViewProductos)

        productoAdapter = ProductoAdapter(this, mutableListOf(), { producto ->
        }) { producto ->
            val intent = Intent(this, EditarProductoActivity::class.java).apply {
                putExtra("idProducto", producto.idProducto)
                putExtra("nombre", producto.nombre)
                putExtra("precio", producto.precio)
                putExtra("cantidad", producto.cantidad)
            }
            startActivityForResult(intent, REQUEST_CODE_EDITAR_PRODUCTO)
        }

        listViewProductos.adapter = productoAdapter

        cargarDatos()
    }

    private fun cargarDatos() {
        RetrofitClient.apiService.obtenerProductos().enqueue(object : Callback<List<Producto>> {
            override fun onResponse(call: Call<List<Producto>>, response: Response<List<Producto>>) {
                if (response.isSuccessful) {
                    val productos = response.body()
                    productos?.let {
                        productoAdapter.actualizarDatos(it.toMutableList())
                    }
                } else {
                }
            }

            override fun onFailure(call: Call<List<Producto>>, t: Throwable) {
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_EDITAR_PRODUCTO && resultCode == RESULT_OK) {
            // Recargar los datos después de una actualización exitosa
            cargarDatos()
        }
    }

    companion object {
        const val REQUEST_CODE_EDITAR_PRODUCTO = 1
    }
}
