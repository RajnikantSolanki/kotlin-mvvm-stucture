package com.example.kotlindemo.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.kotlindemo.R
import com.example.kotlindemo.Utils.*
import com.example.kotlindemo.databinding.ActivityRegisterBinding
import com.example.kotlindemo.mvvm.signup.SignUpViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterActivity : AppCompatActivity() {

    private var binding: ActivityRegisterBinding? = null
    private val viewModel : SignUpViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this@RegisterActivity, R.layout.activity_register)
        binding?.viewModel = viewModel
        initComponents()
    }

    private fun initComponents() {
        setupObserver()

        binding?.btnRegister?.setOnClickListener {
            if(isValid().equals("")){
                doRegister()
            }else{
                Utils.snackBar(binding!!.view, isValid(), true, this)
            }
        }

        binding?.ivBack?.setOnClickListener {
            finish()
        }
    }

    fun isValid():String{
        return if(TextUtils.isEmpty(binding?.etFirstName?.text.toString())){
            getString(R.string.str_enter_firstname)
        }else if(TextUtils.isEmpty(binding?.etLastName?.text.toString())){
            getString(R.string.str_enter_lastname)
        }else if(TextUtils.isEmpty(binding?.etMobileNumber?.text.toString())){
            getString(R.string.str_enter_mobile)
        }else if(TextUtils.isEmpty(binding?.etEmail?.text.toString())){
            getString(R.string.str_enter_email)
        }else if(!com.example.kotlindemo.Utils.Utils.isValidEmail(binding?.etEmail?.text.toString())){
            getString(R.string.str_valid_email_enter)
        }else if(TextUtils.isEmpty(binding?.etPassowrd?.text.toString())){
            getString(R.string.str_enter_password)
        }else if(TextUtils.isEmpty(binding?.etConfirmPassowrd?.text.toString())){
            getString(R.string.str_enter_confirm_password)
        }else if(!binding?.etPassowrd?.text.toString().equals(binding?.etConfirmPassowrd?.text.toString())){
            getString(R.string.str_password_not_matched)
        }else{
            ""
        }
    }

    private fun doRegister(){
        val map : HashMap<String?, String?> = HashMap()
        map[param_firstname] = binding?.etFirstName?.text.toString()
        map[param_lastname] = binding?.etLastName?.text.toString()
        map[param_phone] = binding?.etMobileNumber?.text.toString()
        map[param_password] = binding?.etPassowrd?.text.toString()
        map[param_email] = binding?.etEmail?.text.toString()
        map[param_login_type] = "0"

        if(Utils.isOnline(this,false)){
            viewModel.doSignUp(map)
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