package com.bqg.ventas.ui.Adapter


import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bqg.ventas.Entidades.Cliente
import com.bqg.ventas.R
import com.bqg.ventas.data.ClienteDia
import com.bqg.ventas.ui.View.ClienteView
import kotlinx.android.synthetic.main.item_cliente.view.*


class ClienteAdapter(val clientesDia:List<ClienteDia>,  val items : List<Cliente>, val context: Context, val clickListener: (Cliente) -> Unit) : RecyclerView.Adapter<ClienteView>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ClienteView {
        return ClienteView(
            LayoutInflater.from(context).inflate(
                R.layout.item_cliente,
                p0,
                false
            )
        )
    }

    override fun onBindViewHolder(p0: ClienteView, p1: Int) {
        var item = items[p1]
        p0.bind(clientesDia,item, clickListener)
    }

    // Gets the number of animals in the list
    override fun getItemCount(): Int {
        return items.size
    }
}

