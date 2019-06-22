package com.bqg.ventas.Entidades

data class ItemPedidoJSON (
    var IDProductoAlmacen: Int= 0,
    var IDUnidadItemListaPrecio: Int= 0,
    var cantidad: Double= 0.0,
    var precioUnitario: Double= 0.0,
    var precioTotal: Double= 0.0
)