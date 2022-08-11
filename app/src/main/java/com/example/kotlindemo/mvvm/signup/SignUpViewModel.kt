package com.example.kotlindemo.mvvm.signup

import android.content.Context
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlindemo.Api.UsesCaseResult
import com.example.kotlindemo.ApplicationClass
import com.example.kotlindemo.Repository.SignupRepository
import com.example.kotlindemo.model.loginModel.LoginResponse
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

open class SignUpViewModel(private val signupRepository: SignupRepository): ViewModel(), CoroutineScope {

    val job = Job()
    override val coroutineContext: CoroutineContext = Dispatchers.Main + job

    val showloding = MutableLiveData<Boolean>()
    val loginData = MutableLiveData<LoginResponse>()
    val showerror = MutableLiveData<String>()
    val email = ObservableField<String>()
    val password = ObservableField<String>()

    private val context : Context = ApplicationClass.mInstance!!.applicationContext

    fun doSignUp(map: HashMap<String?, String?>) {

        launch {
            val result = withContext(Dispatchers.IO) {
                signupRepository.signUp(map)
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