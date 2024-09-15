package com.example.registrationpage

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.registrationpage.databinding.ActivityAuthorizationBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class AuthorizationActivity : AppCompatActivity() {
    private lateinit var _binding: ActivityAuthorizationBinding
    private lateinit var _auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = ActivityAuthorizationBinding.inflate(layoutInflater)
        _auth = Firebase.auth
        setContentView(_binding.root)

        _binding.buttonAuthorization.setOnClickListener {
            authorization()
        }

        _binding.registrationLink.setOnClickListener {
            startActivity(Intent(this, RegistrationActivity::class.java))
        }
    }

    private fun authorization() {
        val userEmail = _binding.userEmail.text.toString()
        val userPassword = _binding.userPassword.text.toString()

        _auth.signInWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(this, "Добро пожаловать, $userEmail!", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else
                Toast.makeText(this, "Логин не найден", Toast.LENGTH_SHORT).show()
        }
    }
}