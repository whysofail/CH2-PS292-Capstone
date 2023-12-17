package com.dicoding.mystoryapp.preference

import android.content.Context

class UserPref(context: Context) {
    companion object{
        const val SP_NAME="user_pref"

        const val ACCESSTOKEN="token"
    }

    val preference = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)

    fun setUser(userModel: UserModel){
        val prefEditor = preference.edit()
        prefEditor.putString(ACCESSTOKEN, userModel.accessToken)
        prefEditor.apply()
    }

    fun getUser():UserModel{
        val userModel = UserModel()
        userModel.accessToken = preference.getString(ACCESSTOKEN, "")

        return userModel
    }

}