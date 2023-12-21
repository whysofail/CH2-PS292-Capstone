package com.dicoding.lawmate.api.response

import com.google.gson.annotations.SerializedName

data class CreateConsultationResponse(

	@field:SerializedName("msg")
	val msg: Msg? = null
)

data class Msg(

	@field:SerializedName("picture_URI")
	val pictureURI: String? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("lawyer_id")
	val lawyerId: String? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("ekstrakteks")
	val ekstrakteks: Any? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)
