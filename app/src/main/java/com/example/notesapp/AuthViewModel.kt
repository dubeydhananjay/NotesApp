package com.example.notesapp

import android.text.TextUtils
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapp.model.User
import com.example.notesapp.model.UserRequest
import com.example.notesapp.model.UserResponse
import com.example.notesapp.repository.UserRepository
import com.example.notesapp.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    val userResponseLiveData : LiveData<NetworkResult<UserResponse>>
        get() = userRepository.userRepositoryLiveData

    fun registerUser(userRequest: UserRequest) {
        viewModelScope.launch {
            userRepository.registerUser(userRequest)
        }
    }

    fun loginUser(userRequest: UserRequest) {
        viewModelScope.launch {
            userRepository.loginUser(userRequest)
        }
    }

    fun validateCredentials(username: String, email: String, password: String, isLogin: Boolean): Pair<Boolean, String> {
        var res = Pair(true, "")
        if(!isLogin && TextUtils.isEmpty(username) || TextUtils.isEmpty(password) || TextUtils.isEmpty(email)) {
            res = Pair(false, "Please provide the credentials")
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            res = Pair(false, "Please provide valid email")
        }
        else if(password.length <= 5) {
            res = Pair(false, "Password length should be greater than 5")
        }
        return res
    }
}