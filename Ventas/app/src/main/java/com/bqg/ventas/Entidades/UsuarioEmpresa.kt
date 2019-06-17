package com.bqg.ventas.Entidades

import android.os.Parcel
import android.os.Parcelable

data class UsuarioEmpresa(
    var IDEmpresa: String= "",
    var Nombre: String = "",
    var UrlServicioWeb:String="",
    var NombreEmpresa:String="",
    var IDDocumento:String="",
    var IDVendedor:String="",
    var EncontroImei:String=""
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(IDEmpresa)
        parcel.writeString(Nombre)
        parcel.writeString(UrlServicioWeb)
        parcel.writeString(NombreEmpresa)
        parcel.writeString(IDDocumento)
        parcel.writeString(IDVendedor)
        parcel.writeString(EncontroImei)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UsuarioEmpresa> {
        override fun createFromParcel(parcel: Parcel): UsuarioEmpresa {
            return UsuarioEmpresa(parcel)
        }

        override fun newArray(size: Int): Array<UsuarioEmpresa?> {
            return arrayOfNulls(size)
        }
    }

}