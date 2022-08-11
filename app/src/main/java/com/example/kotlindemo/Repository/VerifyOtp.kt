package com.example.kotlindemo.Repository

import com.example.kotlindemo.Api.Apis
import com.example.kotlindemo.Api.UsesCaseResult
import com.example.kotlindemo.model.loginModel.LoginResponse


interface VerifyOtpRepository {
    suspend fun verifyOtp(map: HashMap<String?, String?>): UsesCaseResult<LoginResponse>
}

class VerifyOtpRepositoryImpl(private val apis: Apis): VerifyOtpRepository {
    override suspend fun verifyOtp(map: HashMap<String?, String?>): UsesCaseResult<LoginResponse> {
        val result = apis.doVerifyOtp(map).await()
        return try {
            UsesCaseResult.Success(result)
        }catch (e: Exception){
            UsesCaseResult.Failed(e)
        }
    }
}
