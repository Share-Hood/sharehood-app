package com.facom.sharehoodapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private lateinit var btnEsqueciMinhaSenha: Button
    private lateinit var btnCadastroUsuario: Button
    private lateinit var edtTextLoginEmail: EditText
    private lateinit var edtTextLoginSenha: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnEsqueciMinhaSenha = findViewById(R.id.btnLoginEsqueciMinhaSenha)
        btnCadastroUsuario = findViewById(R.id.btnLoginCadastrar)
        edtTextLoginEmail = findViewById(R.id.edtTextLoginEmail)
        edtTextLoginSenha = findViewById(R.id.edtTextLoginSenha)
    }

    fun goEsqueciMinhaSenha(view: View) {
        var i = Intent(applicationContext, EsqueciSenhaActivity::class.java)
        startActivity(i)
    }

    fun goCadastroUsuario(view: View) {
        var i = Intent(applicationContext, CadastroUsuarioActivity::class.java)
        startActivity(i)
    }
    fun entrar(view: View){
        if(edtTextLoginEmail.text.toString().isEmpty()){
            Toast.makeText(applicationContext, "Informe o email", Toast.LENGTH_LONG).show();
        }
        else if(edtTextLoginSenha.text.toString().isEmpty()){
            Toast.makeText(applicationContext, "Informe a senha", Toast.LENGTH_LONG).show();
        }

    }
}
