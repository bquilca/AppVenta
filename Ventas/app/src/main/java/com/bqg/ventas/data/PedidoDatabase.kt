package com.bqg.ventas.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

@Database(entities = arrayOf(PedidoEntity::class), version = 2)
abstract  class PedidoDatabase : RoomDatabase() {
    abstract fun pedidoDao(): PedidoDao
}