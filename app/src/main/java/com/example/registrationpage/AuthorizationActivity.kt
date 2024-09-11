package com.example.registrationpage

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class AuthorizationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_authorization)
        val userLogin: EditText = findViewById(R.id.user_login_authorization)
        val userPassword: EditText = findViewById(R.id.user_password_authorization)

        val registrationLink: TextView = findViewById(R.id.registration_link)

        val authorizationButton: Button = findViewById(R.id.button_authorization)

        registrationLink.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)

            startActivity(intent)
        }

        authorizationButton.setOnClickListener{
            val login = userLogin.text.toString().trim()
            val password = userPassword.text.toString().trim()

            if (login == "" || password == "")
                Toast.makeText(this, "Не все поля заполнены", Toast.LENGTH_LONG).show()
            else {
                val database = DbHelper(this, null)
                val isAuthorization = database.getUser(login, password)

                if (isAuthorization) {
                    val intent = Intent(this, ItemsActivity::class.java)

                    Toast.makeText(this, "Пользователь $login успешно авторизован!", Toast.LENGTH_LONG).show()

                    userLogin.text.clear()
                    userPassword.text.clear()

                    startActivity(intent)
                }
                else
                    Toast.makeText(this, "Пользователя $login не существует.", Toast.LENGTH_LONG).show()
            }
        }
    }
}