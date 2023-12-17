package com.dicoding.lawmate.ui.lawbot

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.lawmate.api.ApiConfig
import com.dicoding.lawmate.api.response.ChatResponse

class LawBotViewModel : ViewModel() {

    private val _chatResult = MutableLiveData<ChatResponse>()
    val chatResult: LiveData<ChatResponse> = _chatResult

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


    suspend fun chat(token: String, input_user: String) {
        _isLoading.value = true
        // Membuat panggilan API untuk login
        val apiService = ApiConfig.getApiService(token)
        val chat = apiService.chat(input_user)

        _chatResult.value = chat
        _isLoading.value = false
    }
}