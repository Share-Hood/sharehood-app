package com.facom.sharehoodapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class NovoPedidoActivity : AppCompatActivity() {
    private lateinit var btnNovoPedidoCadastrar: Button
    private lateinit var btnNovoPedidoCancelar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_novo_pedido)
        btnNovoPedidoCadastrar = findViewById(R.id.btnNovoPedidoCadastrar)
        btnNovoPedidoCancelar = findViewById(R.id.btnNovoPedidoCancelar)
    }

    fun cancelar(view: View) {
        finish()
    }

}
