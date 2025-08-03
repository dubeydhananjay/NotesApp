package com.example.notesapp.utils

import android.content.Context
import com.example.notesapp.utils.Constants.PREFS_TOKEN_FILE
import com.example.notesapp.utils.Constants.USER_TOKEN
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import androidx.core.content.edit

class TokenManager @Inject constructor(@ApplicationContext context: Context) {

    private var prefs = context.getSharedPreferences(PREFS_TOKEN_FILE, Context.MODE_PRIVATE)

    fun saveToken(token: String) =
        prefs.edit {
            putString(USER_TOKEN, token)
        }

    fun getToken(): String? = prefs.getString(USER_TOKEN, null)

    fun clearToken() =
        prefs.edit {
            remove(USER_TOKEN)
        }
}