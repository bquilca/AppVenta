package com.bqg.ventas.Entidades

import com.google.gson.annotations.SerializedName

data class ListaUnidades(
    @SerializedName("CodigoRespuesta")
    var CodigoRespuesta:Int,
    @SerializedName("MensajeRespuesta")
    var MensajeRespuesta:String,
    @SerializedName("Items")
    var unidades: List<Unidad>? = null
)