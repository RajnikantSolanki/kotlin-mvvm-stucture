package com.example.kotlindemo.Api

import com.example.kotlindemo.ApplicationClass
import com.example.kotlindemo.Repository.*
import com.example.kotlindemo.Utils.CAT_API_BASE_URL
import com.example.kotlindemo.mvvm.change_password.ChangePasswordViewModel
import com.example.kotlindemo.mvvm.forgot_password.ForgotPasswordViewModel
import com.example.kotlindemo.mvvm.list.StoreListViewModel

import com.example.kotlindemo.mvvm.login.LoginViewModel
import com.example.kotlindemo.mvvm.signup.SignUpViewModel
import com.example.kotlindemo.mvvm.verify_otp.VerifyOtpViewModel

import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel


import org.koin.dsl.module


import retrofit2.CallAdapter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

sealed class UsesCaseResult<out T: Any> {
    class Success<out T: Any>(val data: T) : UsesCaseResult<T>()
    class Failed(val exception: Throwable) : UsesCaseResult<Nothing>()
}

val appmodules = module {
    single {
        WebSerivice<Apis>(
            okHttpClient = createOkhttpclient(),
            factory = RxJava2CallAdapterFactory.create(),
            baseurl = CAT_API_BASE_URL
        )
    }

    single { ApplicationClass.mInstance!!.applicationContext }

    factory<LoginRepository> { LoginRepositoryImpl(get()) }
    viewModel() { LoginViewModel(get()) }

    factory<StoreListRepository> { StoreListRepositoryImpl(get()) }
    viewModel { StoreListViewModel(get()) }

    factory<ForgotPasswordRepository> { ForgotPasswordRepositoryImpl(get()) }
    viewModel { ForgotPasswordViewModel(get()) }

    factory<SignupRepository> { SignupRepositoryImpl(get()) }
    viewModel { SignUpViewModel(get()) }

    factory<VerifyOtpRepository> { VerifyOtpRepositoryImpl(get()) }
    viewModel { VerifyOtpViewModel(get()) }

    factory<ChangePasswordRepository> { ChangePasswordRepositoryImpl(get()) }
    viewModel { ChangePasswordViewModel(get()) }

}

fun createOkhttpclient(): OkHttpClient {
    val interceptor = HttpLoggingInterceptor()
    interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
    val okHttpClient = OkHttpClient.Builder()
    okHttpClient.addInterceptor(interceptor)
    okHttpClient.connectTimeout(100, TimeUnit.SECONDS)
    okHttpClient.readTimeout(100, TimeUnit.SECONDS)
    okHttpClient.writeTimeout(100, TimeUnit.SECONDS)
    okHttpClient.retryOnConnectionFailure(true)

    return okHttpClient.build()
}

inline fun <reified T> WebSerivice(okHttpClient: OkHttpClient, factory: CallAdapter.Factory, baseurl: String): T{
    var retrofit = Retrofit.Builder()
        .baseUrl(baseurl)
        .addCallAdapterFactory(factory)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .client(okHttpClient)
        .build()
    return retrofit.create(T::class.java)
}