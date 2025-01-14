package com.example.tugas

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.service.quicksettings.Tile
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.tugas.room.Book
import com.example.tugas.room.MainViewModel

class EditBookActivity : AppCompatActivity() {
    private lateinit var  mainViewModel: MainViewModel
    private lateinit var  editTitle: EditText
    private lateinit var  editContent: EditText
    private lateinit var  saveButton: Button

    private var bookId: Int = 0
    private lateinit var originalBook: Book

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_edit_book2)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]

        val book = intent.getSerializableExtra("book") as? Book
        book?.let{
            originalBook = it
            bookId = it.id
        }
        editTitle = findViewById(R.id.editTitleField)
        editContent = findViewById(R.id.editContentField)
        saveButton = findViewById(R.id.buttonSave)

        editTitle.setText(originalBook.title)
        editContent.setText(originalBook.content)

        saveButton.setOnClickListener{
            val updatedTitle = editTitle.text.toString()
            val updatedContent = editContent.text.toString()

            if (updatedTitle.isNotEmpty() && updatedContent.isNotEmpty()){
                val updatedBook = Book(
                    id = originalBook.id,
                    title = updatedTitle,
                    content = updatedContent,
                    timestamp = originalBook.timestamp
                )

                mainViewModel.updateBook(updatedBook)

                val resultIntent = Intent().apply {
                    putExtra("updateBook", updatedBook)
                }

                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            } else {
                Toast.makeText(this, "Title and Content cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }
    }
}