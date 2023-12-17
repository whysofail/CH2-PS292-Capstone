package com.dicoding.lawmate.api.response

import com.google.gson.annotations.SerializedName

data class LawyerResponse(

	@field:SerializedName("data")
	val data: List<DataItem> = emptyList(),

	@field:SerializedName("message")
	val message: String? = null
)

data class Role(

	@field:SerializedName("name")
	val name: String? = null
)

data class LawyerTagsItem(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("description")
	val description: String? = null
)

data class DataItem(

	@field:SerializedName("lawyerTags")
	val lawyerTags: List<LawyerTagsItem> = emptyList(),

	@field:SerializedName("role")
	val role: Role? = null,

	@field:SerializedName("last_name")
	val lastName: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("first_name")
	val firstName: String? = null,

	@field:SerializedName("email")
	val email: String? = null
)
