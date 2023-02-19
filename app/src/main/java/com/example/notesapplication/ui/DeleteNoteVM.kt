package com.example.notesapplication.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapplication.model.Note
import com.example.notesapplication.model.NotesDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeleteNoteVM @Inject constructor(private val db: NotesDatabase) : ViewModel() {

    fun DeleteNote(note: Note){
        viewModelScope.launch {
            db.dao.delete(note)
        }
    }

}