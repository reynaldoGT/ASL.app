package com.neo.signLanguage.utils

// Importo las siguientes dependencias
import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import java.lang.Exception

object ConfigTeclado {

    // Mediante esta función ocultamos el teclado cuando no se use en la interface de la aplicación
    fun ocultarTeclado(context: Activity, view: View) {

        // Dentro de la variable 'metodoentrada' hacemos uso de getSystemService y le pasamos el método
        // 'InputMethodManager' el cual detecta el método de entrada que es el teclado
        val metodoentrada = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        // A la variable 'metodoentrada' le pasamos el método 'hideSoftInputFromWindow' que solicita al sistema
        // ocultar el método de entrada de la aplicación, el cual es el teclado y le obtenemos un token con
        // el método 'getWindowToken', por último le pasamos el método 'InputMethodManager' con
        // la función 'HIDE_NOT_ALWAYS' que mantiene siempre en estado oculto el teclado
        metodoentrada.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS)
    }

    // Llamamos a la función 'ocultarTeclado' mediante un 'try'
    fun ocultarTeclado(context: Activity) {
        try {
            ocultarTeclado(context, context.currentFocus!!)
        }

        // Si hay un error lanzamos una excepción mediante 'catch'
        catch (e: Exception) { }
    }

}