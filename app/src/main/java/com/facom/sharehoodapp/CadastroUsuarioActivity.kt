package com.facom.sharehoodapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class CadastroUsuarioActivity : AppCompatActivity() {
    private lateinit var btnCadastroUsuarioCadastrar: Button
    private lateinit var edtTextCadastroUsuarioNome: EditText
    private lateinit var edtTextCadastroUsuarioEmail: EditText
    private lateinit var edtTextCadastroUsuarioSenha: EditText
    private lateinit var edtTextCadastroUsuarioConfirmarSenha: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_usuario)
        btnCadastroUsuarioCadastrar = findViewById(R.id.btnCadastroUsuarioCadastrar)
        edtTextCadastroUsuarioNome = findViewById(R.id.edtTextCadastroUsuarioNome)
        edtTextCadastroUsuarioEmail = findViewById(R.id.edtTextCadastroUsuarioEmail)
        edtTextCadastroUsuarioSenha = findViewById(R.id.edtTextCadastroUsuarioSenha)
        edtTextCadastroUsuarioConfirmarSenha = findViewById(R.id.edtTextCadastroUsuarioConfirmarSenha)
    }

    fun cancelar(view: View) {
        finish()
    }
    fun cadastrar (view: View){

        if(edtTextCadastroUsuarioNome.text.toString().isEmpty()){
            Toast.makeText(applicationContext, "Informe o nome", Toast.LENGTH_LONG).show();
        }
        else if(edtTextCadastroUsuarioEmail.text.toString().isEmpty()){
            Toast.makeText(applicationContext, "Informe o email", Toast.LENGTH_LONG).show();
        }
        else if(edtTextCadastroUsuarioSenha.text.toString().isEmpty()){
            Toast.makeText(applicationContext, "Informe a senha", Toast.LENGTH_LONG).show();
        }
        else if(edtTextCadastroUsuarioConfirmarSenha.text.toString().isEmpty()){
            Toast.makeText(applicationContext, "Confirme a senha", Toast.LENGTH_LONG).show();
        }
        else if(edtTextCadastroUsuarioSenha != edtTextCadastroUsuarioConfirmarSenha){
            Toast.makeText(applicationContext, "Senhas diferentes", Toast.LENGTH_LONG).show();
            return;
        }
    }
}
