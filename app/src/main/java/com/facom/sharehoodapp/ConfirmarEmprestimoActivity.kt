package com.facom.sharehoodapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class ConfirmarEmprestimoActivity : AppCompatActivity() {
    private lateinit var edtTextObjeto: TextView
    private lateinit var edtTextNovoPedidoTempo : TextView
    private lateinit var edtTextPedidoPor : TextView
    private lateinit var btnNovoEmprestimo : Button
    private lateinit var btnEmprestimoCancelar : Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirmar_emprestimo)
        btnNovoEmprestimo = findViewById(R.id.btnNovoEmprestimo)
        btnEmprestimoCancelar = findViewById(R.id.btnEmprestimoCancelar)
    }

    fun cancelar(view: View) {
        finish()
    }
}
