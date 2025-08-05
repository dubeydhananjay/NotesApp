package com.example.notesapp.model

data class NotesResponse(
    val __v: Int,
    val _id: String,
    val createdAt: String,
    val desc: String,
    val title: String,
    val updatedAt: String,
    val userId: String
)