package com.bqg.ventas.data

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface PedidoDao {
    @Query("SELECT * FROM Pedido")
    fun getListaPedidos(): MutableList<PedidoEntity>


    @Insert
    fun agregarPedido(pedidoEntity : PedidoEntity):Long


    @Query("SELECT * FROM Pedido where id like :id")
    fun getPedidoPorId(id: Long): PedidoEntity

    @Query("delete FROM Pedido where strftime('%Y-%m-%d',fechaCreacion) > strftime('%Y-%m-%d',:fechaActual) ")
    fun eliminarPedidosDiasAnterior(fechaActual: String)

}