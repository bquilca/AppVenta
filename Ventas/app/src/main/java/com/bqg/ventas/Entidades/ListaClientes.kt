package com.bqg.ventas.Entidades

import com.google.gson.annotations.SerializedName

data class ListaClientes (
    @SerializedName("CodigoRespuesta")
    var CodigoRespuesta:Int,
    @SerializedName("MensajeRespuesta")
    var MensajeRespuesta:String,
    @SerializedName("Items")
    var clientes: List<Cliente>? = null
)
