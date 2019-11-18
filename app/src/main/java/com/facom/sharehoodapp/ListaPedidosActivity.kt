package com.facom.sharehoodapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.facom.sharehoodapp.model.User
import kotlinx.coroutines.selects.select
import org.jetbrains.anko.db.MapRowParser
import org.jetbrains.anko.db.parseSingle
import org.jetbrains.anko.db.select
import org.jetbrains.anko.find

class ListaPedidosActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_pedidos)

        database.use {
            select(AppValues.USER_TABLE_NAME).exec {
                moveToFirst()
                var user = User()
                user.name = getString(1)
                user.email = getString(2)
                runOnUiThread {
                    Toast.makeText(applicationContext, user.toString(), Toast.LENGTH_LONG).show()
                }
            }
        }
    }
    fun goToNovoPedido(view: View) {
        val i = Intent(applicationContext, NovoPedidoActivity::class.java)
        startActivity(i)
    }
}
