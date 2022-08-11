package com.example.kotlindemo.model.storeListmodel


import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


import java.util.ArrayList

/**
 * Created by admin on 2/7/2017.
 */

@Entity(tableName = "storelist")
class StoreResponse  {

    @PrimaryKey(autoGenerate = true)
    @SerializedName("store_id")
    @Expose
    var id: Int ?= null


    @SerializedName("store_category_id")
    @Expose
    var categoryId: String? = null

    @SerializedName("store_location_id")
    @Expose
    var locationId: String? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("email")
    @Expose
    var email: String? = null

    @SerializedName("phone")
    @Expose
    var phone: String? = null

    @SerializedName("address")
    @Expose
    var address: String? = null

    @SerializedName("latitude")
    @Expose
    var latitude: String? = null

    @SerializedName("longitude")
    @Expose
    var longitude: String? = null

    @SerializedName("open_day")
    @Expose
    var openDay: String? = null

    @SerializedName("close_day")
    @Expose
    var closeDay: String? = null

    @SerializedName("open_time")
    @Expose
    var openTime: String? = null

    @SerializedName("close_time")
    @Expose
    var closeTime: String? = null

    @SerializedName("discount")
    @Expose
    var discount: String? = null

    @SerializedName("image")
    @Expose
    var image: String? = null

    @SerializedName("facebook_url")
    @Expose
    var facebookUrl: String? = null

    @SerializedName("youtube_url")
    @Expose
    var youtubeUrl: String? = null

    @SerializedName("instagram_url")
    @Expose
    var instagramUrl: String? = null

    @SerializedName("pinterest_url")
    @Expose
    var pinterestUrl: String? = null

    @SerializedName("facility")
    @Expose
    var facility: String? = null

    @SerializedName("avg_rate")
    @Expose
    var avgRate: String? = null

    @SerializedName("status")
    @Expose
    var status: String? = null

    @SerializedName("dt_added")
    @Expose
    var dtAdded: String? = null

    @SerializedName("dt_updated")
    @Expose
    var dtUpdated: String? = null

    @Ignore
    @SerializedName("offer_data")
    @Expose
    var offerData: List<StoreOfferData>? = null

}
