package com.facom.sharehoodapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.facom.sharehoodapp.model.User
import org.jetbrains.anko.db.select

class NovoPedidoActivity : AppCompatActivity() {
    private lateinit var btnNovoPedidoCadastrar: Button
    private lateinit var btnNovoPedidoCancelar: Button
    private lateinit var loggedUser: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_novo_pedido)
        btnNovoPedidoCadastrar = findViewById(R.id.btnNovoPedidoCadastrar)
        btnNovoPedidoCancelar = findViewById(R.id.btnNovoPedidoCancelar)
    }

    fun cancelar(view: View) {
        finish()
    }

    fun getLoggedUser() {
        database.use {
            select(AppValues.USER_TABLE_NAME).exec {
                moveToFirst()
                loggedUser = User()
                loggedUser.name = getString(1)
                loggedUser.email = getString(2)
            }
        }
    }

}
