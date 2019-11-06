package com.facom.sharehoodapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class EsqueciSenhaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_esqueci_senha)
    }

    fun cancelar(view: View) {
        finish()
    }
}
