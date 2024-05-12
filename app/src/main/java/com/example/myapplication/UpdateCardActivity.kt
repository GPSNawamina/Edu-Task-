package com.example.todo

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.myapplication.R
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class UpdateCardActivity<MyDatabase> : AppCompatActivity() {
    private var database: MyDatabase
    private lateinit var createTitle: EditText
    private lateinit var createPriority: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_card)
        database = Room.databaseBuilder(
            applicationContext, MyDatabase::class.java, "To_Do"
        ).build()

        createTitle = findViewById(R.id.create_title)
        createPriority = findViewById(R.id.create_priority)

        val pos = intent.getIntExtra("id", -1)
        if (pos != -1) {
            val title = createTitle.text.toString()
            val priority = createPriority.text.toString()

            createTitle.setText(title)
            createPriority.setText(priority)

            delete_button.setOnClickListener {
                GlobalScope.launch {
                    database.dao().deleteTask(Entity(pos, title, priority))
                }
                myIntent()
            }

            update_button.setOnClickListener {
                GlobalScope.launch {
                    database.dao().updateTask(Entity(pos, title, priority))
                }
                myIntent()
            }
        }
    }

    private fun myIntent() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
