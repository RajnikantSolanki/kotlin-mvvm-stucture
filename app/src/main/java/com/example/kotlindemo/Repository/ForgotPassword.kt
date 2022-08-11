package com.example.kotlindemo.Repository

import com.example.kotlindemo.Api.Apis
import com.example.kotlindemo.Api.UsesCaseResult
import com.example.kotlindemo.model.loginModel.LoginResponse


interface ForgotPasswordRepository {
    suspend fun forgotPassword(map: HashMap<String?, String?>): UsesCaseResult<LoginResponse>
}

class ForgotPasswordRepositoryImpl(private val apis: Apis): ForgotPasswordRepository {
    override suspend fun forgotPassword(map: HashMap<String?, String?>): UsesCaseResult<LoginResponse> {
        val result = apis.doForgotPassword(map).await()
        return try {
            UsesCaseResult.Success(result)
        }catch (e: Exception){
            UsesCaseResult.Failed(e)
        }
    }
}
