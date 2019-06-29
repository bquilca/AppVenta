package com.bqg.ventas.ui.View

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bqg.ventas.Entidades.ItemPedido
import com.bqg.ventas.Utiles.Helper
import kotlinx.android.synthetic.main.item_carrito_compras.view.*

class CarritoView (view: View) : RecyclerView.ViewHolder(view) {
    var helper= Helper()

    fun bind(part: ItemPedido, clickListener: (ItemPedido) -> Unit) {

        itemView.txtCodigoProductoCarrito.text="Codigo: ${part.producto!!.Cod_Prod} "
        itemView.txtNombreProductoCarrito.text = "${part.producto!!.Descripcion}"
        itemView.txtCantidadCarrito.text = "Cant:${helper.formateaDecimal(part.cantidad)}  ${part.unidad!!.Descripcion}  ${helper.formateaDecimalPrecio(part.precioUnitario)}"
        itemView.txtPrecioTotalCarrito.text = "Total: ${helper.formateaMonedaSoles(part.precioTotal)}"
        itemView.setOnClickListener { clickListener(part)}
    }

}