package com.example.kotlindemo.mvvm.forgot_password

import android.content.Context
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlindemo.Repository.LoginRepository
import com.example.kotlindemo.Api.UsesCaseResult
import com.example.kotlindemo.ApplicationClass
import com.example.kotlindemo.Repository.ForgotPasswordRepository
import com.example.kotlindemo.model.loginModel.LoginResponse
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext


open class ForgotPasswordViewModel(private val forgotPasswordRepository: ForgotPasswordRepository): ViewModel(), CoroutineScope {

    val job = Job()
    override val coroutineContext: CoroutineContext = Dispatchers.Main + job

    val showloding = MutableLiveData<Boolean>()
    val loginData = MutableLiveData<LoginResponse>()
    val showerror = MutableLiveData<String>()


    private val context : Context = ApplicationClass.mInstance!!.applicationContext


    fun doForgotPassword(map: HashMap<String?, String?>) {

        launch {
            val result = withContext(Dispatchers.IO) {
                forgotPasswordRepository.forgotPassword(map)
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