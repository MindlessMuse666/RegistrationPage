package com.example.registrationpage

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView

class ItemsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_items)

        val itemsList: RecyclerView = findViewById(R.id.itemsList)
        val items: ArrayList<Item> = arrayListOf()

        items.add(Item(1, "Суп_грибной", "Суп грибной", "Краткое описание грибного супа", "Описание грибного супа", 400))
        items.add(Item(2, "Салат \"Цезарь\"", "Салат \"Цезарь\"", "Краткое описание Салата \"Цезарь\"", "Описание Салата \"Цезарь\"", 250))
        items.add(Item(3, "Эспрессо", "Эспрессо", "Краткое описание эспрессо", "Описание Эспрессо", 180))
    }
}