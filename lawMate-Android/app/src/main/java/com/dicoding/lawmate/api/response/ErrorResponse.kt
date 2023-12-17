package com.dicoding.lawmate.api.response

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @field:SerializedName("msg")
    val msg: String? = null
)