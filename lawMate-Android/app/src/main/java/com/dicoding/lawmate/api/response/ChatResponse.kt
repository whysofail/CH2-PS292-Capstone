package com.dicoding.lawmate.api.response

import com.google.gson.annotations.SerializedName

data class ChatResponse(

	@field:SerializedName("msg")
	val msg: String? = null,

	@field:SerializedName("tag")
	val tag: String? = null
)
