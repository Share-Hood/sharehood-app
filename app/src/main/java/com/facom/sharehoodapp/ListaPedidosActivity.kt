package com.facom.sharehoodapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.Toast
import com.facom.sharehoodapp.adapter.RequestAdapter
import com.facom.sharehoodapp.model.Request
import com.facom.sharehoodapp.model.User
import com.facom.sharehoodapp.service.RequestService
import io.github.rybalkinsd.kohttp.ext.asString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.selects.select
import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import kotlinx.serialization.parseList
import org.jetbrains.anko.db.MapRowParser
import org.jetbrains.anko.db.parseSingle
import org.jetbrains.anko.db.select
import org.jetbrains.anko.find

class ListaPedidosActivity : AppCompatActivity() {

    lateinit var pbListaPedidos: ProgressBar
    lateinit var listViewListaPedidos: ListView

    @ImplicitReflectionSerializer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_pedidos)

        pbListaPedidos = findViewById(R.id.pbListaPedidos)
        listViewListaPedidos = findViewById(R.id.listViewListaPedidos)

        GlobalScope.launch(Dispatchers.Main) {
            try {
                val response = RequestService.findAll().await()
                if(response.isSuccessful) {
                    val requests = Json.parseList<Request>(response.asString()!!)
                    listViewListaPedidos.adapter = RequestAdapter(applicationContext, ArrayList(requests))
                    listViewListaPedidos.setOnItemClickListener { parent, view, position, id ->
                        val selectedRequest = requests[position]
                        val i = Intent(applicationContext, DetalhePedidoActivity::class.java)
                        i.putExtra(AppValues.EXTRA_DETAIL_REQUEST, selectedRequest)
                        startActivity(i)
                    }
                }
            }catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(applicationContext, "Algo deu errado, tente novamente mais tarde", Toast.LENGTH_LONG).show()
            } finally {
                pbListaPedidos.visibility = View.INVISIBLE
            }
        }

    }
    fun goToNovoPedido(view: View) {
        val i = Intent(applicationContext, HistoricoPedido::class.java)
        startActivity(i)
    }
}
