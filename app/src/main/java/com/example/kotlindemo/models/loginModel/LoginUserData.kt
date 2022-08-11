package com.example.kotlindemo.loginModel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by admin on 2/7/2017.
 */

class LoginUserData {
    @SerializedName("user_id")
    @Expose
    var id: String? = null
    @SerializedName("fname")
    @Expose
    var fname: String? = null
    @SerializedName("lname")
    @Expose
    var lname: String? = null
    @SerializedName("email")
    @Expose
    var email: String? = null
    @SerializedName("phone")
    @Expose
    var phone: String? = null
    @SerializedName("login_type")
    @Expose
    var loginType: String? = null

}
