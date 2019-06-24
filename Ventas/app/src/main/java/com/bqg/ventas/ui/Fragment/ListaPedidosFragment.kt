package com.bqg.ventas.ui.Fragment

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bqg.ventas.Entidades.Pedido

import com.bqg.ventas.R
import com.bqg.ventas.TomaPedidosApp
import com.bqg.ventas.Utiles.Helper
import com.bqg.ventas.Utiles.Prefs
import com.bqg.ventas.data.PedidoEntity
import com.bqg.ventas.ui.Activity.MainActivity
import com.bqg.ventas.ui.Activity.PedidoActivity
import com.bqg.ventas.ui.Adapter.PedidoAdapter
import com.google.gson.Gson
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread


class ListaPedidosFragment : Fragment(){
    var recViewListaPedidos:RecyclerView?=null
    var fabNuevoPedido:FloatingActionButton?=null
    var txtEncabezadoPedidos:TextView?=null
    val listaPedidos=ArrayList<Pedido>()
    var vistaListaPedido:View?=null
    var help=Helper()
    var swipeContainer: SwipeRefreshLayout?=null
    var prefs:Prefs?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vistaListaPedido=inflater.inflate(R.layout.fragment_lista_pedidos, container, false)
        activity!!.title="Pedidos del dia"
        InicializaVariables()
        return vistaListaPedido
    }

    fun InicializaVariables(){
        var viewListaPedido=vistaListaPedido!!
        prefs=Prefs(viewListaPedido.context)
        swipeContainer=viewListaPedido.findViewById(R.id.srlContainer)
        swipeContainer!!.setOnRefreshListener {
            listarPedido()
        }

        recViewListaPedidos=viewListaPedido.findViewById(R.id.recViewListaPedidos)
        recViewListaPedidos!!.setLayoutManager(LinearLayoutManager(viewListaPedido.context))

        txtEncabezadoPedidos=viewListaPedido.findViewById(R.id.txtEncabezadoPedidos)

        fabNuevoPedido = viewListaPedido.findViewById(R.id.fabNuevoPedido)
        fabNuevoPedido!!.setOnClickListener { view ->
            val pedido = Intent(viewListaPedido.context, PedidoActivity::class.java)
            startActivity(pedido)
        }

        pedidos=ArrayList()

        (activity as MainActivity).mostarModalLoading(true)

        eliminarPedidosAnteriores()
    }

    lateinit var pedidos: MutableList<PedidoEntity>

    fun listarPedido(){
        doAsync {
            pedidos = TomaPedidosApp.database.pedidoDao().getListaPedidos(prefs!!.usuario)
            uiThread {
                cargarPedidos()
                swipeContainer!!.isRefreshing=false
            }
        }
    }

    fun eliminarPedidosAnteriores(){
        doAsync {
            TomaPedidosApp.database.pedidoDao().eliminarPedidosDiasAnterior(help.obtenerFechaActualTexto())
            uiThread {
                listarPedido()
            }
        }
    }

    fun cargarPedidos(){
        var pedidoSQL= pedidos.toList()
        listaPedidos.clear()
        for(itemPedido in pedidoSQL){
            val pedidoObjeto= Gson().fromJson(itemPedido.jsonPedido, Pedido::class.java )
            listaPedidos.add(pedidoObjeto)
        }

        txtEncabezadoPedidos!!.text="Total ${listaPedidos.size.toString()} pedidos en el día"

        var pedidoAdapter = PedidoAdapter(
            pedidoSQL,
            vistaListaPedido!!.context,
            { partItem: PedidoEntity -> partItemProductoClicked(partItem) })

        recViewListaPedidos!!.adapter=pedidoAdapter
        pedidoAdapter.notifyDataSetChanged()

        (activity as MainActivity).mostarModalLoading(false)

    }

    fun partItemProductoClicked(partItem: PedidoEntity){

    }

}
