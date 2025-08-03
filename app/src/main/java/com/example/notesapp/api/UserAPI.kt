package com.example.notesapp.api

import com.example.notesapp.model.UserRequest
import com.example.notesapp.model.UserResponse
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Body

interface UserAPI {

    @POST("/users/signup")
    suspend fun signup(@Body userRequest: UserRequest) : Response<UserResponse>

    @POST("/users/signin")
    suspend fun signin(@Body userRequest: UserRequest) : Response<UserResponse>
}