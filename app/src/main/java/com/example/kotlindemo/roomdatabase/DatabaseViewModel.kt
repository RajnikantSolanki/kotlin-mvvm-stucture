package com.cmexpertise.beautyapp.roomdatabase

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.kotlindemo.model.storeListmodel.StoreResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DatabaseViewModel(application: Application) :AndroidViewModel(application) {

    //storelist
    private var repository: DatabaseRepository? = null
    var allStoreList:LiveData<List<StoreResponse>> ?= null

    init {
        val databaseDao = AppRoomDataBase.getDatabase(application).databseDao()
        repository = DatabaseRepository(databaseDao)
        allStoreList=repository!!.storeList

    }

    fun insert(storeResponse: StoreResponse) = viewModelScope.launch(Dispatchers.IO) {
        repository!!.insert(storeResponse)
    }
}