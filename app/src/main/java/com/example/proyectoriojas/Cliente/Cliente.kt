import android.os.Parcel
import android.os.Parcelable

data class Cliente(
    val idCliente: Int,
    val nombre: String,
    val direccion: String,
    val correoElectronico: String,
    val telefono: String,
    val status: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(idCliente)
        parcel.writeString(nombre)
        parcel.writeString(direccion)
        parcel.writeString(correoElectronico)
        parcel.writeString(telefono)
        parcel.writeInt(status)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Cliente> {
        override fun createFromParcel(parcel: Parcel): Cliente {
            return Cliente(parcel)
        }

        override fun newArray(size: Int): Array<Cliente?> {
            return arrayOfNulls(size)
        }
    }

}
