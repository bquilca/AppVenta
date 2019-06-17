package com.bqg.ventas.Entidades


import com.google.gson.annotations.SerializedName

data class  ListaUsuariosEmpresa(
    @SerializedName("estado")
    var estado:Boolean?=false,
    @SerializedName("mensaje")
    var mensaje:String?=null,
    @SerializedName("items")
    var usuarios: List<UsuarioEmpresa>? = null
)