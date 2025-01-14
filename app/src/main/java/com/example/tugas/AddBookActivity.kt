package com.example.tugas

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tugas.room.Book
import com.example.tugas.room.BookDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AddBookActivity : AppCompatActivity() {
    private lateinit var editTextTitle:EditText
    private lateinit var editTextContent:EditText
    private lateinit var buttonSave:Button
    private lateinit var bookDatabase: BookDatabase

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_book)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        editTextTitle = findViewById(R.id.editTextTitle)
        editTextContent = findViewById(R.id.editTextContent)
        buttonSave = findViewById(R.id.buttonSave)

        bookDatabase = BookDatabase.getDatabase(this)

        buttonSave.setOnClickListener{
            saveBook()
        }
    }

    private fun saveBook(){
        val title = editTextTitle.text.toString()
        val content = editTextContent.text.toString()
        val timestamp = SimpleDateFormat("yyy-MM-dd", Locale.getDefault()).format(Date())
        val book = Book(title = title, content = content, timestamp = timestamp)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                Log.d("test", title)
                Log.d("test", content)
                Log.d("test", timestamp)

                bookDatabase.BookDao().insert(book)
                runOnUiThread{
                    Toast.makeText(this@AddBookActivity, "Book save successfully!", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }catch (e: Exception){
                runOnUiThread{
                    Toast.makeText(this@AddBookActivity, "Failed to save note: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }

        if (title.isEmpty() || content.isEmpty()){
            Toast.makeText(this, "Title and Content cannot be empty", Toast.LENGTH_SHORT).show()
            return
        }
    }
}