package com.bqg.ventas.ui.Adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bqg.ventas.Entidades.ItemPedido
import com.bqg.ventas.R
import com.bqg.ventas.ui.View.CarritoView
import kotlinx.android.synthetic.main.item_carrito_compras.view.*
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

class CarritoAdapter(val items : ArrayList<ItemPedido>, val context: Context, val clickListener: (ItemPedido) -> Unit) : RecyclerView.Adapter<CarritoView>()
{
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CarritoView{
        return CarritoView(
            LayoutInflater.from(context).inflate(
                R.layout.item_carrito_compras,
                p0,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(p0: CarritoView, p1: Int) {
        var item = items[p1]
        p0.bind(item, clickListener)
    }
}
