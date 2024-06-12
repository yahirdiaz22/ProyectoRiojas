package com.example.proyectoriojas.Producto

import Producto
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.proyectoriojas.R

class ProductoAdapter(
    context: Context,
    private var dataSource: MutableList<Producto>,
    private val onEliminarProducto: (Producto) -> Unit,
    private val onEditarProducto: (Producto) -> Unit
) : ArrayAdapter<Producto>(context, R.layout.item_producto, dataSource) {

    private class ViewHolder {
        lateinit var nombreProducto: TextView
        lateinit var precioProducto: TextView
        lateinit var cantidadProducto: TextView
        lateinit var botonEliminar: ImageButton
        lateinit var botonEditar: ImageButton
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        val holder: ViewHolder

        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_producto, parent, false)
            holder = ViewHolder()
            holder.nombreProducto = view.findViewById(R.id.textViewNombre)
            holder.precioProducto = view.findViewById(R.id.textViewPrecio)
            holder.cantidadProducto = view.findViewById(R.id.textViewCantidad)
            holder.botonEliminar = view.findViewById(R.id.botonEliminar)
            holder.botonEditar = view.findViewById(R.id.botonEditar)
            view.tag = holder
        } else {
            view = convertView
            holder = convertView.tag as ViewHolder
        }

        // Obtener el producto actual
        val producto = dataSource[position]

        // Asignar valores a los TextView
        holder.nombreProducto.text = producto.nombre
        holder.precioProducto.text = producto.precio.toString()
        holder.cantidadProducto.text = producto.cantidad.toString()

        // Configurar el botón eliminar
        holder.botonEliminar.setOnClickListener {
            AlertDialog.Builder(context).apply {
                setTitle("Confirmar eliminación")
                setMessage("¿Está seguro de que desea eliminar el producto ${producto.nombre}?")
                setPositiveButton("Sí") { _, _ -> onEliminarProducto(producto) }
                setNegativeButton("No", null)
            }.show()
        }

        // Configurar el botón editar
        holder.botonEditar.setOnClickListener {
            onEditarProducto(producto)
        }

        return view
    }

    // Método para actualizar los datos de la lista
    fun actualizarDatos(nuevosDatos: MutableList<Producto>) {
        dataSource.clear()
        dataSource.addAll(nuevosDatos)
        notifyDataSetChanged()
    }
}
