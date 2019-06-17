package com.bqg.ventas.Entidades

import com.google.gson.annotations.SerializedName

data class ListaUnidadEscala (
    @SerializedName("CodigoRespuesta")
    var CodigoRespuesta:Int,
    @SerializedName("MensajeRespuesta")
    var MensajeRespuesta:String,
    @SerializedName("Items")
    var escalas: List<UnidadEscala>? = null
)