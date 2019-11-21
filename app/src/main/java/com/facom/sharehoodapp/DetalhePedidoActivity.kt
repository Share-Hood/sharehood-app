package com.facom.sharehoodapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.facom.sharehoodapp.dao.UserDao
import com.facom.sharehoodapp.model.Request
import com.facom.sharehoodapp.model.User

class DetalhePedidoActivity : AppCompatActivity() {
    private lateinit var edtTextEmprestimoNome: TextView
    private lateinit var edtTextEmprestimoMotivo : TextView
    private lateinit var edtTextNovoPedidoTempo : TextView
    private lateinit var edtTextPedidoPor : TextView
    private lateinit var btnEmprestar : Button
    private lateinit var request: Request
    private lateinit var loggedUser: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhe_pedido)
        edtTextEmprestimoNome = findViewById(R.id.edtTextEmprestimoNome)
        edtTextEmprestimoMotivo = findViewById(R.id.edtTextEmprestimoMotivo)
        edtTextNovoPedidoTempo = findViewById(R.id.edtTextNovoPedidoTempo)
        edtTextPedidoPor = findViewById(R.id.edtTextPedidoPor)
        btnEmprestar = findViewById(R.id.btnEmprestar)

        if(intent.hasExtra(AppValues.EXTRA_DETAIL_REQUEST)) {
            request = intent.getSerializableExtra(AppValues.EXTRA_DETAIL_REQUEST) as Request
            edtTextEmprestimoNome.text = request.name
            edtTextEmprestimoMotivo.text = request.reason
            edtTextNovoPedidoTempo.text = "Tempo de empréstimo: ${request.duration} dias"
            edtTextPedidoPor.text = "Pedido por: ${request.user.name}"

            val loggedUser = UserDao.getLoggedUser(applicationContext)
            if(loggedUser == null) finish()
            else this.loggedUser = loggedUser

            if(request.user.id == loggedUser!!.id) {
                btnEmprestar.isClickable = false
                btnEmprestar.background = getDrawable(R.drawable.rounded_corners_disabled)
            }

        } else finish()
    }

    fun goToConfirmaEmprestimo(view: View) {
        val i = Intent(this, ConfirmarEmprestimoActivity::class.java)
        i.putExtra(AppValues.EXTRA_DETAIL_REQUEST, request)
        startActivity(i)
        finish()
    }
}
