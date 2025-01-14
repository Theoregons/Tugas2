package com.example.tugas

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tugas.room.Book
import com.example.tugas.room.BookAdapter
import com.example.tugas.room.MainViewModel


class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var bookAdapter: BookAdapter
    private lateinit var mainViewModel: MainViewModel


    private val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            Toast.makeText(this, "Data berhasil di tambahkan", Toast.LENGTH_SHORT).show()
            mainViewModel.fetchBooks()
        }
    }

    private val startForResultEdit = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val updatedBook = result.data?.getSerializableExtra("updatedBook") as Book
            updatedBook.let{
                Toast.makeText(this, "Data berhasil di edit", Toast.LENGTH_SHORT).show()
                mainViewModel.fetchBooks()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerViewBooks)
        recyclerView.layoutManager = GridLayoutManager(this, 3)

        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        mainViewModel.allBook.observe(this) { books ->
            updateRecyclerView(books)
            recyclerView.adapter = bookAdapter
        }

        val buttonAddBook:Button = findViewById(R.id.fab_add)
        buttonAddBook.setOnClickListener{
            val intent = Intent(this, AddBookActivity::class.java)
            startForResult.launch(intent)
        }
    }

    private fun updateRecyclerView(books: List<Book>){
        bookAdapter = BookAdapter(books,
            onDeleteListener = {bookId -> showDeleteConfirmationDialog(bookId)},
            onEditListener = {book -> showEditConfirmationDialog(book)})
        recyclerView.adapter = bookAdapter
        bookAdapter.notifyDataSetChanged()
    }

    private fun showEditConfirmationDialog(book: Book){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Edit Book")
            .setMessage("you want to edit this data")
            .setPositiveButton("yes"){dialog, _ ->
                val intent = Intent(this, EditBookActivity::class.java)
                intent.putExtra("book", book)
                startForResultEdit.launch(intent)
                dialog.dismiss()
            }
            .setNegativeButton("no"){
                dialog, _ ->
                dialog.dismiss()
            }
        builder.create().show()
    }

    private fun showDeleteConfirmationDialog(bookId: Int){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("delete")
            .setMessage("you sure to delete data")
            .setPositiveButton("yes"){dialog, _ ->
                mainViewModel.deleteBookById(bookId)
                Toast.makeText(this, "Deleted Successfully", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
                mainViewModel.fetchBooks()
            }
            .setNegativeButton("no"){
                dialog, _ ->
                dialog.dismiss()
            }
        builder.create().show()
    }
}
