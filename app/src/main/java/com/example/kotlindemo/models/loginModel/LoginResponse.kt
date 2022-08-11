package com.example.kotlindemo.model.loginModel


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class LoginResponse {

    @SerializedName("responsedata")
    @Expose
    var responsedata: LoginResponseList? = null

}