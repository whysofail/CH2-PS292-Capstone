package com.dicoding.lawmate.ui.consultation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.lawmate.api.ApiConfig
import com.dicoding.lawmate.api.response.CreateConsultationResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody

class ConsultationViewModel : ViewModel() {
    private val _consultation = MutableLiveData<CreateConsultationResponse>()
    val consultation: LiveData<CreateConsultationResponse> = _consultation

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    suspend fun sendConsultation(
        token: String,
        id: String,
        title: RequestBody,
        description: RequestBody,
        file: MultipartBody.Part
    ) {
        _isLoading.value = true
        // Membuat panggilan API untuk login
        val apiService = ApiConfig.getApiService(token)
        val consultation = apiService.createConsultation(id, title, description, file)

        _consultation.value = consultation
        _isLoading.value = false
    }

    suspend fun sendConsultationNoFile(
        token: String,
        id: String,
        title: RequestBody,
        description: RequestBody
    ) {
        _isLoading.value = true
        // Membuat panggilan API untuk login
        val apiService = ApiConfig.getApiService(token)
        val consultation = apiService.createConsultationNoFile(id, title, description)

        _consultation.value = consultation
        _isLoading.value = false
    }
}