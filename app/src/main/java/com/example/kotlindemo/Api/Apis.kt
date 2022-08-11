package com.example.kotlindemo.Api


import com.example.kotlindemo.Utils.*
import com.example.kotlindemo.model.loginModel.LoginResponse
import com.example.kotlindemo.model.storeListmodel.StoreResponseData
import kotlinx.coroutines.Deferred
import retrofit2.http.*

interface Apis {

    @FormUrlEncoded
    @POST(Constans_login)
    fun doLogin(@FieldMap  data: HashMap<String?, String?>): Deferred<LoginResponse>

    @FormUrlEncoded
    @POST(Constans_forgot_password)
    fun doForgotPassword(@FieldMap  data: HashMap<String?, String?>): Deferred<LoginResponse>

    @FormUrlEncoded
    @POST(Constans_signup)
    fun doSignUp(@FieldMap  data: HashMap<String?, String?>): Deferred<LoginResponse>

    @FormUrlEncoded
    @POST(Constans_verifyotp)
    fun doVerifyOtp(@FieldMap  data: HashMap<String?, String?>): Deferred<LoginResponse>

    @FormUrlEncoded
    @POST(Constans_change_password)
    fun doChangePassword(@FieldMap  data: HashMap<String?, String?>): Deferred<LoginResponse>


    @FormUrlEncoded
    @POST(Constans_StoreList)
    fun getList(
        @Field("category_id") category_id: String,
        @Field("location_id") location_id: String,
        @Field("offset") offset: String
    ): Deferred<StoreResponseData>


}