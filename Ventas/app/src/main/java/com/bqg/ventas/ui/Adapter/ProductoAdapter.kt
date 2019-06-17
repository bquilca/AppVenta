package com.bqg.ventas.ui.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bqg.ventas.Entidades.Producto
import com.bqg.ventas.R
import com.bqg.ventas.ui.View.ProductoView
import kotlinx.android.synthetic.main.item_producto_alert.view.*

class ProductoAdapter (val items : List<Producto>,
                       val context: Context,
                       val clickListener: (Producto) -> Unit)
    : RecyclerView.Adapter<ProductoView>()
{
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ProductoView {
        return ProductoView(
            LayoutInflater.from(context).inflate(
                R.layout.item_producto_alert,
                p0,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(p0: ProductoView, p1: Int) {
        var item = items[p1]
        (p0).bind(item , clickListener)
    }
}

