package com.facom.sharehoodapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.TextureView
import android.view.View
import android.widget.Button
import android.widget.TextView

class PerfilUsuarioActivity : AppCompatActivity() {
    private lateinit var edtPerfilNome : TextureView
    private lateinit var edtPerfilEmail : TextView
    private lateinit var edtPerfilSenha : TextView
    private lateinit var btnPerfilEditar : Button
    private lateinit var btnPerfilCancelar : Button
    private lateinit var btnPerfilSair : Button
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil_usuario)
    }

    fun editar(view: View) {}

    fun goSair(view: View) {
        finish()
    }
    fun goCancelar(view: View) {
        finish()
    }
}
