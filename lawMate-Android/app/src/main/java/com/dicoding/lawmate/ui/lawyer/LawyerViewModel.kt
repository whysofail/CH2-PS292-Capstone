package com.dicoding.lawmate.ui.lawyer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LawyerViewModel: ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Lawyer Fragment"
    }
    val text: LiveData<String> = _text
}