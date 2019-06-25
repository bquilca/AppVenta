package com.bqg.ventas.Entidades

import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*

data class Pedido (
    var idTipoDocumento: String="",
    var IDVendedor:String="",
    var esClienteReferencial: Boolean=false,
    var numeroDocumento: String ? =null,
    var nombreClienteReferencial: String ="",
    var direccionClienteReferencial: String ="",
    var documentoClienteReferencial: String ="",
    var uuid: String = UUID.randomUUID().toString(),
    var cliente: Cliente ?= null,
    var esCredito: Boolean =false,
    var itemsPedido: ArrayList<ItemPedido>?=ArrayList(),
    var montoSubTotal: Double=0.00,
    var montoAfecto:Double=0.00,
    var montoTotalExonerado:Double=0.00,
    var montoDescuento:Double=0.00,
    var montoIGV:Double=0.00,
    var montoTotal: Double=0.00,
    var longitudeGPS:Double=0.00,
    var latitudeGPS:Double=0.00
    )
{
    fun setcliente(clienteSeleccionado:Cliente){
        cliente=clienteSeleccionado
        esCredito=false
        itemsPedido!!.clear()
        calcularTotal()
    }

    fun setClienteReferencial(valor: Boolean){
        cliente=null
        esCredito=false
        nombreClienteReferencial=""
        documentoClienteReferencial=""
        documentoClienteReferencial=""
        esClienteReferencial=valor
    }

    fun agregarItem(itemPedido:ItemPedido){
        itemPedido.grabado=true
        itemsPedido!!.add(itemPedido)
        calcularTotal()
    }

    fun modificarItem(itemPedido:ItemPedido){
        itemsPedido!![itemsPedido!!.indexOf(itemPedido)] = itemPedido
    }

    fun eliminarItem(itemPedido:ItemPedido){
        itemsPedido!!.remove(itemPedido)
        calcularTotal()
    }

    private fun calcularTotal(){
        var totalCalculo=0.00
        for (item in itemsPedido!!){
            item.precioTotal=item.cantidad*item.precioUnitario
            totalCalculo= totalCalculo!! +(item.cantidad*item.precioUnitario)
        }
        this.montoTotal= BigDecimal(totalCalculo).setScale(2, RoundingMode.HALF_EVEN).toDouble()
        this.montoIGV= BigDecimal(  totalCalculo*0.18).setScale(2, RoundingMode.HALF_EVEN).toDouble()
        this.montoSubTotal= BigDecimal( totalCalculo*(1-0.18)).setScale(2, RoundingMode.HALF_EVEN).toDouble()
        this.montoAfecto=0.00
        this.montoTotalExonerado=0.00
        this.montoDescuento=0.00
    }


    fun datosClienteCompleto():Boolean{
        var rs=false
        if(esClienteReferencial){
            rs=true
            if(nombreClienteReferencial==""){
                rs=false
            }else if(documentoClienteReferencial==""){
                rs=false
            }else if(direccionClienteReferencial == ""){
                rs=false
            }
        }else{
            if(cliente!=null){
                rs=true
            }
        }
        return  rs
    }

}