package com.facom.sharehoodapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.facom.sharehoodapp.model.Request

class DetalhePedidoActivity : AppCompatActivity() {
    private lateinit var edtTextEmprestimoNome: TextView
    private lateinit var edtTextEmprestimoMotivo : TextView
    private lateinit var edtTextNovoPedidoTempo : TextView
    private lateinit var edtTextPedidoPor : TextView
    private lateinit var btnNovoEmprestimo : Button
    private lateinit var request: Request


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhe_pedido)
        edtTextEmprestimoNome = findViewById(R.id.edtTextEmprestimoNome)
        edtTextEmprestimoMotivo = findViewById(R.id.edtTextEmprestimoMotivo)
        edtTextNovoPedidoTempo = findViewById(R.id.edtTextNovoPedidoTempo)
        edtTextPedidoPor = findViewById(R.id.edtTextPedidoPor)
        btnNovoEmprestimo = findViewById(R.id.btnNovoEmprestimo)

        if(intent.hasExtra(AppValues.EXTRA_DETAIL_REQUEST)) {
            request = intent.getSerializableExtra(AppValues.EXTRA_DETAIL_REQUEST) as Request
            edtTextEmprestimoNome.text = request.name
            edtTextEmprestimoMotivo.text = request.reason
            edtTextNovoPedidoTempo.text = "Tempo de empréstimo: ${request.duration} dias"
            edtTextPedidoPor.text = "Pedido por: ${request.user}"
        } else finish()
    }
}
