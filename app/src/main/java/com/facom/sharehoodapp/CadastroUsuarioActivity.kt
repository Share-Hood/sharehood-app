package com.facom.sharehoodapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import br.com.simplepass.loadingbutton.customViews.CircularProgressButton
import com.facom.sharehoodapp.model.User
import com.facom.sharehoodapp.service.UserService
import io.github.rybalkinsd.kohttp.ext.asString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CadastroUsuarioActivity : AppCompatActivity() {
    private lateinit var btnCadastroUsuarioCadastrar: CircularProgressButton
    private lateinit var edtTextCadastroUsuarioNome: EditText
    private lateinit var edtTextCadastroUsuarioEmail: EditText
    private lateinit var edtTextCadastroUsuarioCpf: EditText
    private lateinit var edtTextCadastroUsuarioCelular: EditText
    private lateinit var edtTextCadastroUsuarioSenha: EditText
    private lateinit var edtTextCadastroUsuarioConfirmarSenha: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_usuario)
        btnCadastroUsuarioCadastrar = findViewById(R.id.btnCadastroUsuarioCadastrar)
        edtTextCadastroUsuarioNome = findViewById(R.id.edtTextCadastroUsuarioNome)
        edtTextCadastroUsuarioEmail = findViewById(R.id.edtTextCadastroUsuarioEmail)
        edtTextCadastroUsuarioCpf = findViewById(R.id.edtTextCadastroUsuarioCpf)
        edtTextCadastroUsuarioCelular = findViewById(R.id.edtTextCadastroUsuarioCelular)
        edtTextCadastroUsuarioSenha = findViewById(R.id.edtTextCadastroUsuarioSenha)
        edtTextCadastroUsuarioConfirmarSenha = findViewById(R.id.edtTextCadastroUsuarioConfirmarSenha)
    }

    fun cancelar(view: View) {
        finish()
    }
    fun cadastrar (view: View){

        if(edtTextCadastroUsuarioNome.text.toString().isEmpty()){
            Toast.makeText(applicationContext, "Informe o nome", Toast.LENGTH_LONG).show();
            return;
        }
        else if(edtTextCadastroUsuarioEmail.text.toString().isEmpty()){
            Toast.makeText(applicationContext, "Informe o email", Toast.LENGTH_LONG).show();
            return;
        }
        else if(edtTextCadastroUsuarioSenha.text.toString().isEmpty()){
            Toast.makeText(applicationContext, "Informe a senha", Toast.LENGTH_LONG).show();
            return;
        }
        else if(edtTextCadastroUsuarioConfirmarSenha.text.toString().isEmpty()){
            Toast.makeText(applicationContext, "Confirme a senha", Toast.LENGTH_LONG).show();
            return;
        }
        else if(edtTextCadastroUsuarioSenha.text.toString() != edtTextCadastroUsuarioConfirmarSenha.text.toString()){
            Toast.makeText(applicationContext, "Senhas diferentes", Toast.LENGTH_LONG).show();
            return;
        }

        var usuario = User()
        usuario.name = edtTextCadastroUsuarioNome.text.toString()
        usuario.email = edtTextCadastroUsuarioEmail.text.toString()
        usuario.cpf = edtTextCadastroUsuarioCpf.text.toString()
        usuario.cellphone = edtTextCadastroUsuarioCelular.text.toString()
        usuario.password = edtTextCadastroUsuarioSenha.text.toString()

        GlobalScope.launch(context = Dispatchers.Main) {
            btnCadastroUsuarioCadastrar.startAnimation()
            try {
                val response = UserService.cadastroUsuario(usuario).await()
                if(response.isSuccessful) {
                    Toast.makeText(
                        applicationContext,
                        "Usuário salvo com sucesso!",
                        Toast.LENGTH_LONG
                    ).show()
                    finish()
                }
                else Toast.makeText(applicationContext, response.asString(), Toast.LENGTH_LONG).show()
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(applicationContext, "Algo inesperado aconteceu, tente novamente mais tarde", Toast.LENGTH_LONG).show()
            } finally {
                btnCadastroUsuarioCadastrar.revertAnimation {
                    btnCadastroUsuarioCadastrar.background = getDrawable(R.drawable.rounded_corners_primary)
                }
            }
        }

    }
}
