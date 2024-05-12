package com.example.todo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.room.Room
import com.example.myapplication.R
import com.example.todo.database.MyDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainStartActivity : AppCompatActivity() {
    private lateinit var database: MyDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_start)
        database = Room.databaseBuilder(
            applicationContext, MyDatabase::class.java, "To_Do"
        ).build()

        GlobalScope.launch(Dispatchers.Main) {
            DataObject.listdata = withContext(Dispatchers.IO) {
                database.dao().getTasks() as MutableList<CardInfo>
            }
            navigateToMain()
        }
    }

    private fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
