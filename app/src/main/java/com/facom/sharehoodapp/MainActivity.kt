package com.facom.sharehoodapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {

    private lateinit var btnEsqueciMinhaSenha: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnEsqueciMinhaSenha = findViewById(R.id.btnLoginEsqueciMinhaSenha)
    }

    fun goEsqueciMinhaSenha(view: View) {
        var i = Intent(applicationContext, EsqueciSenhaActivity::class.java)
        startActivity(i)
    }
}
