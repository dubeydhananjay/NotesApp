package com.example.notesapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapp.model.NotesRequest
import com.example.notesapp.repository.NotesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(private val notesRepository: NotesRepository): ViewModel() {

    val notesLiveData get() = notesRepository.notesLiveData
    val statusLiveData get() = notesRepository.statusLiveData

    fun createNode(notesRequest: NotesRequest) {
        viewModelScope.launch {
            notesRepository.createNote(notesRequest)
        }
    }

    fun getAllNotes() {
        viewModelScope.launch {
            notesRepository.getNotes()
        }
    }

    fun updateNote(id: String, notesRequest: NotesRequest){
        viewModelScope.launch {
            notesRepository.updateNote(id, notesRequest)
        }
    }

    fun deleteNote(id: String) {
        viewModelScope.launch {
            notesRepository.deleteNote(id)
        }
    }

}