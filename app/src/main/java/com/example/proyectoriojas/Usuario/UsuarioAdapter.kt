package com.example.proyectoriojas.Usuario

import Usuario
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.TextView
import com.example.proyectoriojas.Proveedor.EditarProveedorActivity
import com.example.proyectoriojas.R
class UsuarioAdapter(
    context: Context,
    private val dataSource: MutableList<Usuario>,
    private val eliminarUsuarioCallback: (Usuario) -> Unit
) : ArrayAdapter<Usuario>(context, R.layout.item_usuario, dataSource) {

    private class ViewHolder {
        lateinit var idUsuario: TextView
        lateinit var nombreUsuario: TextView
        lateinit var correoUsuario: TextView
        lateinit var botonEliminar: ImageButton
        lateinit var botonEditar: ImageButton
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        val holder: ViewHolder

        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_usuario, parent, false)
            holder = ViewHolder()
            holder.idUsuario = view.findViewById(R.id.textViewIdUsuario)
            holder.nombreUsuario = view.findViewById(R.id.textViewNombreUsuario)
            holder.correoUsuario = view.findViewById(R.id.textViewCorreoUsuario)
            holder.botonEliminar = view.findViewById(R.id.botonEliminar)
            holder.botonEditar = view.findViewById(R.id.botonEditar)
            view.tag = holder
        } else {
            view = convertView
            holder = convertView.tag as ViewHolder
        }

        val usuario = dataSource[position]

        holder.idUsuario.text = usuario.idUsuario.toString()
        holder.nombreUsuario.text = usuario.nombre
        holder.correoUsuario.text = usuario.correoElectronico

        holder.botonEliminar.setOnClickListener {
            eliminarUsuarioCallback(usuario)
        }

        holder.botonEditar.setOnClickListener {
            val intent = Intent(context, EditarUsuarioActivity::class.java).apply {
                putExtra("idUsuario", usuario.idUsuario)
                putExtra("NOMBRE", usuario.nombre)
                putExtra("CORREO", usuario.correoElectronico)
            }
            context.startActivity(intent)
        }

        return view
}
}
