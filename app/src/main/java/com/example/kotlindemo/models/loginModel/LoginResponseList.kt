package com.example.kotlindemo.model.loginModel

import com.example.kotlindemo.loginModel.LoginUserData
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by admin on 2/7/2017.
 */

class LoginResponseList {

    @SerializedName("success")
    @Expose
    var success: Int? = null
    @SerializedName("message")
    @Expose
    var message: String? = null
    @SerializedName("user_data")
    @Expose
    var userData: LoginUserData? = null


}
