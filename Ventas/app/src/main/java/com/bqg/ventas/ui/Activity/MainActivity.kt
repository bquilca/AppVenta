package com.bqg.ventas.ui.Activity

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.MenuItem
import android.support.v4.widget.DrawerLayout
import android.support.design.widget.NavigationView
import android.support.v4.app.FragmentManager
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.widget.TextView
import com.bqg.ventas.Entidades.UsuarioEmpresa
import com.bqg.ventas.R
import com.bqg.ventas.Utiles.Prefs
import com.bqg.ventas.ui.Fragment.HomeFragment
import com.bqg.ventas.ui.Fragment.ListaPedidosFragment

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    var fragmentContenedor : FragmentManager?=null
    var usuarioLogueado= UsuarioEmpresa()
    var prefs: Prefs? = null
    var alertDialog: android.app.AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        usuarioLogueado = this.intent.extras.get("UsuarioLogueado") as UsuarioEmpresa

        prefs = Prefs(this)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener(this)

        fragmentContenedor =supportFragmentManager
        fragmentContenedor!!.beginTransaction().replace(R.id.contenedor,
            HomeFragment()
        ).commit()
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
            alertDialog!!.cancel()
        }
    }


    override fun onBackPressed() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_home->{
                fragmentContenedor!!.beginTransaction().replace(R.id.contenedor,
                    HomeFragment()
                ).commit()
            }
            R.id.nav_nuevo_pedido -> {
                val pedido = Intent(this.applicationContext, PedidoActivity::class.java)
                startActivity(pedido)
            }
            R.id.nav_pedido_dia -> {
                fragmentContenedor!!.beginTransaction().replace(R.id.contenedor,
                    ListaPedidosFragment()
                ).commit()
            }
            R.id.nav_cerrarSesio0n -> {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Confirmar")
                builder.setMessage("Desea salir del app  ${prefs!!.NombreUsuario}")

                builder.setPositiveButton("Aceptar") { dialog, which ->
                    val login = Intent(this.applicationContext, LoginActivity::class.java)
                    startActivity(login)
                }

                builder.setNegativeButton("Cancelar"){dialog, which ->
                }

                builder.show()
            }

        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}