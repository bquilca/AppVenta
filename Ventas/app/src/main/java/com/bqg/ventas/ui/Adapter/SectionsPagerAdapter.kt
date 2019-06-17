package com.bqg.ventas.ui.Adapter

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.bqg.ventas.ui.Fragment.ArticulosPedidoFragment
import com.bqg.ventas.ui.Fragment.CabeceraPedidoFragment
import com.bqg.ventas.ui.Fragment.CarritoComprasFragment


/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(val context: Context, fm: FragmentManager,var totalTabs: Int) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment? {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        //return PlaceholderFragment.newInstance(position + 1)
        when (position){
            0 -> return CabeceraPedidoFragment()
            1 -> return ArticulosPedidoFragment()
            2 -> return CarritoComprasFragment()
            else -> return null
        }
    }



    override fun getCount(): Int {
        // Show 2 total pages.
        return totalTabs
    }
}