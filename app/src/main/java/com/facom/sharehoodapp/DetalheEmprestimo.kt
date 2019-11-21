package com.facom.sharehoodapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import br.com.simplepass.loadingbutton.customViews.CircularProgressButton
import com.facom.sharehoodapp.adapter.RequestAdapter
import com.facom.sharehoodapp.dao.UserDao
import com.facom.sharehoodapp.model.Lending
import com.facom.sharehoodapp.model.Request
import com.facom.sharehoodapp.model.User
import com.facom.sharehoodapp.service.LendingService
import io.github.rybalkinsd.kohttp.ext.asString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.parseList

class DetalheEmprestimo : AppCompatActivity() {


    private lateinit var txtViewEmprestimoNome: TextView
    private lateinit var txtViewEmprestimoMotivo: TextView
    private lateinit var txtViewEmprestimoTempo: TextView
    private lateinit var txtViewEmprestimoPeidoPor: TextView
    private lateinit var btnJaPeguei: CircularProgressButton
    private lateinit var lending: Lending
    private lateinit var loggedUser: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhe_emprestimo)

        txtViewEmprestimoNome = findViewById(R.id.txtViewEmprestimoNome)
        txtViewEmprestimoMotivo = findViewById(R.id.txtViewEmprestimoMotivo)
        txtViewEmprestimoTempo = findViewById(R.id.txtViewEmprestimoTempo)
        txtViewEmprestimoPeidoPor = findViewById(R.id.txtViewEmprestimoPeidoPor)
        btnJaPeguei = findViewById(R.id.btnJaPeguei)

        if(intent.hasExtra(AppValues.EXTRA_DETAIL_LENDING)) {
            lending = intent.getSerializableExtra(AppValues.EXTRA_DETAIL_LENDING) as Lending
            txtViewEmprestimoPeidoPor.text = lending.request.name
            txtViewEmprestimoMotivo.text = lending.request.reason
            txtViewEmprestimoTempo.text = "Tempo de empréstimo: ${lending.request.duration} dias"
            txtViewEmprestimoPeidoPor.text = "Pedido por: ${lending.request.user.name}"

            val loggedUser = UserDao.getLoggedUser(applicationContext)
            if(loggedUser == null) finish()
            else this.loggedUser = loggedUser

            if(lending.borrower.id == loggedUser!!.id) {
                btnJaPeguei.visibility = View.INVISIBLE
            }

        } else finish()
    }

    @ImplicitReflectionSerializer
    fun finalizeLending(view: View) {
        GlobalScope.launch(Dispatchers.Default) {
            btnJaPeguei.startAnimation()
            try {
                var response = LendingService.finalize(lending).await()
                if(response.isSuccessful) {
                    val requests = Json.parseList<Request>(response.asString()!!)
                    runOnUiThread {
                        Toast.makeText(applicationContext, "Pedido finalizado com sucesso", Toast.LENGTH_LONG).show()
                    }
                }
            }catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(applicationContext, "Algo deu errado, tente novamente mais tarde", Toast.LENGTH_LONG).show()
            } finally {
                runOnUiThread {
                    btnJaPeguei.revertAnimation {
                        btnJaPeguei.background = getDrawable(R.drawable.rounded_corners_primary)
                    }
                }
            }
        }
    }
}
