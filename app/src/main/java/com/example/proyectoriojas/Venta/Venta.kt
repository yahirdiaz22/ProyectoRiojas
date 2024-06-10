import android.os.Parcel
import android.os.Parcelable

data class Venta(
    val idVenta: Int,
    val cantidadVendida: Int,
    val fecha: String,
    val nombre: String,
    val status: Int = 1
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(idVenta)
        parcel.writeInt(cantidadVendida)
        parcel.writeString(fecha)
        parcel.writeString(nombre)
        parcel.writeInt(status)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Venta> {
        override fun createFromParcel(parcel: Parcel): Venta {
            return Venta(parcel)
        }

        override fun newArray(size: Int): Array<Venta?> {
            return arrayOfNulls(size)
        }
    }
}
