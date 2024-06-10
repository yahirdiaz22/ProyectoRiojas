    import android.os.Parcel
    import android.os.Parcelable

    data class Usuario(
        val idUsuario: Int,
        val nombre: String,
        val correoElectronico: String,
        val password: String,
        val status: Int = 1
    ) : Parcelable {
        constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString() ?: "",
            parcel.readString() ?: "",
            parcel.readString() ?: "",
            parcel.readInt()
        )

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeInt(idUsuario)
            parcel.writeString(nombre)
            parcel.writeString(correoElectronico)
            parcel.writeString(password)
            parcel.writeInt(status)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<Usuario> {
            override fun createFromParcel(parcel: Parcel): Usuario {
                return Usuario(parcel)
            }

            override fun newArray(size: Int): Array<Usuario?> {
                return arrayOfNulls(size)
            }
        }
    }
