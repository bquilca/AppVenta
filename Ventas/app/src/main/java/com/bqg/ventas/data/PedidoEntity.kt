package com.bqg.ventas.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Pedido")
data class PedidoEntity (
    @PrimaryKey(autoGenerate = true)
    var id:Int = 0,
    var jsonPedido:String = "",
    var fechaCreacion:String = "",
    var IDCliente:Int=0,
    var usuario:String=""
)