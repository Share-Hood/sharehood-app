package com.facom.sharehoodapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {

    private lateinit var btnEsqueciMinhaSenha: Button
    private lateinit var btnCadastroUsuario: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnEsqueciMinhaSenha = findViewById(R.id.btnLoginEsqueciMinhaSenha)
        btnCadastroUsuario = findViewById(R.id.btnLoginCadastrar)
    }

    fun goEsqueciMinhaSenha(view: View) {
        var i = Intent(applicationContext, EsqueciSenhaActivity::class.java)
        startActivity(i)
    }

    fun goCadastroUsuario(view: View) {
        var i = Intent(applicationContext, CadastroUsuarioActivity::class.java)
        startActivity(i)
    }
}
