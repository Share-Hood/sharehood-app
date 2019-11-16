package com.facom.sharehoodapp

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import br.com.simplepass.loadingbutton.customViews.CircularProgressButton
import com.facom.sharehoodapp.service.UserService
import io.github.rybalkinsd.kohttp.ext.asString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class EsqueciSenhaActivity : AppCompatActivity() {

    private lateinit var edtTextEsqueciSenhaEmail: EditText
    private lateinit var btnEsqueciSenhaRecuperar: CircularProgressButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_esqueci_senha)

        edtTextEsqueciSenhaEmail = findViewById(R.id.edtTextEsqueciSenhaEmail)
        btnEsqueciSenhaRecuperar = findViewById(R.id.btnEsqueciSenhaRecuperar)
    }

    fun cancelar(view: View) {
        finish()
    }

    fun recuperar(view: View) {
        btnEsqueciSenhaRecuperar.startAnimation()
        GlobalScope.launch(context = Dispatchers.Main) {
            try {
                val email = edtTextEsqueciSenhaEmail.text.toString()
                val response = UserService.esqueciMinhaSenha(email).await()
                if(response.isSuccessful) {
                    btnEsqueciSenhaRecuperar.doneLoadingAnimation(R.color.success, (getDrawable(R.drawable.ic_done_white_48dp) as BitmapDrawable).bitmap)
                    Toast.makeText(
                        applicationContext,
                        response.asString(),
                        Toast.LENGTH_LONG
                    ).show()
                    btnEsqueciSenhaRecuperar.revertAnimation {
                        finish()
                    }
                }
                else Toast.makeText(applicationContext, response.asString(), Toast.LENGTH_LONG).show()
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(applicationContext, "Algo inesperado aconteceu, tente novamente mais tarde", Toast.LENGTH_LONG).show()
            }
        }
    }
}
