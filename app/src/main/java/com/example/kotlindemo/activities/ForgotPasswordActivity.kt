package com.example.kotlindemo.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.kotlindemo.R
import com.example.kotlindemo.Utils.*
import com.example.kotlindemo.databinding.ActivityForgotPasswordBinding
import com.example.kotlindemo.mvvm.forgot_password.ForgotPasswordViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ForgotPasswordActivity : AppCompatActivity() {

    private var binding: ActivityForgotPasswordBinding? = null
    private val viewModel : ForgotPasswordViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this@ForgotPasswordActivity, R.layout.activity_forgot_password)
        binding?.viewModel = viewModel

        initComponents()
    }

    private fun initComponents() {
        setupObserver()
        binding?.btnSubmit?.setOnClickListener {

            if(isValid().equals("")){
                doForgotPassword()
            }else{
                Utils.snackBar(binding!!.view, isValid(), true, this)
            }

        }

        binding?.ivBack?.setOnClickListener {
           finish()
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

    private fun doForgotPassword(){
        val map : HashMap<String?, String?> = HashMap()
        map[param_email] = binding?.etEmail?.text.toString()

        if(Utils.isOnline(this,false)){
            viewModel.doForgotPassword(map)
        }
    }

    fun isValid():String{
        return if(TextUtils.isEmpty(binding?.etEmail?.text.toString())){
               getString(R.string.str_enter_email)
               }else if(!Utils.isValidEmail(binding?.etEmail?.text.toString())){
                   getString(R.string.str_valid_email_enter)
               }else{ "" }
    }
}