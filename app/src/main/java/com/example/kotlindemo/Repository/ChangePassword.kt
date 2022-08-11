package com.example.kotlindemo.Repository

import com.example.kotlindemo.Api.Apis
import com.example.kotlindemo.Api.UsesCaseResult
import com.example.kotlindemo.model.loginModel.LoginResponse


interface ChangePasswordRepository {
    suspend fun changePassword(map: HashMap<String?, String?>): UsesCaseResult<LoginResponse>
}

class ChangePasswordRepositoryImpl(private val apis: Apis): ChangePasswordRepository {
    override suspend fun changePassword(map: HashMap<String?, String?>): UsesCaseResult<LoginResponse> {
        val result = apis.doChangePassword(map).await()
        return try {
            UsesCaseResult.Success(result)
        }catch (e: Exception){
            UsesCaseResult.Failed(e)
        }
    }
}
