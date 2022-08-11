package com.example.kotlindemo.mvvm.verify_otp

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlindemo.Api.UsesCaseResult
import com.example.kotlindemo.ApplicationClass
import com.example.kotlindemo.Repository.VerifyOtpRepository
import com.example.kotlindemo.model.loginModel.LoginResponse
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

open class VerifyOtpViewModel(private val verifyOtpRepository: VerifyOtpRepository): ViewModel(), CoroutineScope {

    val job = Job()
    override val coroutineContext: CoroutineContext = Dispatchers.Main + job

    val showloding = MutableLiveData<Boolean>()
    val loginData = MutableLiveData<LoginResponse>()
    val showerror = MutableLiveData<String>()


    private val context : Context = ApplicationClass.mInstance!!.applicationContext


    fun doVerifyOtp(map: HashMap<String?, String?>) {
        launch {
            val result = withContext(Dispatchers.IO) {
                verifyOtpRepository.verifyOtp(map)
            }
            showloding.value = false
            when(result){
                is UsesCaseResult.Success -> {
                    withContext(Dispatchers.Main){
                        loginData.value= result.data
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