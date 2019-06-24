package com.bqg.ventas.ui.Fragment

import android.app.AlertDialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.content.DialogInterface
import com.bqg.ventas.Entidades.*
import com.bqg.ventas.ui.Activity.PedidoActivity
import com.bqg.ventas.R
import com.bqg.ventas.Utiles.Helper
import com.bqg.ventas.Utiles.Prefs
import com.bqg.ventas.ui.Adapter.ProductoAdapter
import com.bqg.ventas.ui.Adapter.UnidadAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ArticulosPedidoFragment: Fragment() {
    var btnBuscarProducto:Button?=null
    var txtCodigoProducto:TextView?=null
    var txtNombreProducto:TextView?=null
    var rdgGroupSeccionUnidad:RadioGroup?=null
    var btnSelecionarUnidad:Button?=null
    var txtUnidadProducto:TextView?=null
    var txtCantidadProducto:EditText?=null
    var labelStockActual:TextView?=null
    var labelStockNuevo:TextView?=null
    var labelPrecioUnitario:TextView?=null
    var labelPrecioTotal:TextView?=null
    var btnAgregarCarrito:Button?=null

    var recyclerViewProductos: RecyclerView? = null
    var recyclerUnidadPrecio:RecyclerView? = null
    var alertDialog: AlertDialog? = null

    var prefs: Prefs? = null
    var helper= Helper()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var viewCabeceraProducto=inflater!!.inflate(R.layout.fragment_articulos_pedido, container, false)
        InicializaVariables(viewCabeceraProducto)

        CargarInformacionFragmet()
        EventosControles()
        return viewCabeceraProducto
    }

    fun InicializaVariables(view:View){
        prefs = Prefs(activity!!)
        btnBuscarProducto=view.findViewById(R.id.btnBuscarProducto)
        txtCodigoProducto=view.findViewById(R.id.txtCodigoProducto)
        txtNombreProducto=view.findViewById(R.id.txtNombreProducto)
        rdgGroupSeccionUnidad=view.findViewById(R.id.rdgGroupSeccionUnidad)
        btnSelecionarUnidad=view.findViewById(R.id.btnSelecionarUnidad)
        txtUnidadProducto=view.findViewById(R.id.txtUnidadProducto)
        txtCantidadProducto=view.findViewById(R.id.txtCantidadProducto)
        labelStockActual=view.findViewById(R.id.labelStockActual)
        labelStockNuevo=view.findViewById(R.id.labelStockNuevo)
        labelPrecioUnitario=view.findViewById(R.id.labelPrecioUnitario)
        labelPrecioTotal=view.findViewById(R.id.labelPrecioTotal)
        btnAgregarCarrito=view.findViewById(R.id.btnAgregarCarrito)
    }

    fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)

    fun CargarInformacionFragmet(){
        limpiarControles()
        var itemPedido = (activity as PedidoActivity).itemPedidoActualActivity
        var cantidadIngresada=""
        if(itemPedido!!.cantidad>0){
            cantidadIngresada=itemPedido!!.cantidad.toString()
        }

        txtCantidadProducto!!.text=cantidadIngresada.toEditable()
        if (itemPedido!!.producto!=null){
            rdgGroupSeccionUnidad!!.visibility=View.VISIBLE
            txtCodigoProducto!!.text=itemPedido.producto!!.IDProductoAlmacen.toString()
            txtNombreProducto!!.text=itemPedido.producto!!.Descripcion
        }
        if(itemPedido!!.unidad!=null){
            txtUnidadProducto!!.text=itemPedido.unidad!!.Descripcion
            cargarTotales()
        }
    }

    fun cargarTotales(){
        var itemPedido = (activity as PedidoActivity).itemPedidoActualActivity
        if(itemPedido!!.producto!=null){
            if(itemPedido!!.unidad!=null){
                labelStockActual!!.text= helper.formateaDecimal(itemPedido.unidad!!.Stock)
                labelStockNuevo!!.text=helper.formateaDecimal(itemPedido.stockNuevo)
                labelPrecioUnitario!!.text=  helper.formateaDecimalPrecio(itemPedido!!.precioUnitario)
                labelPrecioTotal!!.text=helper.formateaMonedaSoles(itemPedido!!.precioTotal)
            }
        }else{
            labelStockActual!!.text=""
            labelPrecioUnitario!!.text=""
            labelPrecioTotal!!.text=""
        }
    }

    fun limpiarControles(){
        txtCodigoProducto!!.text=""
        txtNombreProducto!!.text=""
        rdgGroupSeccionUnidad!!.visibility=View.GONE
        txtUnidadProducto!!.text=""
        txtCantidadProducto!!.setText("")
        btnAgregarCarrito!!.visibility=View.GONE
        labelPrecioTotal!!.text=""
        labelPrecioUnitario!!.text=""
        labelStockNuevo!!.text=""
        labelStockActual!!.text=""
    }

    fun EventosControles(){
        btnBuscarProducto!!.setOnClickListener {
            alertaSelecionarProducto()
        }

        btnSelecionarUnidad!!.setOnClickListener{
            alertaSelecionarUnidad()
        }

        btnAgregarCarrito!!.setOnClickListener{
            confirmacionPedido()
        }

        txtCantidadProducto!!.addTextChangedListener( object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                var valor=0.00
                if(s.toString()!=""){
                    valor=s.toString().toDouble()
                }
                (activity as PedidoActivity).itemPedidoActualActivity!!.cantidad=valor
                (activity as PedidoActivity).itemPedidoActualActivity!!.ObtenerPrecio()

                if((activity as PedidoActivity).itemPedidoActualActivity!!.stockNuevo<0.0){
                    txtCantidadProducto!!.error="Adventencia!! Cantidad ingresada supera stock actual"
                }

                habiltarBotonCarritoCompras()
                cargarTotales()
            }
        }
        )
    }


    fun agregarItemCarrito(){
        if((activity as PedidoActivity).itemPedidoActualActivity!!.grabado){
            (activity as PedidoActivity).pedidoActualActivity!!.modificarItem((activity as PedidoActivity).itemPedidoActualActivity!!)
        }else{
            (activity as PedidoActivity).pedidoActualActivity!!.agregarItem((activity as PedidoActivity).itemPedidoActualActivity!!)
        }

        var itemPedido=ItemPedido()
        (activity as PedidoActivity).itemPedidoActualActivity=itemPedido
        limpiarControles()
    }

    fun confirmacionPedido(){
        val builder = AlertDialog.Builder(this.context)
        builder.setTitle("Confirmar producto")
        builder.setMessage("Desea agregar producto al carrito de compras")

        val dialogClickListener = DialogInterface.OnClickListener{_,which ->
            when(which){
                DialogInterface.BUTTON_POSITIVE -> agregarItemCarrito()
            }
        }

        builder.setPositiveButton("CONFIRMAR",dialogClickListener)
        builder.setNeutralButton("CANCELAR",dialogClickListener)

        val dialog = builder.create()

        dialog.show()

    }

    var txtBuscarProducto:EditText?=null
    var btnBuscarProductoAlert:ImageButton?=null
    private fun alertaSelecionarProducto(){
        val dialog = AlertDialog.Builder(this.context)
        val view = layoutInflater.inflate(R.layout.alert_producto, null)

        btnBuscarProductoAlert = view.findViewById(R.id.btnBuscarProductoAlert) as ImageButton
        txtBuscarProducto = view.findViewById(R.id.txtBuscarProducto) as EditText

        recyclerViewProductos=view.findViewById(R.id.recyclerProductos) as RecyclerView
        recyclerViewProductos!!.setLayoutManager(LinearLayoutManager(view.context))

        dialog.setView(view)
        dialog.setCancelable(true)

        alertDialog = dialog.create()
        alertDialog!!.show()

        btnBuscarProductoAlert!!.setOnClickListener {
            ObtenerProductosWebService()
        }
    }

    fun ObtenerProductosWebService(){
        var retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(prefs!!.UrlServicioWeb)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        var api = retrofit.create(Api::class.java)
        var call = api.obtenerProductos(txtBuscarProducto!!.text.toString(), prefs!!.idAlmacen)
        btnBuscarProductoAlert!!.visibility=View.INVISIBLE
        //loadingCliente!!.visibility=View.VISIBLE

        (activity as PedidoActivity).mostarModalLoading(true)
        call.enqueue(
            object : Callback<ListaProductos> {
                override fun onFailure(call: Call<ListaProductos>, t: Throwable) {
                    MostrarAlerta(t.toString())
                    btnBuscarProductoAlert!!.visibility=View.VISIBLE
                    (activity as PedidoActivity).mostarModalLoading(false)
                }

                override fun onResponse(call: Call<ListaProductos>, response: Response<ListaProductos>) {
                    var productosObtenidos = response?.body()
                    var productoAdapter = ProductoAdapter(
                        productosObtenidos!!.productos!!,
                        view!!.context,
                        { partItem: Producto -> partItemProductoClicked(partItem) })
                    recyclerViewProductos!!.adapter=productoAdapter
                    productoAdapter.notifyDataSetChanged()
                    btnBuscarProductoAlert!!.visibility=View.VISIBLE
                    (activity as PedidoActivity).mostarModalLoading(false)
                }
            }
        )
    }

    fun MostrarAlerta(mensaje:String){
        Toast.makeText(this.context, "Error de WebSerce: ${mensaje}", Toast.LENGTH_LONG).show()
    }

    fun alertaSelecionarUnidad(){
        val dialog = AlertDialog.Builder(this.context)
        val view = layoutInflater.inflate(R.layout.alert_unidad_precio, null)

        recyclerUnidadPrecio=view.findViewById(R.id.recyclerUnidadPrecio) as RecyclerView
        recyclerUnidadPrecio!!.setLayoutManager(LinearLayoutManager(view.context))

        ObtenerUnidadesWebService()

        dialog.setTitle("Seleccione una unidad")
        dialog.setView(view)
        dialog.setCancelable(true)

        alertDialog = dialog.create()
        alertDialog!!.show()
    }

    fun ObtenerUnidadesWebService(){
        var retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(prefs!!.UrlServicioWeb)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        var api = retrofit.create(Api::class.java)

        var pedidoActual=(activity as PedidoActivity).pedidoActualActivity!!
        var IDCliente=0
        var IDTipoCliente=0

        if(pedidoActual.cliente!=null){
            IDCliente=pedidoActual!!.cliente!!.IDCliente
            IDTipoCliente=pedidoActual!!.cliente!!.IDTipoCliente
        }


        (activity as PedidoActivity).mostarModalLoading(true)


        var call = api.obtenerUnidades(
            IDCliente,
            IDTipoCliente,
            pedidoActual!!.esCredito!!,
            (activity as PedidoActivity).itemPedidoActualActivity!!.producto!!.IDProductoAlmacen ,
            prefs!!.idAlmacen)

        call.enqueue(
            object : Callback<ListaUnidades> {
                override fun onFailure(call: Call<ListaUnidades>, t: Throwable) {
                    MostrarAlerta(t.toString())
                    (activity as PedidoActivity).mostarModalLoading(false)
                }
                override fun onResponse(call: Call<ListaUnidades>, response: Response<ListaUnidades>) {
                    var unidadesObtenidos = response?.body()
                    var unidadAdapter = UnidadAdapter(
                        unidadesObtenidos!!.unidades!!,
                        view!!.context,
                        { partItem: Unidad -> partItemUnidadClicked(partItem) })
                    recyclerUnidadPrecio!!.adapter=unidadAdapter
                    unidadAdapter.notifyDataSetChanged()
                    (activity as PedidoActivity).mostarModalLoading(false)
                }
            }
        )
    }

    fun ObtenerEscalaWebService(){
        var retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(prefs!!.UrlServicioWeb)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        var api = retrofit.create(Api::class.java)
        var call = api.obtenerEscalasUnidad(
            (activity as PedidoActivity).itemPedidoActualActivity!!.unidad!!.IDUnidadItemListaPrecio,
            (activity as PedidoActivity).pedidoActualActivity!!.esCredito!!)
        call.enqueue(
            object : Callback<ListaUnidadEscala> {
                override fun onFailure(call: Call<ListaUnidadEscala>, t: Throwable) {
                    MostrarAlerta(t.toString())
                }
                override fun onResponse(call: Call<ListaUnidadEscala>, response: Response<ListaUnidadEscala>) {
                    var unidadesObtenidos = response?.body()
                    (activity as PedidoActivity).itemPedidoActualActivity!!.unidad!!.escalas=unidadesObtenidos!!.escalas
                }
            }
        )
    }


    fun habiltarBotonCarritoCompras(){
        var resultado:Boolean=true
        if(txtCodigoProducto!!.text.toString()==""){
            resultado=false
        }else if(txtCantidadProducto!!.text.toString()==""){
            resultado=false
        }else if(txtUnidadProducto!!.text.toString()=="") {
            resultado = false
        }
        if(resultado){
            btnAgregarCarrito!!.visibility=View.VISIBLE
        }else{
            btnAgregarCarrito!!.visibility=View.INVISIBLE
        }
    }

    fun partItemProductoClicked(partItem : Producto) {
        (activity as PedidoActivity).itemPedidoActualActivity!!.EstablecerProducto(partItem)
        CargarInformacionFragmet()
        alertDialog!!.dismiss()
        alertaSelecionarUnidad()
    }

    fun partItemUnidadClicked(partItem : Unidad){
        (activity as PedidoActivity).itemPedidoActualActivity!!.unidad=partItem
        if(partItem.TieneEscalas==1){
            ObtenerEscalaWebService()
        }
        CargarInformacionFragmet()
        alertDialog!!.dismiss()
    }

}