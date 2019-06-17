package com.bqg.ventas.Utiles

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.SimpleDateFormat
import java.util.*

class Helper {


    fun obtenerTextoFecha(fecha: Date): String {
        var formatoFechaDia = "EEEE dd/MM/yyyy"
        var locale: Locale = Locale.getDefault()
        val formatter = SimpleDateFormat(formatoFechaDia, locale)
        return formatter.format(fecha)
    }

    fun obtenerFechaHoraActual(dias: Int): Date {
        var fecha: Calendar = Calendar.getInstance()
        fecha.add(Calendar.DAY_OF_YEAR, dias)
        return fecha.time
    }

    fun formateaDecimal(numero:Double):String{
        val simbolos: DecimalFormatSymbols = DecimalFormatSymbols()
        simbolos.decimalSeparator='.'
        val formato = DecimalFormat("###,###.00",simbolos)
        val valorFormato = formato.format(numero)
        return valorFormato
    }
}