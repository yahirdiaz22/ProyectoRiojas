package com.example.proyectoriojas.Venta

import Venta
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.TextView
import com.example.proyectoriojas.R
import com.example.proyectoriojas.Usuario.EditarUsuarioActivity

class VentaAdapter(
    context: Context,
    private val dataSource: MutableList<Venta>,
    private val eliminarVentaCallback: (Venta) -> Unit
) : ArrayAdapter<Venta>(context, R.layout.item_venta, dataSource) {

    private class ViewHolder {
        lateinit var cantidadVendida: TextView
        lateinit var fecha: TextView
        lateinit var botonEliminar: ImageButton
        lateinit var botonEditar: ImageButton
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        val holder: ViewHolder

        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_venta, parent, false)
            holder = ViewHolder()
            holder.cantidadVendida = view.findViewById(R.id.textViewCantidadVendida)
            holder.fecha = view.findViewById(R.id.textViewFecha)
            holder.botonEliminar = view.findViewById(R.id.botonEliminar)
            holder.botonEditar = view.findViewById(R.id.botonEditar)
            view.tag = holder
        } else {
            view = convertView
            holder = convertView.tag as ViewHolder
        }

        val venta = dataSource[position]

        holder.cantidadVendida.text = venta.cantidadVendida.toString()
        holder.fecha.text = venta.fecha

        holder.botonEliminar.setOnClickListener {
            eliminarVentaCallback(venta)
        }

        holder.botonEditar.setOnClickListener {
            val intent = Intent(context, EditarVentaActivity::class.java).apply {
                putExtra("idVenta", venta.idVenta)
                putExtra("CANTIDAD", venta.cantidadVendida)
                putExtra("FECHA", venta.fecha)
                putExtra("NOMBRE", venta.nombre)
                putExtra("PRECIOUNITARIO", venta.precioUnitario)

            }
            context.startActivity(intent)
        }

        return view
    }
}
