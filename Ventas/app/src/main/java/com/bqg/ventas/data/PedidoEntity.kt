package com.bqg.ventas.data

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "Pedido")
data class PedidoEntity (
    @PrimaryKey(autoGenerate = true)
    var id:Int = 0,
    var jsonPedido:String = "",
    var fechaCreacion:String = "",
    var IDCliente:Int=0,
    var usuario:String=""
)