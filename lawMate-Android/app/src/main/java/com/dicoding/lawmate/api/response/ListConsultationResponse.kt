package com.dicoding.lawmate.api.response

import com.google.gson.annotations.SerializedName

data class ListConsultationResponse(

	@field:SerializedName("msg")
	val msg: List<MsgItem> = emptyList()
)

data class MsgItem(

	@field:SerializedName("picture_URI")
	val pictureURI: String? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("lawyer_id")
	val lawyerId: Int? = null,

	@field:SerializedName("user_id")
	val userId: String? = null,

	@field:SerializedName("ekstrakteks")
	val ekstrakteks: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)
