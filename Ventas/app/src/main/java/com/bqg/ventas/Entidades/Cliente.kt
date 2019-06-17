package com.bqg.ventas.Entidades

data class Cliente
    (
    var IDCliente:Int=0,
    var NombreCompleto:String="",
    var DI:String="",
    var Documento:String="",
    var TipoCliente:String="",
    var IDTipoCliente:Int=0,
    var TieneLineaCredito:Boolean=false,
    var MontoLineaCredito:Double=0.00,
    var DiasCredito:Int=0,
    var SaldoCredito:Double=0.0
    )
{
}