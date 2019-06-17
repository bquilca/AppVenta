package com.bqg.ventas.ui.View

import android.support.v7.widget.RecyclerView
import android.view.View
import com.bqg.ventas.Entidades.Producto
import kotlinx.android.synthetic.main.item_producto_alert.view.*

class ProductoView (view: View) : RecyclerView.ViewHolder(view) {
    fun bind(part: Producto, clickListener: (Producto) -> Unit) {
        itemView.txtCodigoProductoBUsca.text = part.Cod_Prod
        itemView.txtNombreProductoBusca.text = part.Descripcion
        itemView.setOnClickListener { clickListener(part)}
    }
}