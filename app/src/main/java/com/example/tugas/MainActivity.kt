package com.example.tugas

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tugas.room.BookAdapter
import com.example.tugas.room.MainViewModel


class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var bookAdapter: BookAdapter
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerViewBooks)
        recyclerView.layoutManager = GridLayoutManager(this, 3)

        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        mainViewModel.allBook.observe(this, Observer { books ->
            bookAdapter = BookAdapter(books)
            recyclerView.adapter = bookAdapter
        })

        val buttonAddBook:Button = findViewById(R.id.fab_add)
        buttonAddBook.setOnClickListener{
            val intent = Intent(this, AddBookActivity::class.java)
            startActivity(intent)
        }
    }
}
