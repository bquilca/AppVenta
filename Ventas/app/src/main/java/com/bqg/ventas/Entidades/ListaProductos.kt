package com.bqg.ventas.Entidades

import com.google.gson.annotations.SerializedName

data class ListaProductos(
    @SerializedName("CodigoRespuesta")
    var CodigoRespuesta:Int,
    @SerializedName("MensajeRespuesta")
    var MensajeRespuesta:String,
    @SerializedName("Items")
    var productos: List<Producto>? = null
)