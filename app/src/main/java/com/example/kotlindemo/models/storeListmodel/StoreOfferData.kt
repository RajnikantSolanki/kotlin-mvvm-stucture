package com.example.kotlindemo.model.storeListmodel

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by admin on 3/2/2017.
 */

class StoreOfferData  {

    @SerializedName("offer_id")
    @Expose
    var id: String? = null

    @SerializedName("title")
    @Expose
    var title: String? = null

    @SerializedName("offer_service_id")
    @Expose
    var offer_service_id: String? = null

    @SerializedName("description")
    @Expose
    var description: String? = null

    @SerializedName("actual_price")
    @Expose
    var actualPrice: String? = null

    @SerializedName("discount_price")
    @Expose
    var discountPrice: String? = null

    @SerializedName("discount_per")
    @Expose
    var discountPer: String? = null

    @SerializedName("image")
    @Expose
    var image: String? = null

    @SerializedName("start_date")
    @Expose
    var start_date: String? = null

    @SerializedName("end_date")
    @Expose
    var end_date: String? = null

}
