package com.dicoding.lawmate.ui.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.lawmate.api.ApiConfig
import com.dicoding.lawmate.api.response.DataItem
import com.dicoding.lawmate.api.response.GetUserResponse
import com.dicoding.lawmate.api.response.ListConsultationResponse
import com.dicoding.lawmate.api.response.Msg

class ChatViewModel: ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Belum ada konsultasi"
    }
    val text: LiveData<String> = _text


    private val _consultation = MutableLiveData<ListConsultationResponse>()
    val consultation:LiveData<ListConsultationResponse> = _consultation

    private val _user = MutableLiveData<GetUserResponse>()
    val user:LiveData<GetUserResponse> = _user

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


    suspend fun getConsultation(token:String){
        _isLoading.value = true
        val apiService = ApiConfig.getApiService(token)
        val call = apiService.getConsultation()

        _consultation.value = call
        _isLoading.value = false
    }

    suspend fun getConsultationLawyer(token:String){
        _isLoading.value = true
        val apiService = ApiConfig.getApiService(token)
        val call = apiService.getConsultationLawyer()

        _consultation.value = call
        _isLoading.value = false
    }

    suspend fun getUserById(id:String, token: String){
        _isLoading.value = true
        val apiService = ApiConfig.getApiService(token)
        val call = apiService.getUserById(id)

        _user.value = call
        _isLoading.value = false
    }
}