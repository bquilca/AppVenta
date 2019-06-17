package com.bqg.ventas.ui.View

import android.support.v7.widget.RecyclerView
import android.view.View
import com.bqg.ventas.Entidades.ItemPedido
import com.bqg.ventas.Utiles.Helper
import kotlinx.android.synthetic.main.item_carrito_compras.view.*

class CarritoView (view: View) : RecyclerView.ViewHolder(view) {
    var helper= Helper()

    fun bind(part: ItemPedido, clickListener: (ItemPedido) -> Unit) {
        itemView.txtNombreProductoCarrito.text = "${part.producto!!.Cod_Prod} | ${part.producto!!.Descripcion}"
        itemView.txtCantidadCarrito.text = helper.formateaDecimal(part.cantidad)
        itemView.txtUnidadProductoCarrito.text = part.unidad!!.Descripcion
        itemView.txtPrecioUnitarioCarrito.text = helper.formateaDecimal(part.precioUnitario)
        itemView.txtPrecioTotalCarrito.text = helper.formateaDecimal(part.precioTotal)
        itemView.setOnClickListener { clickListener(part)}
    }

}