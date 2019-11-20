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
import com.google.android.material.bottomnavigation.BottomNavigationView
import io.github.rybalkinsd.kohttp.ext.asString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.selects.select
import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import kotlinx.serialization.parseList
import android.view.MenuItem
import com.facom.sharehoodapp.dao.UserDao


class ListaPedidosActivity : AppCompatActivity() {

    lateinit var pbListaPedidos: ProgressBar
    lateinit var listViewListaPedidos: ListView
    lateinit var navigationView: BottomNavigationView
    lateinit var loggedUser: User

    @ImplicitReflectionSerializer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_pedidos)
        navigationView = findViewById(R.id.navigationView)
        navigationView.setOnNavigationItemReselectedListener {
            when (it.getItemId()) {
                R.id.nav_sair -> {
                    logout(null)
                }
                R.id.nav_perfil -> {
                    goToPerfil(null)
                }
                R.id.nav_historico -> {
                    goToHistorico(null)
                }
            }
        }


        pbListaPedidos = findViewById(R.id.pbListaPedidos)
        listViewListaPedidos = findViewById(R.id.listViewListaPedidos)

        val loggedUser = UserDao.getLoggedUser(applicationContext)
        if(loggedUser == null) finish()
        else this.loggedUser = loggedUser

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
        val i = Intent(applicationContext, NovoPedidoActivity::class.java)
        startActivity(i)
    }

    fun goToHistorico(view: View?) {
        val i = Intent(applicationContext, HistoricoPedido::class.java)
        startActivity(i)
    }

    fun goToPerfil(view: View?) {
        val i = Intent(applicationContext, PerfilUsuarioActivity::class.java)
        startActivity(i)
    }

    fun logout(view: View?) {
        UserDao.logout(applicationContext, loggedUser)
        val i = Intent(applicationContext, MainActivity::class.java)
        startActivity(i)
        finish()
    }
}
