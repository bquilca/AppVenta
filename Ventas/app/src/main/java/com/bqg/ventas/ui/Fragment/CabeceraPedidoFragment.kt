package com.bqg.ventas.ui.Fragment

import android.app.AlertDialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.GestureDetector
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.bqg.ventas.Entidades.Api
import com.bqg.ventas.Entidades.Cliente
import com.bqg.ventas.Entidades.ListaClientes
import com.bqg.ventas.Entidades.Pedido
import com.bqg.ventas.ui.Activity.PedidoActivity
import com.bqg.ventas.R
import com.bqg.ventas.ui.Adapter.ClienteAdapter
import com.bqg.ventas.Utiles.Helper
import com.bqg.ventas.Utiles.Prefs
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * A fragment with a Google +1 button.
 * Activities that contain this fragment must implement the
 * [CabeceraPedidoFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [CabeceraPedidoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CabeceraPedidoFragment : Fragment() {
    //Datos de Pantalla
    private var btnBuscarCliente:Button?=null
    private var switchClienteReferencial:Switch?=null

    private var rdgGroupSeccionCliente:RadioGroup?=null
    private var txtDocumento:TextView?=null
    private var txtNombreCliente: TextView? = null
    private var txtTipoCliente:TextView?=null

    private var rdgGroupSeccionCredito:RadioGroup?=null
    private var checkBoxCredito:CheckBox?=null
    private var labelLineaCredito:TextView?=null
    private var labelMontoDeuda:TextView?=null
    private var labelDiasCredito:TextView?=null
    private var labelFechaVencimiento:TextView?=null

    var rdgGroupSeccionClienteReferencial:RadioGroup?=null
    var txtDocumentoClienteReferencial:EditText?=null
    var txtNombreClienteReferencial:EditText?=null
    var txtDireccionClienteReferencial:EditText?=null

    var helper:Helper=Helper()
    var  prefs:Prefs?=null
    //Popup Clientes
    private var recyclerViewClientes: RecyclerView? = null
    private var alertDialog: AlertDialog? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var viewCabeceraPedido=inflater!!.inflate(R.layout.fragment_cabecera_pedido, container, false)
        InicializaVariables(viewCabeceraPedido)
        return viewCabeceraPedido
    }

    fun InicializaVariables(viewCabeceraPedido:View){
        prefs = Prefs(activity!!)
        btnBuscarCliente=viewCabeceraPedido.findViewById(R.id.btnBuscarCliente)
        switchClienteReferencial=viewCabeceraPedido.findViewById(R.id.switchClienteReferencial)

        rdgGroupSeccionCliente=viewCabeceraPedido.findViewById(R.id.rdgGroupSeccionCliente)
        txtDocumento=viewCabeceraPedido.findViewById(R.id.txtDocumento)
        txtNombreCliente = viewCabeceraPedido.findViewById(R.id.txtNombreCliente)
        txtTipoCliente=viewCabeceraPedido.findViewById(R.id.txtTipoCliente)

        rdgGroupSeccionCredito=viewCabeceraPedido.findViewById(R.id.rdgGroupSeccionCredito)
        checkBoxCredito=viewCabeceraPedido.findViewById(R.id.checkBoxCredito)
        labelLineaCredito=viewCabeceraPedido.findViewById(R.id.labelLineaCredito)
        labelMontoDeuda=viewCabeceraPedido.findViewById(R.id.labelMontoDeuda)
        labelDiasCredito=viewCabeceraPedido.findViewById(R.id.labelDiasCredito)
        labelFechaVencimiento=viewCabeceraPedido.findViewById(R.id.labelFechaVencimiento)

        rdgGroupSeccionClienteReferencial=viewCabeceraPedido.findViewById(R.id.rdgGroupSeccionClienteReferencial)
        txtDocumentoClienteReferencial=viewCabeceraPedido.findViewById(R.id.txtDocumentoClienteReferencial)
        txtNombreClienteReferencial=viewCabeceraPedido.findViewById(R.id.txtNombreClienteReferencial)
        txtDireccionClienteReferencial=viewCabeceraPedido.findViewById(R.id.txtDireccionClienteReferencial)

        eventosFragment()
        CargarInformacionFragmet()
    }


    fun eventosFragment(){
        switchClienteReferencial!!.setOnCheckedChangeListener { buttonView, isChecked ->
            (activity as PedidoActivity).pedidoActualActivity!!.setClienteReferencial(isChecked)
            CargarInformacionFragmet()
        }
        btnBuscarCliente!!.setOnClickListener {
            alertaSelecionarCliente()
        }
        checkBoxCredito!!.setOnCheckedChangeListener { buttonView, isChecked ->
            (activity as PedidoActivity).pedidoActualActivity!!.esCredito=isChecked
            CargarInformacionFragmet()
        }

        txtDocumentoClienteReferencial!!.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                (activity as PedidoActivity).pedidoActualActivity!!.documentoClienteReferencial=s.toString()
            }
        }
        )

        txtNombreClienteReferencial!!.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                (activity as PedidoActivity).pedidoActualActivity!!.nombreClienteReferencial=s.toString()
            }

        })

        txtDireccionClienteReferencial!!.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                (activity as PedidoActivity).pedidoActualActivity!!.direccionClienteReferencial=s.toString()
            }
        })
    }

    fun CargarInformacionFragmet() {
        var pedido = (activity as PedidoActivity).pedidoActualActivity!!
        if (pedido.esClienteReferencial!!) {
            rdgGroupSeccionCliente!!.visibility=View.GONE
            rdgGroupSeccionCredito!!.visibility=View.GONE
            rdgGroupSeccionClienteReferencial!!.visibility=View.VISIBLE
        } else {
            rdgGroupSeccionCliente!!.visibility=View.VISIBLE
            rdgGroupSeccionClienteReferencial!!.visibility=View.GONE
        }
        CargarInformacionCliente(pedido)
    }

    fun CargarInformacionCliente(pedido: Pedido){
        if(pedido.cliente!=null){
            txtDocumento!!.text=pedido.cliente!!.Documento
            txtNombreCliente!!.text=pedido.cliente!!.NombreCompleto
            txtTipoCliente!!.text=pedido.cliente!!.TipoCliente
            checkBoxCredito!!.isChecked=pedido.esCredito!!
            if(pedido.cliente!!.TieneLineaCredito){
                rdgGroupSeccionCredito!!.visibility=View.VISIBLE
                CargarInformacionLineaCredito(pedido)
            }else{
                rdgGroupSeccionCredito!!.visibility=View.GONE
            }
        }else{
            txtDocumento!!.text=""
            txtNombreCliente!!.text=""
            txtTipoCliente!!.text=""
            txtDocumentoClienteReferencial!!.setText(pedido.documentoClienteReferencial)
            txtNombreClienteReferencial!!.setText(pedido.nombreClienteReferencial)
            txtDireccionClienteReferencial!!.setText(pedido.direccionClienteReferencial)
            rdgGroupSeccionCredito!!.visibility=View.GONE
        }
    }

    fun CargarInformacionLineaCredito(pedido: Pedido){
        labelLineaCredito!!.text=helper.formateaMonedaSoles(pedido.cliente!!.MontoLineaCredito)
        labelMontoDeuda!!.text= helper.formateaMonedaSoles(pedido.cliente!!.MontoLineaCredito - pedido.cliente!!.SaldoCredito)
        if(pedido.esCredito!!){
            labelDiasCredito!!.text=pedido.cliente!!.DiasCredito.toString()
            var fechaActual=helper.obtenerFechaHoraActual(pedido.cliente!!.DiasCredito)
            labelFechaVencimiento!!.text=helper.obtenerTextoFecha(fechaActual)
        }else{
            labelFechaVencimiento!!.text=""
            labelDiasCredito!!.text=""
        }
    }


    var txtBuscarCliente:EditText?=null
    var loadingCliente:ProgressBar?=null
    var btnBuscarClienteAlert:ImageButton?=null
    private fun alertaSelecionarCliente(){
        val dialog = AlertDialog.Builder(this.context)
        val view = layoutInflater.inflate(R.layout.alert_cliente, null)

        btnBuscarClienteAlert = view.findViewById<EditText>(R.id.btnBuscarClienteAlert) as ImageButton
        txtBuscarCliente = view.findViewById<EditText>(R.id.txtBuscarCliente) as EditText
        loadingCliente =view.findViewById<EditText>(R.id.loadingCliente) as ProgressBar

        recyclerViewClientes=view.findViewById(R.id.recyclerClientes) as RecyclerView
        recyclerViewClientes!!.setLayoutManager(LinearLayoutManager(view.context))


        dialog.setView(view)
        dialog.setCancelable(true)

        alertDialog = dialog.create()
        alertDialog!!.show()
        btnBuscarClienteAlert!!.setOnClickListener {
            CargarClientesWebService()
        }
    }

    fun CargarClientesWebService() {
        var retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(prefs!!.UrlServicioWeb)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        var api = retrofit.create(Api::class.java)

        var call = api.obtenerClientes( prefs!!.IDVendedor.toInt(), txtBuscarCliente!!.text.toString())
        Log.d("BQGREQUEST", call.toString() + "")
        btnBuscarClienteAlert!!.visibility=View.INVISIBLE
        loadingCliente!!.visibility=View.VISIBLE
        call.enqueue(
            object :Callback<ListaClientes>{
                override fun onFailure(call: Call<ListaClientes>, t: Throwable) {
                    MostrarAlerta(t.toString())
                    btnBuscarClienteAlert!!.visibility=View.VISIBLE
                    loadingCliente!!.visibility=View.GONE
                }

                override fun onResponse(call: Call<ListaClientes>, response: Response<ListaClientes>) {
                    if (response != null) {
                        var clientesObtenidos = response?.body()
                        var clienteAdapter = ClienteAdapter(
                            (activity as PedidoActivity).clientesDelDia!!,
                            clientesObtenidos?.clientes!!,
                            view!!.context,
                            { partItem: Cliente -> clienteSelecionadoClicked(partItem) })
                        recyclerViewClientes!!.adapter=clienteAdapter
                        clienteAdapter.notifyDataSetChanged()
                    }
                    btnBuscarClienteAlert!!.visibility=View.VISIBLE
                    loadingCliente!!.visibility=View.GONE
                }
            }
        )
    }

    fun MostrarAlerta(mensaje:String){
        Toast.makeText(this.context, "Error de WebSerce: ${mensaje}", Toast.LENGTH_LONG).show()
    }

    private fun clienteSelecionadoClicked(partItem : Cliente) {
        (activity as PedidoActivity).pedidoActualActivity!!.setcliente(partItem)
        CargarInformacionFragmet()
        alertDialog!!.dismiss()
    }
}