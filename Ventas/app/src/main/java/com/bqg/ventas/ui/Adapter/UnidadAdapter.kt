package com.bqg.ventas.ui.Adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bqg.ventas.Entidades.Unidad
import com.bqg.ventas.R
import com.bqg.ventas.ui.View.UnidadView
import kotlinx.android.synthetic.main.item_unidad_precio.view.*

class UnidadAdapter(val items : List<Unidad>, val context: Context, val clickListener: (Unidad) -> Unit) : RecyclerView.Adapter<UnidadView>()
{
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): UnidadView {
        return UnidadView(
            LayoutInflater.from(context).inflate(
                R.layout.item_unidad_precio,
                p0,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(p0: UnidadView, p1: Int) {
        var item = items[p1]
        (p0).bind(item, clickListener)
    }
}
