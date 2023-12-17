package com.dicoding.lawmate.api.response

import com.google.gson.annotations.SerializedName

data class GetUserResponse(

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("role_id")
	val roleId: Int? = null,

	@field:SerializedName("fee")
	val fee: Any? = null,

	@field:SerializedName("last_name")
	val lastName: String? = null,

	@field:SerializedName("profile_picture")
	val profilePicture: Any? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("first_name")
	val firstName: String? = null,

	@field:SerializedName("email")
	val email: String? = null
)
