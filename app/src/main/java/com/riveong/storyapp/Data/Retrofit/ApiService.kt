package com.riveong.storyapp.Data.Retrofit

import com.riveong.storyapp.ui.Activities.Auth.Login
import okhttp3.MultipartBody
import okhttp3.RequestBody

import retrofit2.Call
import retrofit2.http.*


interface ApiService {
    @POST("login")
    @FormUrlEncoded
    fun postLogin(
        @Field("email",encoded=true) email:String,
        @Field("password",encoded=true) password:String
    ): Call<LoginResponse>

    @POST("register")
    @FormUrlEncoded
    fun postRegister(
        @Field("email",encoded=true) email:String,
        @Field("password",encoded=true) password:String,
        @Field("name",encoded = true) name:String
    ): Call<LoginResponse>


    @GET("stories")
    fun getStories(

        //@Header("Authorization") token: String
    ): Call<ListStoryResponse>


    @Multipart
    @POST("stories")
    fun uploadImage(
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
    ): Call<CammieResponse>

}