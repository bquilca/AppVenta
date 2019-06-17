package com.bqg.ventas.Utiles

import android.content.Context
import android.content.SharedPreferences

class Prefs(context:Context) {
    val NOMBRE_ARCHIVO = "com.bqg.ventas.app.prefs"
    val prefs: SharedPreferences = context.getSharedPreferences(NOMBRE_ARCHIVO, 0)

    var usuario: String
        get() = prefs.getString("Usuario", "")
        set(value) = prefs.edit().putString("Usuario", value).apply()

    var password: String
        get() = prefs.getString("Password", "")
        set(value) = prefs.edit().putString("Password", value).apply()

    var NombreUsuario: String
        get() = prefs.getString("NombreUsuario", "")
        set(value) = prefs.edit().putString("NombreUsuario", value).apply()

    var UrlServicioWeb: String
        get() = prefs.getString("UrlServicioWeb", "")
        set(value) = prefs.edit().putString("UrlServicioWeb", value).apply()

    var NombreEmpresa: String
        get() = prefs.getString("NombreEmpresa", "")
        set(value) = prefs.edit().putString("NombreEmpresa", value).apply()

    var IDDocumento: String
        get() = prefs.getString("IDDocumento", "")
        set(value) = prefs.edit().putString("IDDocumento", value).apply()

    var IDVendedor: String
        get() = prefs.getString("IDVendedor", "")
        set(value) = prefs.edit().putString("IDVendedor", value).apply()

    var idAlmacen: Int
        get() = prefs.getInt("idAlmacen", 0)
        set(value) = prefs.edit().putInt("idAlmacen", value).apply()

    var IMEI: String
        get() = prefs.getString("IMEI", "")
        set(value) = prefs.edit().putString("IMEI", value).apply()

}