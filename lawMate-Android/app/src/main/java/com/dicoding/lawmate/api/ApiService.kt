package com.dicoding.lawmate.api

import com.dicoding.lawmate.api.response.ChatResponse
import com.dicoding.lawmate.api.response.CreateConsultationResponse
import com.dicoding.lawmate.api.response.GetUserResponse
import com.dicoding.lawmate.api.response.LawyerResponse
import com.dicoding.lawmate.api.response.ListConsultationResponse
import com.dicoding.lawmate.api.response.LoginResponse
import com.dicoding.lawmate.api.response.RegisterResponse
import com.dicoding.lawmate.api.response.UpdateUserResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @FormUrlEncoded
    @POST("auth/login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    @FormUrlEncoded
    @POST("auth/register")
    suspend fun register(
        @Field("first_name") first_name: String,
        @Field("last_name") last_name: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("confirmationPassword") confirmationPassword: String
    ): RegisterResponse

    @GET("lawyer")
    suspend fun getLawyers(
    ): LawyerResponse

    @GET("lawyer/search")
    suspend fun searchLawyers(
        @Query("tag") tag: String
    ): LawyerResponse

    @GET("auth/who")
    suspend fun getUser(
    ): GetUserResponse

    @GET("auth/user/{id}")
    suspend fun getUserById(
        @Path("id") id: String
    ): GetUserResponse

    @FormUrlEncoded
    @POST("chat")
    suspend fun chat(
        @Field("user_input") user_input: String
    ): ChatResponse


    @Multipart
    @POST("auth/update")
    suspend fun editUser(
        @Part("first_name") first_name: RequestBody,
        @Part("last_name") last_name: RequestBody,
        @Part("email") email: RequestBody,
        @Part("password") password: RequestBody,
        @Part file: MultipartBody.Part,
    ): UpdateUserResponse

    @Multipart
    @POST("auth/update")
    suspend fun editUserNoFile(
        @Part("first_name") first_name: RequestBody,
        @Part("last_name") last_name: RequestBody,
        @Part("email") email: RequestBody,
        @Part("password") password: RequestBody,
    ): UpdateUserResponse


    @Multipart
    @POST("consultation")
    suspend fun createConsultation(
        @Query("lawyer_id") lawyer_id: String,
        @Part("title") title: RequestBody,
        @Part("description") description: RequestBody,
        @Part file: MultipartBody.Part
    ): CreateConsultationResponse

    @Multipart
    @POST("consultation")
    suspend fun createConsultationNoFile(
        @Query("lawyer_id") lawyer_id: String,
        @Part("title") title: RequestBody,
        @Part("description") description: RequestBody
    ): CreateConsultationResponse

    @GET("consultation/user")
    suspend fun getConsultation(
    ): ListConsultationResponse

    @GET("consultation/lawyer")
    suspend fun getConsultationLawyer(
    ): ListConsultationResponse
}