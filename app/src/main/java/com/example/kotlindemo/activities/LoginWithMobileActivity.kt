package com.example.kotlindemo.activities


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.kotlindemo.R
import com.example.kotlindemo.Utils.*
import com.example.kotlindemo.databinding.ActivityLoginWithMobileBinding
import com.example.kotlindemo.mvvm.login.LoginViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginWithMobileActivity : AppCompatActivity() {

    private var binding: ActivityLoginWithMobileBinding? = null
    private val loginViewModel : LoginViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this@LoginWithMobileActivity, R.layout.activity_login_with_mobile)
        binding?.viewModel = loginViewModel
        initComponents()
    }

    private fun initComponents() {
        binding?.btnLogin?.setOnClickListener {
            val intent = Intent(this,VerifyOtpActivity::class.java)
            startActivity(intent)
 /*           if(isValid().equals("")){
                doLogin()
            }else{
                Utils.snackBar(binding!!.view, isValid(), true, this)
            }*/
        }
        setupObserver()
    }

    private fun doLogin(){
        val map : HashMap<String?, String?> = HashMap()
        map[param_email] = "rajanikant.cmexpertise@gmail.com"
        map[param_password] = "123456"
        map[param_login_type] = "0"
        map[param_device_id] = Utils.getDeviceID(this)
        map[param_device_type] = "a"
        map[param_device_token] = ""

        if(Utils.isOnline(this,false)){
            loginViewModel.doLogin(map)
        }
    }

    private fun setupObserver() {
        loginViewModel.loginData.observe(this, Observer { data ->
            if (data.responsedata?.success == 1) {

            }
        })

        loginViewModel.showerror.observe(this, Observer {
            Log.d("Error", "$it")
        })
        loginViewModel.showloding.observe(this, Observer {
            if (it)
                Log.d("isLoading", "$it")
            else
                Log.d("isLoading", "$it")
        })
    }

    fun isValid():String{
        return if(TextUtils.isEmpty(binding?.etMobile?.text.toString())){
            getString(R.string.str_enter_mobile)
        }else{
            ""
        }
    }
}