package com.bqg.ventas.ui.Fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.bqg.ventas.Utiles.Prefs
import com.bqg.ventas.ui.Adapter.CarritoAdapter
import com.bqg.ventas.Utiles.Helper
import com.google.gson.GsonBuilder
import android.location.LocationManager
import android.content.DialogInterface
import android.content.Intent
import android.provider.Settings
import com.bqg.ventas.ui.Activity.PedidoActivity
import com.bqg.ventas.R
import com.bqg.ventas.TomaPedidosApp
import com.bqg.ventas.data.PedidoEntity
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.support.v4.content.ContextCompat.getSystemService
import com.bqg.ventas.Entidades.*
import com.bqg.ventas.ui.Activity.LoginActivity
import com.bqg.ventas.ui.Activity.MainActivity

class CarritoComprasFragment : Fragment() {
    //Interfaces
    var txtClienteCarrito:TextView?=null
    var txtSubtTotalCarrito:TextView?=null
    var txtAfectoCarrito:TextView?=null
    var txtTotalExoneradoCarrito:TextView?=null
    var txtIgvCarrito:TextView?=null
    var txtDescuentoCarrito:TextView?=null
    var txtTotalCarrito:TextView?=null
    var btnGrabar:Button?=null

    //Detalle Carrito
    var recyclerViewItemCarrito: RecyclerView? = null
    var viewCarritoCompras:View?=null
    var carritoAdapter: CarritoAdapter?=null

    var prefs: Prefs? = null
    var help=Helper()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        // Inflate the layout for this fragment

        myClipboard =  activity!!.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager?

        viewCarritoCompras=inflater!!.inflate(R.layout.fragment_carrito_compras, container, false)
        recyclerViewItemCarrito= this.viewCarritoCompras!!.findViewById(R.id.recyclerViewItemCarrito) as RecyclerView
        recyclerViewItemCarrito!!.setLayoutManager(LinearLayoutManager(viewCarritoCompras!!.context))

        carritoAdapter = CarritoAdapter(
            (activity as PedidoActivity).pedidoActualActivity!!.itemsPedido!!,
            viewCarritoCompras!!.context,
            { partItem: ItemPedido -> partItemClicked(partItem) })
        recyclerViewItemCarrito!!.adapter=carritoAdapter
        carritoAdapter!!.notifyDataSetChanged()

        InicializaVariables(viewCarritoCompras!!)
        EventosControles()

        CargarInformacionFragmet()

        //locationManager =  activity!!.getSystemService(Context.LOCATION_SERVICE) as LocationManager?

        return viewCarritoCompras
    }


    fun InicializaVariables(view:View){
        prefs = Prefs(activity!!)
        txtClienteCarrito=view.findViewById(R.id.txtClienteCarrito)
        txtSubtTotalCarrito=view.findViewById(R.id.txtSubtTotalCarrito)
        txtAfectoCarrito=view.findViewById(R.id.txtAfectoCarrito)
        txtTotalExoneradoCarrito=view.findViewById(R.id.txtTotalExoneradoCarrito)
        txtIgvCarrito=view.findViewById(R.id.txtIgvCarrito)
        txtDescuentoCarrito=view.findViewById(R.id.txtDescuentoCarrito)
        txtTotalCarrito=view.findViewById(R.id.txtTotalCarrito)
        btnGrabar = view.findViewById(R.id.btnGrabar)

    }

    fun EventosControles(){
        btnGrabar!!.setOnClickListener{
            GrabarPedido()
        }
    }

    var locationManager : LocationManager? = null

    var longitudeBest: Double = 0.toDouble()
    var latitudeBest:Double = 0.toDouble()

    fun GrabarPedido(){
        var pedido=(activity as PedidoActivity).pedidoActualActivity!!
        pedido.IDVendedor=prefs!!.IDVendedor
        pedido.idTipoDocumento= prefs!!.IDDocumento
        val jsonPedido: String = ObtenerPedidoJSON()

        val builder = AlertDialog.Builder(this.context!!)
        builder.setTitle("Pedido")
        builder.setMessage("Desea grabar el pedido : ${jsonPedido}")

        builder.setPositiveButton("Grabar") { dialog, which ->
            //checkLocation()
            grabarPedidoWebService()
        }

        builder.setNegativeButton("CopiarJSON"){dialog, which ->
            CompartirJSON(jsonPedido)
        }

        builder.show()

    }


    fun ObtenerPedidoJSON() : String{
        var pedido=(activity as PedidoActivity).pedidoActualActivity!!
        var pedidoJSON=PedidoJSON()

        pedidoJSON.codigoVendedor=pedido.IDVendedor
        pedidoJSON.idTipoDocumento= pedido.idTipoDocumento

        pedidoJSON.esClienteReferencial=pedido.esClienteReferencial
        if (pedidoJSON.esClienteReferencial){
            pedidoJSON.nombreClienteReferencial=pedido.nombreClienteReferencial
            pedidoJSON.direccionClienteReferencial=pedido.direccionClienteReferencial
            pedidoJSON.documentoClienteReferencial=pedido.documentoClienteReferencial
        }else{
            pedidoJSON.IDCliente=pedido.cliente!!.IDCliente
            pedidoJSON.esCredito=pedido.esCredito
        }



        var itemsJSON=ArrayList<ItemPedidoJSON>()

        for(item in pedido.itemsPedido!!){
            var itemPedidoJSON=ItemPedidoJSON()
            itemPedidoJSON.IDProductoAlmacen=item.producto!!.IDProductoAlmacen
            itemPedidoJSON.IDUnidadItemListaPrecio=item.unidad!!.IDUnidadItemListaPrecio
            itemPedidoJSON.cantidad=item.cantidad
            itemPedidoJSON.precioUnitario=item.precioUnitario
            itemPedidoJSON.precioTotal=item.precioTotal
            itemsJSON.add(itemPedidoJSON)
        }

        pedidoJSON.itemsPedido=itemsJSON

        pedidoJSON.longitudeGPS=pedido.longitudeGPS
        pedidoJSON.latitudeGPS=pedido.latitudeGPS

        pedidoJSON.montoSubTotal=pedido.montoSubTotal
        pedidoJSON.montoAfecto=pedido.montoAfecto
        pedidoJSON.montoTotalExonerado=pedido.montoTotalExonerado
        pedidoJSON.montoDescuento=pedido.montoDescuento
        pedidoJSON.montoIGV=pedido.montoIGV
        pedidoJSON.montoTotal=pedido.montoTotal


        val gson = GsonBuilder().setPrettyPrinting().create()
        return gson.toJson(pedidoJSON)
    }

    private var myClipboard: ClipboardManager? = null
    private var myClip: ClipData? = null
    fun CompartirJSON(jsonPedido:String){
        myClip = ClipData.newPlainText("text", jsonPedido)
        myClipboard?.setPrimaryClip(myClip)

        MostrarAlerta("Se copio JSON al portapeles")

    }


    fun grabarPedidoInterno(pedido:PedidoEntity){
        doAsync {
            val id = TomaPedidosApp.database.pedidoDao().agregarPedido(pedido)
            //val recoveryTask = TomaPedidosApp.database.pedidoDao().getTaskById(id)
            uiThread {
                RegresarAPrincipal()
            }
        }

    }

    fun grabarPedidoWebService(){
        val gson = GsonBuilder().setPrettyPrinting().create()
        var pedido=(activity as PedidoActivity).pedidoActualActivity!!

        var retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(prefs!!.UrlServicioWeb)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        var api = retrofit.create(Api::class.java)
        var call = api.grabarPedido(ObtenerPedidoJSON())


        call.enqueue(
            object : Callback<PedidoNegocio> {
                override fun onFailure(call: Call<PedidoNegocio>, t: Throwable) {
                    MostrarAlerta("Error Grabar Pedido WebService: ${t.toString()}")
                }
                override fun onResponse(call: Call<PedidoNegocio>, response: Response<PedidoNegocio>) {
                    var pedidoPOST = response?.body()
                    pedido.numeroDocumento=pedidoPOST!!.numeroPedido

                    MostrarAlerta("Se grabo exitorsamente el pedido con número : ${pedidoPOST.numeroPedido}")


                    val jsonPedido: String = gson.toJson(pedido)
                    var pedidoSQL=PedidoEntity()
                    pedidoSQL.jsonPedido=jsonPedido
                    pedidoSQL.fechaCreacion=help.obtenerFechaActualTexto()
                    pedidoSQL.usuario=prefs!!.usuario
                    if(!pedido.esClienteReferencial){
                        pedidoSQL.IDCliente=pedido.cliente!!.IDCliente
                    }
                    grabarPedidoInterno(pedidoSQL)
                }
            }
        )
    }


    fun RegresarAPrincipal(){
        activity!!.onBackPressed()
    }


    fun MostrarAlerta(mensaje:String){
        Toast.makeText(this.context, mensaje, Toast.LENGTH_LONG).show()
    }


    private fun isLocationEnabled(): Boolean {
        return locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager!!.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun checkLocation(): Boolean {
        if (!isLocationEnabled())
            showAlert()
        return isLocationEnabled()
    }

    private fun showAlert() {
        val dialog = AlertDialog.Builder(this.context!!)
        dialog.setTitle("Enable Location")
            .setMessage(("Su ubicación esta desactivada.\npor favor active su ubicación " + "usa esta app"))
            .setPositiveButton("Configuración de ubicación"
            ) { paramDialogInterface, paramInt ->
                val myIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(myIntent)
            }
            .setNegativeButton("Cancelar", object:DialogInterface.OnClickListener {
                override fun onClick(paramDialogInterface:DialogInterface, paramInt:Int) {}
            })
        dialog.show()
    }

    fun CargarInformacionFragmet(){
        var pedido=(activity as PedidoActivity).pedidoActualActivity!!
        if(pedido.cliente!=null){
            txtClienteCarrito!!.text="${pedido.cliente!!.Documento} - ${pedido.cliente!!.NombreCompleto}"
        }else{
            txtClienteCarrito!!.text=""
        }
        txtSubtTotalCarrito!!.text= help.formateaDecimal(pedido.montoSubTotal!!)
        txtAfectoCarrito!!.text="0.00"
        txtTotalExoneradoCarrito!!.text="0.00"
        txtIgvCarrito!!.text=help.formateaDecimal(pedido.montoIGV!!)
        txtDescuentoCarrito!!.text="0.00"
        txtTotalCarrito!!.text=help.formateaDecimal(pedido.montoTotal!!)
    }

    private fun partItemClicked(partItem : ItemPedido) {
        val builder = AlertDialog.Builder(this.context!!)
        builder.setTitle("Actualizar Producto")
        builder.setMessage("Que desea realizar con el producto : ${partItem.producto!!.Descripcion}")

        builder.setNegativeButton("ELIMINAR") { dialog, which ->
            (activity as PedidoActivity).pedidoActualActivity!!.eliminarItem(partItem)

            carritoAdapter = CarritoAdapter(
                (activity as PedidoActivity).pedidoActualActivity!!.itemsPedido!!,
                viewCarritoCompras!!.context,
                { partItem: ItemPedido -> partItemClicked(partItem) })
            recyclerViewItemCarrito!!.adapter=carritoAdapter
            carritoAdapter!!.notifyDataSetChanged()

            CargarInformacionFragmet()

            Toast.makeText(this.context,
                "Producto elminado del carrito", Toast.LENGTH_SHORT).show()
        }

        builder.setPositiveButton("MODIFICAR") { dialog, which ->
            (activity as PedidoActivity).itemPedidoActualActivity=partItem
            (activity as PedidoActivity).tabModificarProducto()
        }
        builder.show()
    }

}