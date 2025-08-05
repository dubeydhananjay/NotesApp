package com.example.notesapp.api

import com.example.notesapp.model.NotesRequest
import com.example.notesapp.model.NotesResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface NotesAPI {

    @GET("/notes")
    suspend fun getNotes(): Response<List<NotesResponse>>

    @POST("/notes")
    suspend fun postNote(@Body notesRequest: NotesRequest): Response<NotesResponse>

    @PUT("/notes/{noteId}")
    suspend fun updateNote(@Path("noteId") noteId: String, @Body notesRequest: NotesRequest): Response<NotesResponse>

    @DELETE("/notes/{noteId}")
    suspend fun deleteNote(@Path("noteId") noteId: String): Response<NotesResponse>
}