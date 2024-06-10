import android.os.Parcel
import android.os.Parcelable

data class Compra(
    val idCompra: Int = 0, // Campo opcional, puede ser auto-generado o por defecto
    val cantidadComprada: Int,
    val fecha: String,
    val precioUnitario: Double,
    val nombre: String,
    val status: Int = 1 // Valor por defecto
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readDouble(),
        parcel.readString() ?: "",
                parcel.readInt()

    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(idCompra)
        parcel.writeInt(cantidadComprada)
        parcel.writeString(fecha) // La fecha se mantiene igual, ya que está en formato de cadena
        parcel.writeDouble(precioUnitario)
        parcel.writeInt(status)
        parcel.writeString(nombre)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Compra> {
        override fun createFromParcel(parcel: Parcel): Compra {
            return Compra(parcel)
        }

        override fun newArray(size: Int): Array<Compra?> {
            return arrayOfNulls(size)
        }
    }
}
