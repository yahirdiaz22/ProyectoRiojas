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

class ProveedorAdapter(
    context: Context,
    private val dataSource: MutableList<Proveedor>,
    private val eliminarProveedorCallback: (Proveedor) -> Unit
) : ArrayAdapter<Proveedor>(context, R.layout.item_proveedor, dataSource) {

    private class ViewHolder {
        lateinit var idProveedor: TextView
        lateinit var nombreProveedor: TextView
        lateinit var direccionProveedor: TextView
        lateinit var botonEliminar: ImageButton
        lateinit var botonEditar: ImageButton
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        val holder: ViewHolder

        if (convertView == null) {
            // Inflar el layout del item proveedor si convertView es nulo
            view = LayoutInflater.from(context).inflate(R.layout.item_proveedor, parent, false)

            // Inicializar el ViewHolder y encontrar las vistas
            holder = ViewHolder()
            holder.idProveedor = view.findViewById(R.id.textViewIdProveedor)
            holder.nombreProveedor = view.findViewById(R.id.textViewNombreProveedor)
            holder.direccionProveedor = view.findViewById(R.id.textViewDireccionProveedor)
            holder.botonEliminar = view.findViewById(R.id.botonEliminar)
            holder.botonEditar = view.findViewById(R.id.botonEditar)

            // Establecer el ViewHolder como una etiqueta de la vista
            view.tag = holder
        } else {
            // Si convertView no es nulo, obtener el ViewHolder de su etiqueta
            view = convertView
            holder = convertView.tag as ViewHolder
        }

        // Obtener el proveedor actual
        val proveedor = dataSource[position]

        // Asignar valores a las vistas del ViewHolder
        holder.idProveedor.text = proveedor.idProveedor.toString()
        holder.nombreProveedor.text = proveedor.nombre
        holder.direccionProveedor.text = proveedor.direccion

        // Configurar onClickListeners para los botones eliminar y editar
        holder.botonEliminar.setOnClickListener {
            eliminarProveedorCallback(proveedor)
        }

        holder.botonEditar.setOnClickListener {
            val intent = Intent(context, EditarProveedorActivity::class.java).apply {
                putExtra("ID_PROVEEDOR", proveedor.idProveedor)
                putExtra("NOMBRE", proveedor.nombre)
                putExtra("DIRECCION", proveedor.direccion)
                putExtra("CORREO", proveedor.correoElectronico)
                putExtra("TELEFONO", proveedor.telefono)
            }
            context.startActivity(intent)
        }

        return view
    }
}
