package com.dicoding.lawmate.ui.authentication.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.lawmate.api.ApiConfig
import com.dicoding.lawmate.api.response.LoginResult

class LoginViewModel:ViewModel() {

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult


    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


    suspend fun login(email:String, password:String){

        _isLoading.value = false

        // Membuat panggilan API untuk login
        val apiService = ApiConfig.getApiService()
        val successLogin = apiService.login(email, password)

        _loginResult.value = successLogin.loginResult
        _isLoading.value = false
    }

}

