package com.bqg.ventas.ui.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bqg.ventas.Entidades.Pedido
import com.bqg.ventas.R
import com.bqg.ventas.ui.View.PedidoView
import java.util.ArrayList

class PedidoAdapter(val items:ArrayList<Pedido>, val context: Context, val clickListener: (Pedido) -> Unit) : RecyclerView.Adapter<PedidoView>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): PedidoView {
        return PedidoView(
            LayoutInflater.from(context).inflate(
                R.layout.item_lista_pedidos,
                p0,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(p0: PedidoView, p1: Int) {
        var item = items[p1]
        p0.bind(item, clickListener)
    }
}