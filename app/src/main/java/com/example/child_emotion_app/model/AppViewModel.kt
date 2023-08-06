package com.example.child_emotion_app.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AppViewModel private constructor() : ViewModel() {
    private val jwtTokenLiveData = MutableLiveData<String>()

    private val userIdLiveData = MutableLiveData<String>()
    private val userPwdLiveData = MutableLiveData<String>()

    fun setJwtToken(token: String) {
        jwtTokenLiveData.value = token
    }

    fun getJwtToken(): LiveData<String> {
        return jwtTokenLiveData
    }

    fun setUserId(id: String) {
        userIdLiveData.value = id
    }

    fun getUserId(): LiveData<String> {
        return userIdLiveData
    }

    fun setUserPwd(pwd: String) {
        userPwdLiveData.value = pwd
    }

    fun getUserPwd(): LiveData<String> {
        return userPwdLiveData
    }

    companion object {
        private var instance: AppViewModel? = null

        fun getInstance(): AppViewModel {
            if (instance == null) {
                instance = AppViewModel()
            }
            return instance!!
        }
    }
}