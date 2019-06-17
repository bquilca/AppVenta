package com.bqg.ventas.ui.View

import android.support.v7.widget.RecyclerView
import android.view.View
import com.bqg.ventas.Entidades.Unidad
import kotlinx.android.synthetic.main.item_unidad_precio.view.*

class UnidadView (view: View) : RecyclerView.ViewHolder(view) {
    fun bind(part: Unidad, clickListener: (Unidad) -> Unit) {
        itemView.txtUnidadDescripcion.text = part.Descripcion
        itemView.txtUnidadStock.text = part.Stock.toString()
        itemView.txtUnidadPrecio.text=part.Precio.toString()
        itemView.setOnClickListener { clickListener(part)}
    }
}