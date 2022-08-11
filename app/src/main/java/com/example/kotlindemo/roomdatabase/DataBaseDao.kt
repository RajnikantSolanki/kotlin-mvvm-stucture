package com.cmexpertise.beautyapp.roomdatabase

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.kotlindemo.model.storeListmodel.StoreResponse

@Dao
interface DataBaseDao
{

    @Insert(onConflict=OnConflictStrategy.REPLACE)
     fun insert(storeResponse: StoreResponse)

    @Query("SELECT * from storelist")
    fun getList():LiveData<List<StoreResponse>>


    @Query(value = "select * from storelist")
    fun allUsers() : List<StoreResponse>

    @Query("DELETE FROM storelist WHERE id =:storeId")
     fun delete(storeId : Int)


}