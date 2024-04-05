package com.example.pablerto
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.view.isVisible

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button: Button = findViewById(R.id.btnabrirperfil)
        val error: TextView = findViewById(R.id.tverrorLogin)
        val name: EditText = findViewById(R.id.edittxtname)
        val password: EditText = findViewById(R.id.edittxtpassword)
        val button2:TextView=findViewById(R.id.textView)

        button.setOnClickListener {
            val name: EditText = findViewById(R.id.edittxtname)
            val password: EditText = findViewById(R.id.edittxtpassword)
            val nameText: String = name.text.toString()
            val passwordText: String = password.text.toString()

            if (nameText.isEmpty() || passwordText.isEmpty()) {
                error.isVisible = true
            } else {
                val nameText: String = name.text.toString()
                val passwordText: String = password.text.toString()

                val User: User = User(nameText, passwordText)
                listusers.listUsers.add(User)
                currentuser.currentUser=User
                val intent: Intent = Intent(this, cameraActivity::class.java)
                startActivity(intent)
            }
        }
        button2.isClickable=true
        button2.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}
private fun Intent.putExtra(s: String, User: User) {

}
