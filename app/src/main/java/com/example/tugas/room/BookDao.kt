package com.example.tugas.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface BookDao {
    @Insert
    suspend fun insert(book: Book)

    @Update
    suspend fun update(book:Book)

    @Query("Select * From books")
    suspend fun getAllBooks(): List<Book>

    @Query("Select * From books Where id = :id")
    suspend fun getBookById(id:Int):Book?

    @Query("Delete From books Where id = :id")
    suspend fun deleteById(id:Int)
}