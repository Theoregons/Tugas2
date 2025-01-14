package com.example.tugas.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")

data class Book(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val content: String,
    val timestamp: String,
)
