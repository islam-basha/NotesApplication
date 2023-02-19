package com.example.notesapplication.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Insert
    suspend fun addNote(note:Note)

    @Query("SELECT * FROM Note")
    fun getNotes(): Flow<List<Note>>

    @Delete
    suspend fun delete(note : Note)

}