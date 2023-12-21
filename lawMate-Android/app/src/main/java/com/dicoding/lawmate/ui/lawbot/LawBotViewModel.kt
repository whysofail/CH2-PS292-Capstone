package com.dicoding.lawmate.ui.lawbot

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.lawmate.api.ApiConfig
import com.dicoding.lawmate.api.response.ChatResponse
import com.dicoding.lawmate.api.response.DataItem

class LawBotViewModel : ViewModel() {

    private val _chatResult = MutableLiveData<ChatResponse>()
    val chatResult: LiveData<ChatResponse> = _chatResult

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _lawyers = MutableLiveData<List<DataItem>>()
    val lawyers:LiveData<List<DataItem>> get() = _lawyers


    suspend fun chat(token: String, input_user: String) {
        _isLoading.value = true
        // Membuat panggilan API untuk login
        val apiService = ApiConfig.getApiService(token)
        val chat = apiService.chat(input_user)

        _chatResult.value = chat
        _isLoading.value = false
    }

    suspend fun getLawyers(tag:String){
        _isLoading.value = true
        val apiService = ApiConfig.getApiService("")
        val call = apiService.searchLawyers(tag)

        _lawyers.value = call.data
        _isLoading.value = false
    }
}