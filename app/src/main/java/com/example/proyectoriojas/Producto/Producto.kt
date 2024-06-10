import android.os.Parcel
import android.os.Parcelable
data class Producto(
    val idProducto: Int = 0,
    val nombre: String,
    val precio: Double,
    val cantidad: Int,
    val status: Int = 1
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readDouble(),
        parcel.readInt(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(idProducto)
        parcel.writeString(nombre)
        parcel.writeDouble(precio)
        parcel.writeInt(cantidad)
        parcel.writeInt(status)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Producto> {
        override fun createFromParcel(parcel: Parcel): Producto {
            return Producto(parcel)
        }

        override fun newArray(size: Int): Array<Producto?> {
            return arrayOfNulls(size)
        }
    }
}
