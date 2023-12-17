package com.dicoding.lawmate.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.lawmate.api.ApiConfig
import com.dicoding.lawmate.api.response.GetUserResponse

class ProfileViewModel:ViewModel() {
    private val _user = MutableLiveData<GetUserResponse>()
    val user:LiveData<GetUserResponse> = _user


    suspend fun getLawyers(token:String){
        val apiService = ApiConfig.getApiService(token)
        val userResponse = apiService.getUser()

        _user.value = userResponse
    }


}