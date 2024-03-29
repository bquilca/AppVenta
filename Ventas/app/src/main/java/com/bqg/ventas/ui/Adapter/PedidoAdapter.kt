package com.bqg.ventas.ui.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bqg.ventas.Entidades.Pedido
import com.bqg.ventas.R
import com.bqg.ventas.data.PedidoDao
import com.bqg.ventas.data.PedidoEntity
import com.bqg.ventas.ui.View.PedidoView
import java.util.ArrayList

class PedidoAdapter(val items:List<PedidoEntity>, val context: Context, val clickListener: (PedidoEntity) -> Unit) : RecyclerView.Adapter<PedidoView>() {
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
        var imagenes= intArrayOf(R.drawable.ic_shortcut_credit_card,R.drawable.ic_shortcut_money)

        p0.bind(imagenes, item, clickListener)
    }
}