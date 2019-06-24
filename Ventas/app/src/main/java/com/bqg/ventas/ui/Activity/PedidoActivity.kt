package com.bqg.ventas.ui.Activity

import android.content.Context
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.*
import android.view.inputmethod.InputMethodManager
import com.bqg.ventas.Entidades.ItemPedido
import com.bqg.ventas.Entidades.Pedido
import com.bqg.ventas.R
import com.bqg.ventas.TomaPedidosApp
import com.bqg.ventas.Utiles.Helper
import com.bqg.ventas.Utiles.Prefs
import com.bqg.ventas.data.ClienteDia
import com.bqg.ventas.ui.Adapter.SectionsPagerAdapter
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread


class PedidoActivity : AppCompatActivity() {
    var tabLayout: TabLayout? = null
    var viewPager: ViewPager? = null
    var pedidoActualActivity=Pedido()
    var itemPedidoActualActivity: ItemPedido?=ItemPedido()
    var clientesDelDia: List<ClienteDia>?=null
    var adapterTab: SectionsPagerAdapter?=null
    var prefs:Prefs?=null
    var helper=Helper()

    var alertDialog: android.app.AlertDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pedido)

        //val toolbar = findViewById<Toolbar>(R.id.toolbar)
        //setSupportActionBar(toolbar)

        tabLayout = findViewById(R.id.tabLayout)
        viewPager = findViewById(R.id.view_pager)

        tabLayout!!.addTab(tabLayout!!.newTab().setText("Cliente"))
        tabLayout!!.addTab(tabLayout!!.newTab().setText("Pedido"))
        tabLayout!!.addTab(tabLayout!!.newTab().setText("Carrito de Compras"))

        tabLayout!!.tabGravity = TabLayout.GRAVITY_FILL

        setTitle("Nuevo Pedido")
        adapterTab = SectionsPagerAdapter(this, supportFragmentManager, tabLayout!!.tabCount)
        viewPager!!.adapter = adapterTab
        viewPager!!.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))

        val builder = AlertDialog.Builder(this)

        ObtenerClientesConVentasDelDia()
        prefs= Prefs(this)

        tabLayout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager!!.currentItem = tab.position
                if(tab.position==1){
                    if(!pedidoActualActivity!!.datosClienteCompleto()){
                        builder.setTitle("Debe registrar datos de Cliente")
                        builder.setMessage("Debe llenar datos de cliente")
                        builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                        }
                        builder.show()
                        viewPager!!.currentItem = 0
                    }
                }
                if(tab.position==2){
                    if(!pedidoActualActivity!!.datosClienteCompleto()){
                        builder.setTitle("Debe registrar datos de Cliente")
                        builder.setMessage("Debe llenar datos de cliente")
                        builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                        }
                        builder.show()
                        viewPager!!.currentItem = 0
                    }
                    else if(!ValidarTabCarrito()){
                        builder.setTitle("Debe ingresar productos al carrito")
                        builder.setMessage("Debe ingresar productos al carrito")
                        builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                        }
                        builder.show()
                        viewPager!!.currentItem = 1
                    }
                    else{
                    viewPager!!.adapter = adapterTab
                    viewPager!!.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
                    viewPager!!.adapter!!.notifyDataSetChanged()
                    viewPager!!.currentItem = tab.position
                    }
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {

            }
            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })
    }

    private fun ObtenerClientesConVentasDelDia(){
        doAsync {
            clientesDelDia = TomaPedidosApp.database.pedidoDao().getClientesConVentas(helper.obtenerFechaActualTexto(),prefs!!.usuario)
            uiThread {
            }
        }
    }

    private fun ValidarTabCarrito():Boolean{
        var resultado:Boolean=false
        if(pedidoActualActivity!!.itemsPedido!!.size>0){
            resultado=true
        }
        return  resultado
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }

    fun tabModificarProducto(){
        viewPager!!.adapter = adapterTab
        viewPager!!.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        viewPager!!.adapter!!.notifyDataSetChanged()
        viewPager!!.currentItem = 1
    }


    fun alertaModal(){
        val dialog = android.app.AlertDialog.Builder(this)
        val view = layoutInflater.inflate(R.layout.modal_espera, null)

        dialog.setView(view)
        dialog.setCancelable(false)

        alertDialog = dialog.create()
        alertDialog!!.show()
    }

    fun mostarModalLoading(mostrar:Boolean){
        if(mostrar){
            if(alertDialog==null){
                alertaModal()
            }else{
                alertDialog!!.show()
            }
        }else{
            if(alertDialog!=null){
                alertDialog!!.cancel()
            }
        }
    }
}

