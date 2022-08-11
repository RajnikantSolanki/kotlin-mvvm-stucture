package com.example.kotlindemo.Repository


import com.example.kotlindemo.Api.Apis
import com.example.kotlindemo.Api.UsesCaseResult
import com.example.kotlindemo.model.storeListmodel.StoreResponseData


interface StoreListRepository {
    suspend fun storeList(email: String, password:String,logintype:String): UsesCaseResult<StoreResponseData>
}

class StoreListRepositoryImpl(private val apis: Apis): StoreListRepository {
    override suspend fun storeList(categoryId: String, loginId: String,offset:String): UsesCaseResult<StoreResponseData> {
        val result = apis.getList(categoryId, loginId,offset).await()
        return try {
            UsesCaseResult.Success(result)
        }catch (e: Exception){
            UsesCaseResult.Failed(e)
        }
    }
}
