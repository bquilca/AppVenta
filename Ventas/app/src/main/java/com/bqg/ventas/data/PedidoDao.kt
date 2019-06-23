package com.bqg.ventas.data

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface PedidoDao {
    @Query("SELECT * FROM Pedido where usuario=:usuario order by id desc")
    fun getListaPedidos(usuario:String): MutableList<PedidoEntity>

    @Insert
    fun agregarPedido(pedidoEntity : PedidoEntity):Long

    @Query("SELECT * FROM Pedido where id like :id")
    fun getPedidoPorId(id: Long): PedidoEntity

    @Query("DELETE FROM Pedido where fechaCreacion != :fechaActual")
    fun eliminarPedidosDiasAnterior(fechaActual: String)

    @Query("SELECT  Distinct IDCliente FROM Pedido where fechaCreacion = :fechaActual and usuario=:usuario")
    fun getClientesConVentas(fechaActual :String,usuario: String): List<ClienteDia>

}