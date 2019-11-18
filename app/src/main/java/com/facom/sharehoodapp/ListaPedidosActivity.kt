package com.facom.sharehoodapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.facom.sharehoodapp.model.User
import com.facom.sharehoodapp.service.RequestService
import io.github.rybalkinsd.kohttp.ext.asString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.selects.select
import org.jetbrains.anko.db.MapRowParser
import org.jetbrains.anko.db.parseSingle
import org.jetbrains.anko.db.select
import org.jetbrains.anko.find

class ListaPedidosActivity : AppCompatActivity() {

    lateinit var pbListaPedidos: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_pedidos)

        pbListaPedidos = findViewById(R.id.pbListaPedidos)

        GlobalScope.launch(Dispatchers.Main) {
            try {
                val response = RequestService.findAll().await()
                if(response.isSuccessful) {
                    Toast.makeText(applicationContext, response.asString(), Toast.LENGTH_LONG).show()
                }
            }catch (e: Exception) {
                Toast.makeText(applicationContext, "Algo deu errado, tente novamente mais tarde", Toast.LENGTH_LONG).show()
            } finally {
                pbListaPedidos.visibility = View.INVISIBLE
            }
        }

    }
    fun goToNovoPedido(view: View) {
        val i = Intent(applicationContext, NovoPedidoActivity::class.java)
        startActivity(i)
    }
}
