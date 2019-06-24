package com.bqg.ventas.ui.View

import android.app.Activity
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.View
import com.bqg.ventas.Entidades.Cliente
import com.bqg.ventas.data.ClienteDia
import kotlinx.android.synthetic.main.item_cliente.view.*
import android.graphics.PorterDuff



class ClienteView(view: View) : RecyclerView.ViewHolder(view) {
    fun bind(clientesDia:List<ClienteDia> , part: Cliente, clickListener: (Cliente) -> Unit) {

        for(itemClienteDia in clientesDia){
            if(itemClienteDia.IDCliente==part.IDCliente){
                itemView.labelNombreClienteBusca.setTextColor(Color.parseColor("#FF0000"))
                itemView.labelDocumentoClienteBusca.setTextColor(Color.parseColor("#FF0000"))
                itemView.labelNumeroDocumentoClienteBusca.setTextColor(Color.parseColor("#FF0000"))
                break
            }
        }

        var helper= com.bqg.ventas.Utiles.Helper()
        itemView.labelNombreClienteBusca.text = part.NombreCompleto
        itemView.labelDocumentoClienteBusca.text = part.DI
        itemView.labelNumeroDocumentoClienteBusca.text=part.Documento
        itemView.labelTipoClienteBusca.text=part.TipoCliente
        itemView.labelLineaCreditoBusca.text=helper.formateaMonedaSoles(part.MontoLineaCredito)
        itemView.labelDireccionClienteBusca.text=part.ClienteDireccion
        itemView.setOnClickListener { clickListener(part)}
    }



}