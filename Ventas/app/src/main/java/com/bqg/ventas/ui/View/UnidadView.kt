package com.bqg.ventas.ui.View

import android.support.v7.widget.RecyclerView
import android.view.View
import com.bqg.ventas.Entidades.Unidad
import kotlinx.android.synthetic.main.item_unidad_precio.view.*

class UnidadView (view: View) : RecyclerView.ViewHolder(view) {
    fun bind(part: Unidad, clickListener: (Unidad) -> Unit) {
        var helper= com.bqg.ventas.Utiles.Helper()
        itemView.txtUnidadDescripcion.text = part.Descripcion
        itemView.txtUnidadStock.text = helper.formateaDecimal(part.Stock)
        itemView.txtUnidadPrecio.text=helper.formateaDecimalPrecio(part.Precio)
        itemView.setOnClickListener { clickListener(part)}
    }
}