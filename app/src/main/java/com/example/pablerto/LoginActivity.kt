package com.example.pablerto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val nameEditText: EditText = findViewById(R.id.edittxtname)
        val passwordEditText: EditText = findViewById(R.id.edittxtpassword)
        val loginButton: Button = findViewById(R.id.btnabrirperfil2)
        val errorTextView: TextView = findViewById(R.id.tverrorLogin2)

        loginButton.setOnClickListener {

            val username = nameEditText.text.toString()
            val password2 = passwordEditText.text.toString()

            var userLoged: User? = iniciarSesion(username,password2)

            if (userLoged!= null) {
                // Si las credenciales son correctas, abrir la actividad de perfil
                currentuser.currentUser= userLoged
                val perfilIntent = Intent(this, cameraActivity::class.java)
                startActivity(perfilIntent)

            } else {
                errorTextView.visibility = View.VISIBLE
                // Mostrar un mensaje de error o notificar al usuario que las credenciales son incorrectas
            }
        }
    }
    fun iniciarSesion(username: String, password: String): User? {
        val usuarioEncontrado = listusers.listUsers.find { it.name  == username && it.password == password  }
        return usuarioEncontrado
    }
    private fun Intent.putExtra(s: String, User: User) {

    }
}
