package com.example.proyectoriojas
import Compra
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectoriojas.Compra.CompraAdapter
import com.example.proyectoriojas.Compra.CompraEditarActivity
import com.example.proyectoriojas.InsertarCompra
import com.example.proyectoriojas.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CompraActivity : AppCompatActivity() {

    private lateinit var listViewCompras: ListView
    private lateinit var buttonInsertar: Button
    private lateinit var buttonRegresar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mostrarcompra)

        listViewCompras = findViewById(R.id.listViewCompras)
        buttonInsertar = findViewById(R.id.buttonInsertarCompra)
        buttonRegresar = findViewById(R.id.btnRegresar)

        buttonInsertar.setOnClickListener {
            val intent = Intent(this, InsertarCompra::class.java)
            startActivity(intent)
        }
        buttonRegresar.setOnClickListener {
            finish()
        }

        obtenerComprasDesdeAPI()
    }

    override fun onResume() {
        super.onResume()
        obtenerComprasDesdeAPI()
    }

    private fun obtenerComprasDesdeAPI() {
        val apiService = RetrofitClient.apiService
        apiService.obtenerCompra().enqueue(object : Callback<List<Compra>> {
            override fun onResponse(call: Call<List<Compra>>, response: Response<List<Compra>>) {
                if (response.isSuccessful) {
                    val compras = response.body() ?: emptyList()
                    val comprasConStatusUno = compras.filter { it.status == 1 }

                    val adapter = CompraAdapter(this@CompraActivity, comprasConStatusUno.toMutableList(), ::confirmarEliminarCompra, ::editarCompra)
                    listViewCompras.adapter = adapter
                } else {
                }
            }

            override fun onFailure(call: Call<List<Compra>>, t: Throwable) {
            }
        })
    }

    private fun confirmarEliminarCompra(compra: Compra) {
        AlertDialog.Builder(this).apply {
            setTitle("Confirmar eliminación")
            setMessage("¿Está seguro de que desea eliminar la compra de ${compra.nombre}?")
            setPositiveButton("Sí") { _, _ ->
                eliminarCompraEnAPI(compra)
            }
            setNegativeButton("No", null)
        }.show()
    }

    private fun eliminarCompraEnAPI(compra: Compra) {
        val apiService = RetrofitClient.apiService
        apiService.eliminarCompra(compra.idCompra).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(
                        this@CompraActivity,
                        "Compra eliminada: ${compra.nombre}",
                        Toast.LENGTH_SHORT
                    ).show()
                    obtenerComprasDesdeAPI()
                } else {

                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
            }
        })
    }

    private fun editarCompra(compra: Compra) {
        val intent = Intent(this, CompraEditarActivity::class.java).apply {
            putExtra("ID_COMPRA", compra.idCompra)
            putExtra("CANTIDAD_COMPRADA", compra.cantidadComprada)
            putExtra("FECHA", compra.fecha)
            putExtra("PRECIO_UNITARIO", compra.precioUnitario)
        }
        startActivity(intent)
    }
}
