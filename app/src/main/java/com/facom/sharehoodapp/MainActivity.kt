package com.facom.sharehoodapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.*
import br.com.simplepass.loadingbutton.customViews.CircularProgressButton
import com.facom.sharehoodapp.model.User
import com.facom.sharehoodapp.service.UserService
import io.github.rybalkinsd.kohttp.ext.asString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity() {

    private lateinit var btnEsqueciMinhaSenha: Button
    private lateinit var btnCadastroUsuario: Button
    private lateinit var btnEntrar: CircularProgressButton
    private lateinit var edtTextLoginEmail: EditText
    private lateinit var edtTextLoginSenha: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnEsqueciMinhaSenha = findViewById(R.id.btnLoginEsqueciMinhaSenha)
        btnCadastroUsuario = findViewById(R.id.btnLoginCadastrar)
        btnEntrar = findViewById(R.id.btnLoginEntrar)
        edtTextLoginEmail = findViewById(R.id.edtTextLoginEmail)
        edtTextLoginSenha = findViewById(R.id.edtTextLoginSenha)
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

        GlobalScope.launch (context = Dispatchers.Main) {
            btnEntrar.startAnimation()
            try {
                val user = User(edtTextLoginEmail.text.toString(), edtTextLoginSenha.text.toString())
                val response = UserService.login(user).await()
                if(response.isSuccessful) goToPedidos()
                else Toast.makeText(applicationContext, response.asString(), Toast.LENGTH_LONG).show()
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(applicationContext, "Erro ao logar", Toast.LENGTH_LONG).show()
            } finally {
                btnEntrar.revertAnimation {
                    btnEntrar.background = getDrawable(R.drawable.rounded_corners_primary)
                }
            }
        }
    }

    fun goToPedidos() {
        var i = Intent(this, ListaPedidosActivity::class.java)
        startActivity(i)
        finish()
    }
}
