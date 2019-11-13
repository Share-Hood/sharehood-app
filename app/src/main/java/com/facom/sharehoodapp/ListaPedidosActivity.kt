package com.facom.sharehoodapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class ListaPedidosActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_pedidos)
    }
    fun goNovoPedido(view: View) {
        val i = Intent(applicationContext, NovoPedidoActivity::class.java)
        startActivity(i)
    }
}
