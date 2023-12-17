package com.dicoding.lawmate.ui.authentication.registration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.lawmate.api.ApiConfig
import com.dicoding.lawmate.api.response.LoginResponse
import com.dicoding.lawmate.api.response.RegisterResponse

class RegistrationViewModel:ViewModel() {

    private val _registerResult = MutableLiveData<RegisterResponse>()
    val registerResult: LiveData<RegisterResponse> = _registerResult

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


    suspend fun registrasi(
        firstName:String,
        lastName:String,
        email:String,
        password:String,
        confirmPassword:String){
        _isLoading.value = true
        // Membuat panggilan API untuk login
        val apiService = ApiConfig.getApiService("")
        val resultLogin = apiService.register(firstName,lastName, email, password, confirmPassword)

        _registerResult.value = resultLogin
        _isLoading.value = false
    }

}