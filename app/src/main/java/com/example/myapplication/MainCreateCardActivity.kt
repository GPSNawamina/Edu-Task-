package com.example.todo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_create_card.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainCreateCardActivity : AppCompatActivity() {
    private lateinit var database: myDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_card)
        database = Room.databaseBuilder(
            applicationContext, myDatabase::class.java, "To_Do"
        ).build()

        save_button.setOnClickListener {
            val title = create_title.text.toString().trim()
            val priority = create_priority.text.toString().trim()

            if (title.isNotEmpty() && priority.isNotEmpty()) {
                GlobalScope.launch {
                    database.dao().insertTask(Entity(0, title, priority))
                }

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Title and Priority cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
