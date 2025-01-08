package com.example.tugas.room

import android.app.Application
import android.provider.ContactsContract
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel (application: Application) : AndroidViewModel(application){
    private  val bookDatabase = BookDatabase.getDatabase(application)
    private val bookDao = bookDatabase.BookDao()

    private val _allBooks = MutableLiveData<List<Book>>()
    val allBook: LiveData<List<Book>> = _allBooks

    init {
        fetchBook()
    }

    private fun  fetchBook(){
        viewModelScope.launch {
            try {
                val books = bookDao.getAllBooks()
                _allBooks.postValue(books)
            }catch (e: Exception){
                _allBooks.postValue(emptyList())
            }
        }
    }
}