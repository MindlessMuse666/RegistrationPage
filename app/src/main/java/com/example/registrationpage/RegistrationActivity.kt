package com.example.registrationpage

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.registrationpage.databinding.ActivityRegistrationBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class RegistrationActivity : AppCompatActivity() {
    private lateinit var _binding: ActivityRegistrationBinding
    private lateinit var _auth: FirebaseAuth
    private lateinit var _database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = ActivityRegistrationBinding.inflate(layoutInflater)
        _auth = FirebaseAuth.getInstance()
        _database = FirebaseDatabase.getInstance()
        setContentView(_binding.root)

        _binding.authorizationLink.setOnClickListener {
            startActivity(Intent(this, AuthorizationActivity::class.java))
        }

        _binding.buttonRegistration.setOnClickListener {
            registrationUser()
        }
    }

    private fun registrationUser() {
        val userLogin: String = _binding.userLogin.text.toString().trim()
        val userEmail: String = _binding.userEmail.text.toString().trim()
        val userPassword: String = _binding.userPassword.text.toString().trim()

        if (userEmail.isEmpty() or !Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
            Toast.makeText(this, "Введите корректный email", Toast.LENGTH_SHORT).show()
            return
        }
        if (userPassword.isEmpty()) {
            Toast.makeText(this, "Введите пароль", Toast.LENGTH_SHORT).show()
            return
        }
        if (userLogin.isEmpty()) {
            Toast.makeText(this, "Введите логин", Toast.LENGTH_SHORT).show()
            return
        }

        _auth.createUserWithEmailAndPassword(userEmail, userPassword)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = task.result?.user
                    val userId: String? = user?.uid

                    // Создание пользователя в БД
                    val newUser = User(userId, userLogin, userEmail, userPassword)
                    _database.getReference(GlobalConstants.USERS)
                        .child(userId!!)
                        .setValue(newUser)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(this, "Регистрация успешна", Toast.LENGTH_SHORT)
                                    .show()

                                startActivity(Intent(this, MainActivity::class.java))
                                finish()
                            } else {
                                Toast.makeText(this, "Ошибка при регистрации", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }


                } else {
                    Toast.makeText(this, "Ошибка при регистрации в целом", Toast.LENGTH_SHORT).show()
                }
            }
    }
}