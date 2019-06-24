package com.bqg.ventas.Entidades

data class ItemPedido (
    var producto: Producto?= null,
    var unidad: Unidad?= null,
    var cantidad: Double= 0.0,
    var precioUnitario: Double= 0.0,
    var precioTotal: Double= 0.0,
    var stockNuevo: Double=0.0,
    var grabado:Boolean=false
){
    fun ObtenerPrecio(){
            if(unidad!=null){
                precioUnitario=unidad!!.Precio
                precioTotal=precioUnitario*cantidad
                stockNuevo=unidad!!.Stock-cantidad
            }else{
                precioUnitario=0.00
                precioTotal=0.00
                stockNuevo=0.00
            }
    }

    fun EstablecerProducto(productoActual:Producto){
        producto=productoActual
        unidad=null
        ObtenerPrecio()
    }
}