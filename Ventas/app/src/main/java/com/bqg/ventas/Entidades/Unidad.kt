package com.bqg.ventas.Entidades

class Unidad(
    var Descripcion:String,
    var Stock:Double,
    var IDUnidadItemListaPrecio:Int,
    var Abreviacion:String,
    var Precio:Double,
    var Factor:Int,
    var TieneEscalas:Int,
    var escalas: List<UnidadEscala>? = null
)