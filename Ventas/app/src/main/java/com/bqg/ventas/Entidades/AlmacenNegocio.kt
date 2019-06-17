package com.bqg.ventas.Entidades

import com.google.gson.annotations.SerializedName

data class AlmacenNegocio (
    @SerializedName("CodigoRespuesta")
    var CodigoRespuesta:Int,
    @SerializedName("MensajeRespuesta")
    var MensajeRespuesta:String,
    @SerializedName("Items")
    var almacen:Int?
)