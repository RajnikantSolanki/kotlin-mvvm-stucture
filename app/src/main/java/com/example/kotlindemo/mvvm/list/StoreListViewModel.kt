package com.example.kotlindemo.mvvm.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlindemo.Api.UsesCaseResult
import com.example.kotlindemo.Repository.StoreListRepository
import com.example.kotlindemo.model.storeListmodel.StoreResponseData
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext


open class StoreListViewModel(private val storeListRepository: StoreListRepository): ViewModel(), CoroutineScope {
    val job = Job()
    override val coroutineContext: CoroutineContext = Dispatchers.Main + job

    val showloding = MutableLiveData<Boolean>()
    val storeData = MutableLiveData<StoreResponseData>()
    val showerror = MutableLiveData<String>()



    fun getList(categoryId: String?, locationId: String?,offset:String){

        launch {
            val result = withContext(Dispatchers.IO) {
                storeListRepository.storeList(categoryId!!, locationId!!,offset!!)
            }

            showloding.value = false
            when(result){
                is UsesCaseResult.Success -> {
                    withContext(Dispatchers.Main){
                        storeData.value= result.data
                    }
                }
                is UsesCaseResult.Failed -> {
                    showerror.value = result.exception.message
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}