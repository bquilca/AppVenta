package com.bqg.ventas.ui.View

import android.support.v7.widget.RecyclerView
import android.view.View
import com.bqg.ventas.Entidades.Pedido
import kotlinx.android.synthetic.main.item_lista_pedidos.view.*

class PedidoView(view:View):RecyclerView.ViewHolder(view) {
    fun bind(part: Pedido, clickListener: (Pedido) -> Unit) {
        itemView.txtPedidoListaPedido.text="Número de Pedido : ${part.numeroDocumento}"
        itemView.txtDniListaPedido.text="${part.cliente!!.DI} : ${part.cliente!!.Documento}"
        itemView.txtClienteListaPedido.text="Cliente : ${part.cliente!!.NombreCompleto}"
        itemView.txtTotalListaPedido.text="Total : ${part.montoTotal.toString()}"
        itemView.setOnClickListener { clickListener(part)}
    }
}