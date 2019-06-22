package com.bqg.ventas.Entidades

import retrofit2.Call
import retrofit2.http.*

interface Api {
    @GET("login.php?")
    fun validarLogin(
        @Query("username") username:String,
        @Query("password") password:String,
        @Query("imei") imei:String): Call<ListaUsuariosEmpresa>

    @GET("Negocio?")
    fun obtenerClientes(
        @Query("idVendedor") idVendedor:Int,
        @Query("valorBusqueda") valorBusqueda:String
    ): Call<ListaClientes>

    @GET("Negocio?")
    fun obtenerAlmacem(
        @Query("TipoDocumento") contexto:Int
    ): Call<AlmacenNegocio>

    @GET("Negocio?")
    fun obtenerProductos(
        @Query("valorBusqueda") valorBusqueda:String,
        @Query("IdAlmacen") IdAlmacen:Int
        ): Call<ListaProductos>

    @GET("Negocio?")
    fun obtenerUnidades(
        @Query("idCliente") idCliente:Int,
        @Query("idTipoCliente") idTipoCliente:Int,
        @Query("esCredito") esCredito:Boolean,
        @Query("idProducto") idProducto:Int,
        @Query("idAlmacen") idAlmacen:Int
    ): Call<ListaUnidades>

    @GET("Negocio?")
    fun obtenerEscalasUnidad(
        @Query("IDUnidadItemListaPrecio") IDUnidadItemListaPrecio:Int,
        @Query("esCredito") esCredito:Boolean
    ): Call<ListaUnidadEscala>

    @POST("Pedido")
    fun grabarPedido(
        @Body contenidoJSON:String
    ): Call<PedidoNegocio>
}