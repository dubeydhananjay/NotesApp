package com.example.notesapp.repository

import com.example.notesapp.api.UserApi
import com.example.notesapp.model.UserRequest
import javax.inject.Inject

class UserRepository @Inject constructor(private val userApi: UserApi) {

    suspend fun registerUser(userRequest: UserRequest) {
        val response = userApi.signup(userRequest)
    }

    suspend fun loginUser(userRequest: UserRequest) {
        val response = userApi.signin(userRequest)
    }
}