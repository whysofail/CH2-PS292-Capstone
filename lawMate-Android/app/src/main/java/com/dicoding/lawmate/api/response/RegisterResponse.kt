package com.dicoding.lawmate.api.response

import com.google.gson.annotations.SerializedName

data class RegisterResponse(
	@field:SerializedName("msg")
	val msg: String
)
