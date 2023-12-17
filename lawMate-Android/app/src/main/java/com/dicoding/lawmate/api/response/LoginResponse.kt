package com.dicoding.lawmate.api.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("msg")
	val msg: String?,

	@field:SerializedName("user")
	val user: User?
)

data class User(

	@field:SerializedName("user_id")
	val userId: String?,

	@field:SerializedName("role_id")
	val roleId: String?,

	@field:SerializedName("last_name")
	val lastName: String?,

	@field:SerializedName("accessToken")
	val accessToken: String?,

	@field:SerializedName("first_name")
	val firstName: String?,

	@field:SerializedName("email")
	val email: String?
)
