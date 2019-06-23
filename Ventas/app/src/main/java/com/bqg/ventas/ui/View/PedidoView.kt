package com.bqg.ventas.ui.View

import android.support.v7.widget.RecyclerView
import android.view.View
import com.bqg.ventas.Entidades.Pedido
import com.bqg.ventas.data.PedidoEntity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.item_lista_pedidos.view.*

class PedidoView(view:View):RecyclerView.ViewHolder(view) {
    fun bind(part: PedidoEntity, clickListener: (PedidoEntity) -> Unit) {

        var helper= com.bqg.ventas.Utiles.Helper()
        val pedidoObjeto= Gson().fromJson(part.jsonPedido, Pedido::class.java )

        itemView.txtPedidoListaPedido.text="Número de Pedido : ${pedidoObjeto.numeroDocumento}"

        if(pedidoObjeto.cliente!=null){
            itemView.txtDniListaPedido.text="${pedidoObjeto.cliente!!.DI} : ${pedidoObjeto.cliente!!.Documento}"
            itemView.txtClienteListaPedido.text="Cliente : ${pedidoObjeto.cliente!!.NombreCompleto}"
        }else{
            itemView.txtDniListaPedido.text="Cliente Referencial"
            itemView.txtClienteListaPedido.text="Cliente : ${pedidoObjeto.nombreClienteReferencial}"
        }

        itemView.txtTotalListaPedido.text="Total : ${helper.formateaMonedaSoles(pedidoObjeto.montoTotal)}"
        itemView.txtFechaPedido.text=part.fechaCreacion
        
        itemView.setOnClickListener { clickListener(part)}
    }
}