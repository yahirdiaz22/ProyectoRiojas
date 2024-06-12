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
        lateinit var nombreProveedor: TextView
        lateinit var direccionProveedor: TextView
        lateinit var botonEliminar: ImageButton
        lateinit var botonEditar: ImageButton
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        val holder: ViewHolder

        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_proveedor, parent, false)

            holder = ViewHolder()
            holder.nombreProveedor = view.findViewById(R.id.textViewNombreProveedor)
            holder.direccionProveedor = view.findViewById(R.id.textViewDireccionProveedor)
            holder.botonEliminar = view.findViewById(R.id.botonEliminar)
            holder.botonEditar = view.findViewById(R.id.botonEditar)

            view.tag = holder
        } else {
            view = convertView
            holder = convertView.tag as ViewHolder
        }

        val proveedor = dataSource[position]

        holder.nombreProveedor.text = proveedor.nombre
        holder.direccionProveedor.text = proveedor.direccion
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
