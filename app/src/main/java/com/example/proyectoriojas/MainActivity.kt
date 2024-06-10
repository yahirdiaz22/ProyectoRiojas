package com.example.proyectoriojas

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectoriojas.Producto.ProductoActivity
import com.example.proyectoriojas.Usuario.UsuarioActivity
import com.example.proyectoriojas.Venta.VentaActivity

//import com.example.proyectoriojas.Venta.VentaActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menuactivity)

        val btnCliente: Button = findViewById(R.id.buttonCliente)
        val btnCompra: Button = findViewById(R.id.buttonCompra)
        val btnProducto: Button = findViewById(R.id.buttonProducto)
        val btnProveedor: Button = findViewById(R.id.buttonProveedor)
        val btnUsuario: Button = findViewById(R.id.buttonUsuario)
        val btnVenta: Button = findViewById(R.id.buttonVenta)
        val btnProceso: Button = findViewById(R.id.buttonProceso)

        // Configuración de los listeners para los botones
        btnCliente.setOnClickListener {
            startActivity(Intent(this, ClienteActivity::class.java))
        }

        btnCompra.setOnClickListener {
            startActivity(Intent(this, CompraActivity::class.java))
        }

        btnProducto.setOnClickListener {
            startActivity(Intent(this, ProductoActivity::class.java))
        }

        btnProveedor.setOnClickListener {
            startActivity(Intent(this, ProveedorActivity::class.java))
        }

        btnUsuario.setOnClickListener {
            startActivity(Intent(this, UsuarioActivity::class.java))
        }

        btnVenta.setOnClickListener {
            startActivity(Intent(this, VentaActivity::class.java))
        }
        btnProceso.setOnClickListener {
            startActivity(Intent(this, ProcesoActivity::class.java))
        }
    }
}
