package com.example.project_2.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.project_2.IAuthSignIn
import com.example.project_2.service.NetworkResource
import com.example.project_2.service.RetrofitInstance
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authSignIn: IAuthSignIn
): ViewModel() {
    private val loginResult = MutableLiveData<Boolean>()
    val loginResultLiveData: LiveData<Boolean>
        get() = loginResult

    private val messageResult = MutableLiveData<String>()
    val messageResultLiveData: LiveData<String>
        get() = messageResult


    suspend fun loginUser(username: String, password: String) {
        when(val response = authSignIn.execute(username, password)) {
            is NetworkResource.Success -> {
                RetrofitInstance.accessToken = response.data.accessToken
                RetrofitInstance.refreshToken = response.data.refreshToken
                loginResult.postValue(true)
            }

            is NetworkResource.Error -> {
                loginResult.postValue(false)
                messageResult.postValue(response.message)
            }
        }
    }
}