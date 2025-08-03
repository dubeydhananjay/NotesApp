package com.example.notesapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.notesapp.api.UserAPI
import com.example.notesapp.model.UserRequest
import com.example.notesapp.model.UserResponse
import com.example.notesapp.utils.NetworkResult

import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

class UserRepository @Inject constructor(private val userApi: UserAPI) {

    private val _userRepositoryLiveData = MutableLiveData<NetworkResult<UserResponse>>()
    val userRepositoryLiveData : LiveData<NetworkResult<UserResponse>>
        get() = _userRepositoryLiveData

    private fun handleResponse(response: Response<UserResponse>) {
        _userRepositoryLiveData.postValue(NetworkResult.Loading())
        if(response.isSuccessful && response.body() != null) {
            _userRepositoryLiveData.postValue(NetworkResult.Success<UserResponse>(response.body()!!))
        }
        else if(response.errorBody() != null) {
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            _userRepositoryLiveData.postValue(NetworkResult.Error<UserResponse>(null, errorObj.getString("message")))
        }
        else {
            _userRepositoryLiveData.postValue(NetworkResult.Error<UserResponse>(null, "Something went wrong"))
        }
    }

    suspend fun registerUser(userRequest: UserRequest) {
        val response = userApi.signup(userRequest)
        handleResponse(response)
    }

    suspend fun loginUser(userRequest: UserRequest) {
        val response = userApi.signin(userRequest)
        handleResponse(response)
    }
}