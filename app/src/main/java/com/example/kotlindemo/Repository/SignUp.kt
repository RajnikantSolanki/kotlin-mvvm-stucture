package com.example.kotlindemo.Repository

import com.example.kotlindemo.Api.Apis
import com.example.kotlindemo.Api.UsesCaseResult
import com.example.kotlindemo.model.loginModel.LoginResponse


interface SignupRepository {
    suspend fun signUp(map: HashMap<String?, String?>): UsesCaseResult<LoginResponse>
}

class SignupRepositoryImpl(private val apis: Apis): SignupRepository {
    override suspend fun signUp(map: HashMap<String?, String?>): UsesCaseResult<LoginResponse> {
        val result = apis.doSignUp(map).await()
        return try {
            UsesCaseResult.Success(result)
        }catch (e: Exception){
            UsesCaseResult.Failed(e)
        }
    }
}
