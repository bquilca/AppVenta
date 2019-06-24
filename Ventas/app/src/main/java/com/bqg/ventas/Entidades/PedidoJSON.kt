package com.bqg.ventas.Entidades

data class PedidoJSON
    (
    var codigoVendedor:String="",
    var idTipoDocumento:String="",
    var esClienteReferencial: Boolean=false,

    var nombreClienteReferencial: String ="",
    var direccionClienteReferencial: String ="",
    var documentoClienteReferencial: String ="",

    var IDCliente:Int=0,
    var IDDireccion:Int=0,
    var esCredito: Boolean =false,

    var longitudeGPS:Double=0.00,
    var latitudeGPS:Double=0.00,
    var itemsPedido: ArrayList<ItemPedidoJSON>?=ArrayList(),

    var montoSubTotal: Double=0.00,
    var montoAfecto:Double=0.00,
    var montoTotalExonerado:Double=0.00,
    var montoDescuento:Double=0.00,
    var montoIGV:Double=0.00,
    var montoTotal: Double=0.00
    )