package com.example.todo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import androidx.room.Room.databaseBuilder
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity<myDatabase : Any> : AppCompatActivity() {
    private lateinit var database: myDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        database = databaseBuilder(
            applicationContext, myDatabase, "To_Do"
        ).build()

        add.setOnClickListener {
            val intent = Intent(/* packageContext = */ this, /* cls = */ CreateCard::class.java)
            startActivity(intent)
        }

        val deleteAll
        deleteAll.setOnClickListener {
            GlobalScope.launch {
                database.dao().deleteAll()
            }
            setRecycler()
        }

        setRecycler()
    }

    private fun setRecycler() {
        val tasks = DataObject.getAllData()
        val recycler_view
        recycler_view.adapter = Adapter(tasks)
        recycler_view.layoutManager = LinearLayoutManager(this)
    }
}
