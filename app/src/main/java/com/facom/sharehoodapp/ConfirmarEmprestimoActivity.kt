package com.facom.sharehoodapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import br.com.simplepass.loadingbutton.customViews.CircularProgressButton
import com.facom.sharehoodapp.dao.UserDao
import com.facom.sharehoodapp.model.Lending
import com.facom.sharehoodapp.model.Request
import com.facom.sharehoodapp.model.User
import com.facom.sharehoodapp.service.LendingService
import io.github.rybalkinsd.kohttp.ext.asString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ConfirmarEmprestimoActivity : AppCompatActivity() {
    private lateinit var txtViewConfirmaEmprestimoNome: TextView
    private lateinit var txtViewConfirmaEmprestimoTempo : TextView
    private lateinit var txtViewConfirmaEmprestimoPara : TextView
    private lateinit var btnConfirmaEmprestimoConfirmar : CircularProgressButton
    private lateinit var btnConfirmaEmprestimoCancelar : Button
    private lateinit var request: Request
    private lateinit var loggedUser: User

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
            txtViewConfirmaEmprestimoPara.text = "Emprestando para: ${request.user.name}"
        } else finish()

        val loggedUser = UserDao.getLoggedUser(applicationContext)
        if(loggedUser == null) finish()
        else this.loggedUser = loggedUser
    }

    fun cancelar(view: View) {
        finish()
    }

    fun confirmar(view: View) {
        btnConfirmaEmprestimoConfirmar.startAnimation()
        val lending = Lending("", "", "", request, loggedUser, request.user, "")
        GlobalScope.launch(context = Dispatchers.Main) {
            try {
                val response = LendingService.create(lending).await()
                if(response.isSuccessful) {
                    Toast.makeText(
                        applicationContext,
                        "Salvo com sucesso!",
                        Toast.LENGTH_LONG
                    ).show()
                    finish()
                }
                else Toast.makeText(applicationContext, response.asString(), Toast.LENGTH_LONG).show()
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(applicationContext, "Algo inesperado aconteceu, tente novamente mais tarde", Toast.LENGTH_LONG).show()
            } finally {
                btnConfirmaEmprestimoConfirmar.revertAnimation {
                    btnConfirmaEmprestimoConfirmar.background = getDrawable(R.drawable.rounded_corners_primary)
                }
            }
        }
    }
}
