package com.example.kotlindemo.storeListmodel

import com.example.kotlindemo.model.storeListmodel.StoreResponse
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by admin on 2/7/2017.
 */

class StoreResponseList {

    @SerializedName("success")
    @Expose
    var success: String? = null
    @SerializedName("message")
    @Expose
    var message: String? = null
    @SerializedName("count")
    @Expose
    var count: Int? = null
    @SerializedName("data")
    @Expose
    var data: List<StoreResponse>? = null

}
