@file:Suppress("UNUSED_PARAMETER")

package com.facom.sharehoodapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import br.com.simplepass.loadingbutton.customViews.CircularProgressButton
import com.facom.sharehoodapp.dao.UserDao
import com.facom.sharehoodapp.model.Request
import com.facom.sharehoodapp.model.User
import com.facom.sharehoodapp.service.RequestService
import com.facom.sharehoodapp.service.UserService
import io.github.rybalkinsd.kohttp.ext.asString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.db.select

class NovoPedidoActivity : AppCompatActivity() {
    private lateinit var btnNovoPedidoCadastrar: CircularProgressButton
    private lateinit var btnNovoPedidoCancelar: Button
    private lateinit var loggedUser: User
    private lateinit var edtTextNovoPedidoNomeItem: EditText
    private lateinit var edtTextNovoPedidoTempo: EditText
    private lateinit var edtTextNovoPedidoMotivo: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_novo_pedido)
        btnNovoPedidoCadastrar = findViewById(R.id.btnNovoPedidoCadastrar)
        btnNovoPedidoCancelar = findViewById(R.id.btnNovoPedidoCancelar)
        edtTextNovoPedidoNomeItem = findViewById(R.id.edtTextNovoPedidoNomeItem)
        edtTextNovoPedidoTempo = findViewById(R.id.edtTextNovoPedidoTempo)
        edtTextNovoPedidoMotivo = findViewById(R.id.edtTextNovoPedidoMotivo)
        val loggedUser = UserDao.getLoggedUser(applicationContext)
        if(loggedUser == null) finish()
        else this.loggedUser = loggedUser
    }

    fun cancelar(view: View) {
        finish()
    }

    fun cadastrar (view: View){


        var request = Request(
            "",
            edtTextNovoPedidoNomeItem.text.toString(),
            edtTextNovoPedidoTempo.text.toString().toInt(),
            edtTextNovoPedidoMotivo.text.toString(),
            loggedUser,
            ""
        )

        GlobalScope.launch(context = Dispatchers.Main) {
            btnNovoPedidoCadastrar.startAnimation()
            try {
                val response = RequestService.create(request).await()
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
                btnNovoPedidoCadastrar.revertAnimation {
                    btnNovoPedidoCadastrar.background = getDrawable(R.drawable.rounded_corners_primary)
                }
            }
        }

    }

}
