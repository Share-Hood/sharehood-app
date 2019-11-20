package com.facom.sharehoodapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import br.com.simplepass.loadingbutton.customViews.CircularProgressButton
import com.facom.sharehoodapp.model.Request

class ConfirmarEmprestimoActivity : AppCompatActivity() {
    private lateinit var txtViewConfirmaEmprestimoNome: TextView
    private lateinit var txtViewConfirmaEmprestimoTempo : TextView
    private lateinit var txtViewConfirmaEmprestimoPara : TextView
    private lateinit var btnConfirmaEmprestimoConfirmar : CircularProgressButton
    private lateinit var btnConfirmaEmprestimoCancelar : Button
    private lateinit var request: Request

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirmar_emprestimo)
        btnConfirmaEmprestimoConfirmar = findViewById(R.id.btnConfirmaEmprestimoConfirmar)
        btnConfirmaEmprestimoCancelar = findViewById(R.id.btnConfirmaEmprestimoCancelar)
        txtViewConfirmaEmprestimoNome = findViewById(R.id.txtViewConfirmaEmprestimoNome)
        txtViewConfirmaEmprestimoTempo = findViewById(R.id.txtViewConfirmaEmprestimoTempo)
        txtViewConfirmaEmprestimoPara = findViewById(R.id.txtViewConfirmaEmprestimoPara)

        if(intent.hasExtra(AppValues.EXTRA_DETAIL_REQUEST)) {
            request = intent.getSerializableExtra(AppValues.EXTRA_DETAIL_REQUEST) as Request
            txtViewConfirmaEmprestimoNome.text = request.name
            txtViewConfirmaEmprestimoTempo.text = "Tempo de empréstimo: ${request.duration} dias"
            txtViewConfirmaEmprestimoPara.text = "Emprestando para: ${request.user}"
        } else finish()
    }

    fun cancelar(view: View) {
        finish()
    }

    fun confirmar(view: View) {
        btnConfirmaEmprestimoConfirmar.startAnimation()
    }
}
