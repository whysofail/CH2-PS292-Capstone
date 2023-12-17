package com.dicoding.lawmate.ui.authentication.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.lawmate.api.ApiConfig
import com.dicoding.lawmate.api.response.LoginResponse

class LoginViewModel:ViewModel() {

    private val _loginResult = MutableLiveData<LoginResponse>()
    val loginResult: LiveData<LoginResponse> = _loginResult

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


    suspend fun login(email:String, password:String){
        _isLoading.value = true
        // Membuat panggilan API untuk login
        val apiService = ApiConfig.getApiService("")
        val resultLogin = apiService.login(email, password)

        _loginResult.value = resultLogin
        _isLoading.value = false
    }

}

