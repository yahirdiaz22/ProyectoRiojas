import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.TextView
import com.example.proyectoriojas.Cliente.EditarClienteActivity
import com.example.proyectoriojas.R

class ClienteAdapter(
    context: Context,
    private val dataSource: MutableList<Cliente>,
    private val eliminarClienteCallback: (Cliente) -> Unit
) : ArrayAdapter<Cliente>(context, R.layout.item_cliente, dataSource) {

    private class ViewHolder {
        lateinit var idCliente: TextView
        lateinit var nombre: TextView
        lateinit var direccion: TextView
        lateinit var botonEliminar: ImageButton
        lateinit var botonEditar: ImageButton
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        val holder: ViewHolder

        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_cliente, parent, false)
            holder = ViewHolder()
            holder.idCliente = view.findViewById(R.id.textViewIdCliente)
            holder.nombre = view.findViewById(R.id.textViewNombre)
            holder.direccion = view.findViewById(R.id.textViewDireccion)
            holder.botonEliminar = view.findViewById(R.id.botonEliminar)
            holder.botonEditar = view.findViewById(R.id.botonEditar)
            view.tag = holder
        } else {
            view = convertView
            holder = convertView.tag as ViewHolder
        }

        // Obtener el cliente actual
        val cliente = dataSource[position]

        // Asignar valores a los TextView
        holder.idCliente.text = cliente.idCliente.toString()
        holder.nombre.text = cliente.nombre
        holder.direccion.text = cliente.direccion

        // Configurar el botón eliminar
        holder.botonEliminar.setOnClickListener {
            eliminarClienteCallback(cliente)
        }

        // Configurar el botón editar
        holder.botonEditar.setOnClickListener {
            val intent = Intent(context, EditarClienteActivity::class.java).apply {
                putExtra("ID_CLIENTE", cliente.idCliente)
                putExtra("NOMBRE", cliente.nombre)
                putExtra("DIRECCION", cliente.direccion)
                putExtra("CORREO", cliente.correoElectronico)
                putExtra("TELEFONO", cliente.telefono)
            }
            context.startActivity(intent)
        }

        return view
    }
}
