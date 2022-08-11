package com.cmexpertise.beautyapp.roomdatabase

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.kotlindemo.model.storeListmodel.StoreResponse

class DatabaseRepository(private val dataBaseDao: DataBaseDao)
{

    val storeList:LiveData<List<StoreResponse>> = dataBaseDao.getList()

    @WorkerThread
     fun insert(storeResponse: StoreResponse){
        dataBaseDao.insert(storeResponse)
    }

}