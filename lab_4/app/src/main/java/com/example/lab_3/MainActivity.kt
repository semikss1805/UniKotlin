package com.example.lab_3

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var customAdapter: CustomAdapter
    private lateinit var database: AppDatabase

    private val itemsList = ArrayList<String>()

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        database = Room.databaseBuilder(this, AppDatabase::class.java, "database-name")
            .build()
        val dao = database.stringItemDao()
        GlobalScope.launch {
            dao.insert(StringItem(item = "item 1"))
            dao.insert(StringItem(item = "item 2"))

            val stringItem = dao.getAll()

            for (item in stringItem) {
                itemsList.add(item.item)
            }
        }

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        customAdapter = CustomAdapter(itemsList)
        customAdapter.notifyDataSetChanged()
        val layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = customAdapter
    }
}