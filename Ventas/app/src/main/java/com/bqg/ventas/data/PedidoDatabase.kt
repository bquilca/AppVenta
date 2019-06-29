package com.bqg.ventas.data

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = arrayOf(PedidoEntity::class), version = 2)
abstract  class PedidoDatabase : RoomDatabase() {
    abstract fun pedidoDao(): PedidoDao
}