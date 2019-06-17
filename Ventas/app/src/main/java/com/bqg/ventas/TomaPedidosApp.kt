package com.bqg.ventas

import android.app.Application
import android.arch.persistence.room.Room
import com.bqg.ventas.data.PedidoDatabase

class TomaPedidosApp : Application() {

    companion object {
        lateinit var database: PedidoDatabase
    }

    override fun onCreate() {
        super.onCreate()
        TomaPedidosApp.database= Room.databaseBuilder(this, PedidoDatabase::class.java, "appVentasdb").build()
    }

}