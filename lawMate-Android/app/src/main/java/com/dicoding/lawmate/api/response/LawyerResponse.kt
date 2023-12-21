package com.dicoding.lawmate.api.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class LawyerResponse(

	@field:SerializedName("msg")
	val msg: String? = null,

	@field:SerializedName("data")
	val data: List<DataItem> = emptyList()
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

	@field:SerializedName("fee")
	val fee: String? = null,

	@field:SerializedName("last_name")
	val lastName: String? = null,

	@field:SerializedName("profile_picture")
	val profilePicture: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("first_name")
	val firstName: String? = null,

	@field:SerializedName("email")
	val email: String? = null
)
