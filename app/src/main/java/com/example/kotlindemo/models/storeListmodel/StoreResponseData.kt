package com.example.kotlindemo.model.storeListmodel

import com.example.kotlindemo.storeListmodel.StoreResponseList
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by admin on 2/7/2017.
 */

class StoreResponseData {
    @SerializedName("responsedata")
    @Expose
    var responsedata: StoreResponseList? = null


}
