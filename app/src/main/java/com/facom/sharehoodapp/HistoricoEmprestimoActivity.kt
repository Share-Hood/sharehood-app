package com.facom.sharehoodapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.Toast
import com.facom.sharehoodapp.adapter.LendingAdapter
import com.facom.sharehoodapp.dao.UserDao
import com.facom.sharehoodapp.model.Lending
import com.facom.sharehoodapp.model.User
import com.facom.sharehoodapp.service.LendingService
import io.github.rybalkinsd.kohttp.ext.asString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.parseList


class HistoricoEmprestimoActivity : AppCompatActivity() {

    lateinit var pbListaPedidos: ProgressBar
    lateinit var listViewListaEmprestimos: ListView
    private lateinit var loggedUser: User

    @ImplicitReflectionSerializer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_historico_emprestimo)

        pbListaPedidos = findViewById(R.id.pbListaPedidos)
        listViewListaEmprestimos = findViewById(R.id.listViewListaEmprestimos)

        val loggedUser = UserDao.getLoggedUser(applicationContext)
        if(loggedUser == null) finish()
        else this.loggedUser = loggedUser

        GlobalScope.launch(Dispatchers.Default) {
            try {
                val response = LendingService.findAll(loggedUser!!).await()
                if(response.isSuccessful) {
//                    Toast.makeText(applicationContext, response.asString(), Toast.LENGTH_LONG).show()
                    val list = Json.parseList<Lending>(response.asString()!!)
                    runOnUiThread {
                        listViewListaEmprestimos.adapter = ArrayAdapter(applicationContext, android.R.layout.simple_list_item_1, list)
                        listViewListaEmprestimos.adapter = LendingAdapter(applicationContext, ArrayList(list), loggedUser)
//                        listViewListaEmprestimos.setOnItemClickListener { parent, view, position, id ->
//                            val selectedRequest = requests[position]
//                            val i = Intent(applicationContext, DetalhePedidoActivity::class.java)
//                            i.putExtra(AppValues.EXTRA_DETAIL_REQUEST, selectedRequest)
//                            startActivity(i)
//                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(applicationContext, "Algo deu errado, tente novamente mais tarde", Toast.LENGTH_LONG).show()
            } finally {
                pbListaPedidos.visibility = View.INVISIBLE
            }
        }
    }
}
