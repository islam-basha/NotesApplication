package com.example.notesapplication.ui


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapplication.model.Note
import com.example.notesapplication.model.NotesDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ShowNotesVM @Inject constructor(private val db: NotesDatabase) : ViewModel() {

    var notes by mutableStateOf(emptyList<Note>())
        private set

    init {
        getNotes()
    }

    fun getNotes(){
        db.dao.getNotes().onEach { notesList ->
            notes = notesList
        }.launchIn(viewModelScope)
    }
}