package com.bqg.ventas.ui.View

import android.support.v7.widget.RecyclerView
import android.view.View
import com.bqg.ventas.Entidades.Cliente
import kotlinx.android.synthetic.main.item_cliente.view.*

class ClienteView(view: View) : RecyclerView.ViewHolder(view) {
    fun bind(part: Cliente, clickListener: (Cliente) -> Unit) {
        var helper= com.bqg.ventas.Utiles.Helper()
        itemView.labelNombreClienteBusca.text = part.NombreCompleto
        itemView.labelDocumentoClienteBusca.text = part.DI
        itemView.labelNumeroDocumentoClienteBusca.text=part.Documento
        itemView.labelTipoClienteBusca.text=part.TipoCliente
        itemView.labelLineaCreditoBusca.text=helper.formateaMonedaSoles(part.MontoLineaCredito)
        itemView.setOnClickListener { clickListener(part)}
    }

}