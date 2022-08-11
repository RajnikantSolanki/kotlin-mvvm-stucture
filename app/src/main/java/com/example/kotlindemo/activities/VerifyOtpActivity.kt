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
import com.example.kotlindemo.databinding.ActivityVerifyOtpBinding
import com.example.kotlindemo.mvvm.verify_otp.VerifyOtpViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class VerifyOtpActivity : AppCompatActivity() {

    private var binding : ActivityVerifyOtpBinding ?= null
    private val viewModel : VerifyOtpViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_verify_otp)
        binding?.viewModel = viewModel
        initComponents()
    }

    private fun initComponents() {
        setupObserver()

        binding?.btnVerifyOtp?.setOnClickListener {

/*            if(isValid().equals("")){
                doOtpVerify()
            }else{
                Utils.snackBar(binding!!.view, isValid(), true, this)
            }*/

            val intent = Intent(this,MainActivityDefaultDrawer::class.java)
            startActivity(intent)
        }
    }

    private fun doOtpVerify(){
        val map : HashMap<String?, String?> = HashMap()
        map[param_email] = "dsadas"
        map[param_password] = "dasdasda"
        map[param_login_type] = "0"
        map[param_device_id] = Utils.getDeviceID(this)
        map[param_device_type] = "a"
        map[param_device_token] = ""

        if(Utils.isOnline(this,false)){
            viewModel.doVerifyOtp(map)
        }
    }

    fun isValid():String{
        return if(TextUtils.isEmpty(binding?.otpView?.text.toString())){
            getString(R.string.str_enter_otp)
        }else{
            ""
        }
    }


    private fun setupObserver() {
        viewModel.loginData.observe(this, Observer { data ->
            if (data.responsedata?.success == 1) {

            }
        })

        viewModel.showerror.observe(this, Observer {
            Log.d("Error", "$it")
        })
        viewModel.showloding.observe(this, Observer {
            if (it)
                Log.d("isLoading", "$it")
            else
                Log.d("isLoading", "$it")
        })
    }
}