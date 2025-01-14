package com.example.tugas.room

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tugas.R

class BookAdapter(private  val books:List<Book>) : RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookAdapter.BookViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.book_item, parent, false)
        return  BookViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookAdapter.BookViewHolder, position: Int ) {
        val book = books[position]
        holder.bind(book)
    }

    override fun getItemCount(): Int = books.size

    class BookViewHolder(private val view: android.view.View) :RecyclerView.ViewHolder(view){
        fun bind(book:Book){
            val titleTextView = view.findViewById<TextView>(R.id.textViewTitle)
            val contentTextView = view.findViewById<TextView>(R.id.textViewContent)
            val timestampTextView = view.findViewById<TextView>(R.id.textViewTimestamp)

            titleTextView.text = book.title
            contentTextView.text = book.content
            timestampTextView.text = book.timestamp
        }
    }
}