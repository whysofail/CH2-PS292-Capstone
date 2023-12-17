package com.dicoding.lawmate.ui.lawyer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.lawmate.api.ApiConfig
import com.dicoding.lawmate.api.response.DataItem

class LawyerViewModel: ViewModel() {

    private val _lawyers = MutableLiveData<List<DataItem>>()
    val lawyers:LiveData<List<DataItem>> get() = _lawyers


    suspend fun getLawyers(){
        val apiService = ApiConfig.getApiService("")
        val call = apiService.getLawyers()

        _lawyers.value = call.data
    }

}