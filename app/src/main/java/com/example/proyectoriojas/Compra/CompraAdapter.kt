package com.example.proyectoriojas.Compra

import Compra
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.proyectoriojas.R

class CompraAdapter(
    context: Context,
    private var dataSource: MutableList<Compra>,
    private val eliminarCompraCallback: (Compra) -> Unit,
    private val editarCompraCallback: (Compra) -> Unit
) : ArrayAdapter<Compra>(context, R.layout.item_compra, dataSource) {

    private class ViewHolder {
        lateinit var idCompra: TextView
        lateinit var cantidadComprada: TextView
        lateinit var fecha: TextView
        lateinit var precioUnitario: TextView
        lateinit var botonEliminar: ImageButton
        lateinit var botonEditar: ImageButton
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        val holder: ViewHolder

        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_compra, parent, false)
            holder = ViewHolder()
            holder.idCompra = view.findViewById(R.id.textViewIdCompra)
            holder.cantidadComprada = view.findViewById(R.id.textViewCantidadComprada)
            holder.fecha = view.findViewById(R.id.textViewFecha)
            holder.precioUnitario = view.findViewById(R.id.textViewPrecioUnitario)
            holder.botonEliminar = view.findViewById(R.id.botonEliminar)
            holder.botonEditar = view.findViewById(R.id.botonEditar)
            view.tag = holder
        } else {
            view = convertView
            holder = convertView.tag as ViewHolder
        }

        // Obtener la compra actual
        val compra = dataSource[position]

        // Asignar valores a los TextView
        holder.idCompra.text = compra.idCompra.toString()
        holder.cantidadComprada.text = compra.cantidadComprada.toString()
        holder.fecha.text = compra.fecha
        holder.precioUnitario.text = compra.precioUnitario.toString()

        // Configurar el botón eliminar
        holder.botonEliminar.setOnClickListener {
            mostrarDialogoEliminar(compra)
        }

        // Configurar el botón editar
        holder.botonEditar.setOnClickListener {
            editarCompraCallback(compra)
        }

        return view
    }

    private fun mostrarDialogoEliminar(compra: Compra) {
        AlertDialog.Builder(context)
            .setTitle("Eliminar Compra")
            .setMessage("¿Estás seguro de que deseas eliminar esta compra?")
            .setPositiveButton("Sí") { _, _ ->
                eliminarCompraCallback(compra)
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }
}
