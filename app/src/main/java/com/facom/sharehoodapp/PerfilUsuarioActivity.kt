package com.facom.sharehoodapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.TextureView
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.facom.sharehoodapp.dao.UserDao
import com.facom.sharehoodapp.model.User
import com.facom.sharehoodapp.service.UserService
import io.github.rybalkinsd.kohttp.ext.asString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class PerfilUsuarioActivity : AppCompatActivity() {
    private lateinit var edtPerfilNome : TextView
    private lateinit var edtPerfilEmail : TextView
    private lateinit var edtPerfilSenha : TextView
    private lateinit var edtPerfilConfirmaSenha : TextView
    private lateinit var btnPerfilEditar : Button
    private lateinit var btnPerfilCancelar : Button
    private lateinit var btnPerfilSair : Button

    lateinit var loggedUser: User


    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil_usuario)
        edtPerfilNome = findViewById(R.id.edtPerfilNome)
        edtPerfilEmail = findViewById(R.id.edtPerfilEmail)
        edtPerfilSenha = findViewById(R.id.edtPerfilSenha)
        btnPerfilEditar = findViewById(R.id.btnPerfilEditar)
        btnPerfilCancelar = findViewById(R.id.btnPerfilCancelar)
        btnPerfilSair = findViewById(R.id.btnPerfilSair)
        edtPerfilConfirmaSenha = findViewById(R.id.edtPerfilConfirmaSenha)

        val loggedUser = UserDao.getLoggedUser(applicationContext)
        if(loggedUser == null) finish()
        else this.loggedUser = loggedUser
    }

    fun editar(view: View) {
        if(edtPerfilNome.text.toString().isEmpty()){
            Toast.makeText(applicationContext, "Informe o nome", Toast.LENGTH_LONG).show();
            return;
        }
        else if(edtPerfilEmail.text.toString().isEmpty()){
            Toast.makeText(applicationContext, "Informe o email", Toast.LENGTH_LONG).show();
            return;
        }
        else if(edtPerfilSenha.text.toString().isEmpty()){
            Toast.makeText(applicationContext, "Informe a senha", Toast.LENGTH_LONG).show();
            return;
        }
        else if(edtPerfilConfirmaSenha.text.toString().isEmpty()){
            Toast.makeText(applicationContext, "Confirme a senha", Toast.LENGTH_LONG).show();
            return;
        }
        else if(edtPerfilSenha.text.toString() != edtPerfilConfirmaSenha.text.toString()){
            Toast.makeText(applicationContext, "Senhas diferentes", Toast.LENGTH_LONG).show();
            return;
        }

        var usuario = User()
        usuario.name = edtPerfilNome.text.toString()
        usuario.email = edtPerfilEmail.text.toString()
        usuario.password = edtPerfilSenha.text.toString()

        GlobalScope.launch(context = Dispatchers.Main) {
            try {
                val response = UserService.cadastroUsuario(usuario).await()
                if(response.isSuccessful) {
                    Toast.makeText(
                        applicationContext,
                        "Usuário editado com sucesso!",
                        Toast.LENGTH_LONG
                    ).show()
                    finish()
                }
                else Toast.makeText(applicationContext, response.asString(), Toast.LENGTH_LONG).show()
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(applicationContext, "Algo inesperado aconteceu, tente novamente mais tarde", Toast.LENGTH_LONG).show()
            } finally {

                    btnPerfilEditar.background = getDrawable(R.drawable.rounded_corners_primary)

            }
        }

    }

    fun goSair(view: View) {
        UserDao.logout(applicationContext, loggedUser)
        val i = Intent(applicationContext, MainActivity::class.java)
        startActivity(i)
        finish()
    }
    fun goCancelar(view: View) {
        finish()
    }

}
