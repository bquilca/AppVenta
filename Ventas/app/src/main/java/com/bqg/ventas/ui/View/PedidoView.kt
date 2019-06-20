package com.bqg.ventas.ui.View

import android.support.v7.widget.RecyclerView
import android.view.View
import com.bqg.ventas.Entidades.Pedido
import com.bqg.ventas.data.PedidoEntity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.item_lista_pedidos.view.*

class PedidoView(view:View):RecyclerView.ViewHolder(view) {
    fun bind(part: PedidoEntity, clickListener: (PedidoEntity) -> Unit) {


        val pedidoObjeto= Gson().fromJson(part.jsonPedido, Pedido::class.java )

        itemView.txtPedidoListaPedido.text="Número de Pedido : ${pedidoObjeto.numeroDocumento}"
        itemView.txtDniListaPedido.text="${pedidoObjeto.cliente!!.DI} : ${pedidoObjeto.cliente!!.Documento}"
        itemView.txtClienteListaPedido.text="Cliente : ${pedidoObjeto.cliente!!.NombreCompleto}"
        itemView.txtTotalListaPedido.text="Total : ${pedidoObjeto.montoTotal.toString()}"
        itemView.txtFechaPedido.text=part.fechaCreacion
        
        itemView.setOnClickListener { clickListener(part)}
    }
}