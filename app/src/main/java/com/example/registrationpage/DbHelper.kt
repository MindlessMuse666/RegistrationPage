package com.example.registrationpage

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DbHelper(val context: Context, val factory: SQLiteDatabase.CursorFactory?) : SQLiteOpenHelper(context, "DataBaseName", factory, 1) {

    // Создание Базы Данных
    override fun onCreate(database: SQLiteDatabase?) {
        val query = "CREATE TABLE users (id INT PRIMARY KEY, login TEXT, email TEXT, password TEXT)"

        database!!.execSQL(query)
    }

    // Удаление и пересоздание Базы Данных
    override fun onUpgrade(database: SQLiteDatabase?, p1: Int, p2: Int) {
        database!!.execSQL("DROP TABLE IF EXISTS users")
        onCreate(database)
    }

    // Регистрация нового пользователя в Базе Данных
    fun addUser(user: User) {
        val values = ContentValues()
        val database = this.writableDatabase

        values.put("login", user.login)
        values.put("email", user.email)
        values.put("password", user.password)

        database.insert("users", null, values)
        database.close()
    }

    fun getUser(login: String, password: String) : Boolean {
        val database = this.readableDatabase
        val result = database.rawQuery("SELECT * FROM users WHERE login = '$login' AND password = '$password'", null)

        return result.moveToFirst()
    }
}