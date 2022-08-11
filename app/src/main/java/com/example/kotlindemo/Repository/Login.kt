package com.example.kotlindemo.Repository

import com.example.kotlindemo.Api.Apis
import com.example.kotlindemo.Api.UsesCaseResult
import com.example.kotlindemo.model.loginModel.LoginResponse


interface LoginRepository {
    suspend fun login(map: HashMap<String?, String?>): UsesCaseResult<LoginResponse>
}

class LoginRepositoryImpl(private val apis: Apis): LoginRepository {
    override suspend fun login(map: HashMap<String?, String?>): UsesCaseResult<LoginResponse> {
        val result = apis.doLogin(map).await()
        return try {
            UsesCaseResult.Success(result)
        }catch (e: Exception){
            UsesCaseResult.Failed(e)
        }
    }
}
