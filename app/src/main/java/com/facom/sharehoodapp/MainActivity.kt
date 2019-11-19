package com.facom.sharehoodapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import br.com.simplepass.loadingbutton.customViews.CircularProgressButton
import com.facom.sharehoodapp.model.User
import com.facom.sharehoodapp.service.UserService
import io.github.rybalkinsd.kohttp.ext.asString
import kotlinx.coroutines.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select

class MainActivity : AppCompatActivity() {

    private lateinit var btnEsqueciMinhaSenha: Button
    private lateinit var btnCadastroUsuario: Button
    private lateinit var btnEntrar: CircularProgressButton
    private lateinit var edtTextLoginEmail: EditText
    private lateinit var edtTextLoginSenha: EditText
    private var loggedUser: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnEsqueciMinhaSenha = findViewById(R.id.btnLoginEsqueciMinhaSenha)
        btnCadastroUsuario = findViewById(R.id.btnLoginCadastrar)
        btnEntrar = findViewById(R.id.btnLoginEntrar)
        edtTextLoginEmail = findViewById(R.id.edtTextLoginEmail)
        edtTextLoginSenha = findViewById(R.id.edtTextLoginSenha)

        getLoggedUser()
        if(loggedUser != null) goToPedidos()
    }

    fun goEsqueciMinhaSenha(view: View) {
        val i = Intent(applicationContext, EsqueciSenhaActivity::class.java)
        startActivity(i)
    }

    fun goCadastroUsuario(view: View) {
        val i = Intent(applicationContext, CadastroUsuarioActivity::class.java)
        startActivity(i)
    }
    fun entrar(view: View){
        if(edtTextLoginEmail.text.toString().isEmpty()){
            Toast.makeText(applicationContext, "Informe o email", Toast.LENGTH_LONG).show();
            return
        }
        else if(edtTextLoginSenha.text.toString().isEmpty()){
            Toast.makeText(applicationContext, "Informe a senha", Toast.LENGTH_LONG).show();
            return
        }

        GlobalScope.launch (Dispatchers.Main) {
            btnEntrar.startAnimation()
            try {
                val user = User(edtTextLoginEmail.text.toString(), edtTextLoginSenha.text.toString())
                val response = UserService.login(user).await()
                if(response.isSuccessful) {
                    launch(Dispatchers.Default) {
                        loggedUser = Json(JsonConfiguration.Stable).parse(User.serializer(), response.asString()!!)
                        database.use {
                            insert(AppValues.USER_TABLE_NAME,
                                "id" to loggedUser?.id,
                                "name" to loggedUser?.name,
                                "email" to loggedUser?.email,
                                "cpf" to loggedUser?.cpf,
                                "cellphone" to loggedUser?.cellphone,
                                "password" to loggedUser?.password
                            )
                        }
                        runOnUiThread { goToPedidos() }
                    }
                }
                else {
                    Toast.makeText(applicationContext, response.asString(), Toast.LENGTH_LONG).show()
                    btnEntrar.revertAnimation {
                        btnEntrar.background = getDrawable(R.drawable.rounded_corners_primary)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                btnEntrar.revertAnimation {
                    btnEntrar.background = getDrawable(R.drawable.rounded_corners_primary)
                }
                Toast.makeText(applicationContext, "Erro ao logar", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun goToPedidos() {
        val i = Intent(this, ListaPedidosActivity::class.java)
        startActivity(i)
        finish()
    }

    fun getLoggedUser() {
        database.use {
            select(AppValues.USER_TABLE_NAME).exec {
                if(moveToNext()) {
                    loggedUser = User()
                    loggedUser?.id = getString(0)
                    loggedUser?.name = getString(1)
                    loggedUser?.email = getString(2)
                    loggedUser?.password = getString(3)
                    loggedUser?.cellphone = getString(4)
                    loggedUser?.cpf = getString(5)
                }
            }
        }
    }
}
