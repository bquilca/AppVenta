package com.bqg.ventas.ui.Activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.telephony.TelephonyManager
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.bqg.ventas.R
import android.support.v4.app.ActivityCompat
import android.util.Log
import com.bqg.ventas.Entidades.*
import com.bqg.ventas.Utiles.Prefs
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



class LoginActivity : AppCompatActivity() {
    val BASE_URL = "http://ws.factlace.com/WebApi/User/"
    var btnLogin: Button? = null
    var loading: ProgressBar? = null

    fun AbrirMenuPrincipal(usuarioEmpresa: UsuarioEmpresa) {
        var principal = Intent(this, MainActivity::class.java)
        principal.putExtra("UsuarioLogueado", usuarioEmpresa)
        prefs!!.UrlServicioWeb=usuarioEmpresa.UrlServicioWeb
        prefs!!.IDDocumento=usuarioEmpresa.IDDocumento
        prefs!!.NombreUsuario=usuarioEmpresa.Nombre
        prefs!!.IDVendedor=usuarioEmpresa.IDVendedor
        prefs!!.usuario=username!!.text.toString()
        prefs!!.password=password!!.text.toString()
        prefs!!.NombreEmpresa=usuarioEmpresa.NombreEmpresa
        startActivity(principal)

        finish()
    }

    fun ObtenerAlmacen(usuarioEmpresa: UsuarioEmpresa) {
        var retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(usuarioEmpresa.UrlServicioWeb)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        var api = retrofit.create(Api::class.java)
        var call = api.obtenerAlmacem(usuarioEmpresa.IDDocumento.toInt())

        call.enqueue(object : Callback<AlmacenNegocio> {
            override fun onFailure(call: Call<AlmacenNegocio>, t: Throwable) {
                Toast.makeText(
                    applicationContext,
                    "Error servicio web al obtener Almacen: ${t.toString()}",
                    Toast.LENGTH_LONG
                ).show()
                AbrirMenuPrincipal(usuarioEmpresa)
                loading!!.visibility=View.GONE
                btnLogin!!.visibility=View.VISIBLE
            }
            override fun onResponse(call: Call<AlmacenNegocio>, response: Response<AlmacenNegocio>) {
                if (response != null) {
                    var almacen = response?.body()
                    prefs!!.idAlmacen = almacen!!.almacen!!
                    AbrirMenuPrincipal(usuarioEmpresa)
                }
                loading!!.visibility=View.GONE
                btnLogin!!.visibility=View.VISIBLE
            }
        })
    }

    var prefs: Prefs? = null

    fun validarLogin(usuarioLogin:String, passwordLogin:String, imeiLogin:String) {
        var retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        var api = retrofit.create(Api::class.java)
        var call = api.validarLogin(usuarioLogin, passwordLogin, imeiLogin)
        Log.d("BQGREQUEST", call.toString() + "")

        call.enqueue(object : Callback<ListaUsuariosEmpresa> {
            override fun onResponse(call: Call<ListaUsuariosEmpresa>?, response: Response<ListaUsuariosEmpresa>?) {
                if (response != null) {
                    var usres = response?.body()
                    Log.v("BQGOK", usres?.toString())
                    if (usres?.estado!!) {
                        for(usuario in usres?.usuarios!!){
                            if(usuario.EncontroImei=="1"){
                                Toast.makeText(
                                    applicationContext,
                                    "Bienvenido ${usuario.Nombre}",
                                    Toast.LENGTH_LONG
                                ).show()
                                ObtenerAlmacen(usuario)
                            }else{
                                Toast.makeText(
                                    applicationContext,
                                    "Su telefono con IMEI ${imeiLogin} no tiene licencia para ingresar al sistema, comunicarse con los administradores de la" +
                                            "app.",
                                    Toast.LENGTH_LONG
                                ).show()

                                loading!!.visibility=View.GONE
                                btnLogin!!.visibility=View.VISIBLE
                            }

                        }
                    } else {
                        Toast.makeText(
                            applicationContext,
                            usres?.mensaje,
                            Toast.LENGTH_LONG
                        ).show()

                        loading!!.visibility=View.GONE
                        btnLogin!!.visibility=View.VISIBLE
                    }
                }


            }
            override fun onFailure(call: Call<ListaUsuariosEmpresa>?, t: Throwable?) {
                Log.v("BQGError", t.toString())
                Toast.makeText(
                    applicationContext,
                    "Error servicio web: ${t.toString()}",
                    Toast.LENGTH_LONG
                ).show()

                loading!!.visibility=View.GONE
                btnLogin!!.visibility=View.VISIBLE

            }
        })
    }

    fun validarLoginOffLine(usuarioLogin:String, passwordLogin:String, imeiLogin:String) {
        var usuario=UsuarioEmpresa()
        usuario.IDEmpresa="TIENDA1"
        usuario.Nombre="Benigno Quilca Guerrero"
        usuario.UrlServicioWeb="http://190.237.40.119:81/WebServicesApp/api/"
        usuario.NombreEmpresa="Distribuidora Peru S.R.L"
        usuario.IDDocumento="507"
        usuario.IDVendedor="889"
        usuario.EncontroImei="1"
        ObtenerAlmacen(usuario)
    }


    var username:EditText?=null
    var password:EditText?=null
    var telefono:TelephonyManager?=null


    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        prefs = Prefs(this)

        setContentView(R.layout.activity_login)

        username = findViewById(R.id.username)
        password = findViewById(R.id.password)

        loading = findViewById(R.id.loading)
        btnLogin=findViewById(R.id.btnLogin)

        val permissions = arrayOf(android.Manifest.permission.READ_PHONE_STATE,android.Manifest.permission.INTERNET)
        ActivityCompat.requestPermissions(this, permissions,0)

        telefono = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager


        btnLogin!!.setOnClickListener{
            btnLogin!!.visibility=View.INVISIBLE
            this.loading!!.visibility=View.VISIBLE
            validarLogin(username!!.text.toString(),password!!.text.toString(),telefono!!.deviceId.toString())
            prefs!!.IMEI=telefono!!.deviceId!!.toString()
        }

    }

}

