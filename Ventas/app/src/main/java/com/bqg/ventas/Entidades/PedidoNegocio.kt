package com.bqg.ventas.Entidades

import com.google.gson.annotations.SerializedName

data class  PedidoNegocio (
    @SerializedName("CodigoRespuesta")
    var CodigoRespuesta:Int,
    @SerializedName("MensajeRespuesta")
    var MensajeRespuesta:String,
    @SerializedName("Items")
    var numeroPedido:String
)