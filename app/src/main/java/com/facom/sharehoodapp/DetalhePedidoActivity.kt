package com.facom.sharehoodapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class DetalhePedidoActivity : AppCompatActivity() {
    private lateinit var edtTextEmprestimoMotivo : TextView
    private lateinit var edtTextNovoPedidoTempo : TextView
    private lateinit var edtTextPedidoPor : TextView
    private lateinit var btnNovoEmprestimo : Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhe_pedido)
        btnNovoEmprestimo = findViewById(R.id.btnNovoEmprestimo)
    }
}
