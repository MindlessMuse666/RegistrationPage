package com.example.registrationpage

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.registrationpage.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity(), ValueEventListener {
    private lateinit var _binding: ActivityMainBinding

    private lateinit var _database: FirebaseDatabase
    private lateinit var _auth: FirebaseAuth

    private val _items: ArrayList<Item> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = ActivityMainBinding.inflate(layoutInflater)
        _auth = FirebaseAuth.getInstance()
        _database = FirebaseDatabase.getInstance()
        setContentView(_binding.root)

        if (_auth.currentUser == null) {
            startActivity(Intent(this, RegistrationActivity::class.java))
        }

        showMainActivity()
    }

    private fun showMainActivity() {
        _binding.logoutButton.setOnClickListener {
            startActivity(Intent(this, AuthorizationActivity::class.java))
            finish()
        }

        val user = _auth.currentUser
        val userId = user?.uid

        if (userId != null) {
            _database.getReference(GlobalConstants.USERS)
                .child(userId)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val userLogin = snapshot.child(GlobalConstants.USER_LOGIN).value.toString()
                    _binding.userHelloText.text = "Добро пожаловать, $userLogin!"
                    Log.d("MainActivity", "Логин: $userLogin")
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("MainActivity", "Ошибка: ${error.message}")
                    Toast.makeText(this@MainActivity, "Ошибка получения данных", Toast.LENGTH_SHORT).show()
                }
            })
        }

        _items.add(
            Item(
                1,
                "Суп_грибной",
                "Суп грибной",
                "Краткое описание грибного супа",
                "Описание грибного супа",
                400
            )
        )
        _items.add(
            Item(
                2,
                "Салат \"Цезарь\"",
                "Салат \"Цезарь\"",
                "Краткое описание Салата \"Цезарь\"",
                "Описание Салата \"Цезарь\"",
                250
            )
        )
        _items.add(
            Item(
                3,
                "Эспрессо",
                "Эспрессо",
                "Краткое описание эспрессо",
                "Описание Эспрессо",
                180
            )
        )

        _binding.itemsList.layoutManager = LinearLayoutManager(this)
        _binding.itemsList.adapter = ItemAdapter(_items, this)
    }

    override fun onDataChange(snapshot: DataSnapshot) {
        if (snapshot.exists()) {
            snapshot.getValue(User::class.java)
        }
    }

    override fun onCancelled(error: DatabaseError) {
        Log.e("MainActivity", "Ошибка: ${error.message}")
    }
}