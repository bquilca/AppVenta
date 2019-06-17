package com.bqg.ventas.ui.Fragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bqg.ventas.Entidades.UsuarioEmpresa

import com.bqg.ventas.R
import com.bqg.ventas.Utiles.Helper
import com.bqg.ventas.Utiles.Prefs
import com.bqg.ventas.ui.Activity.MainActivity


class HomeFragment : Fragment() {

    var  prefs:Prefs?=null
    var labelBienvenido:TextView?=null
    var labelEmpresa:TextView?=null
    var labelIMEI:TextView?=null
    var labelVendedor:TextView?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var viewHome=inflater.inflate(R.layout.fragment_home, container, false)
        InicializaVariables(viewHome)
        CargarInformacionFragmet()
        return viewHome
    }

    fun InicializaVariables(viewHome:View){
        prefs = Prefs(activity!!)
        labelBienvenido=viewHome.findViewById(R.id.labelBienvenido)
        labelEmpresa=viewHome.findViewById(R.id.labelEmpresa)
        labelIMEI=viewHome.findViewById(R.id.labelIMEI)
        labelVendedor=viewHome.findViewById(R.id.labelVendedor)
    }

    fun CargarInformacionFragmet(){
        activity!!.title="Inicio"
        labelBienvenido!!.text="Hola ${prefs!!.NombreUsuario }!. Bienvenido nuevamente a la App"
        labelEmpresa!!.text=prefs!!.NombreEmpresa
        labelIMEI!!.text=prefs!!.IMEI
        labelVendedor!!.text=prefs!!.IDVendedor
    }

}
