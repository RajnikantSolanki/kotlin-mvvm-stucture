package com.example.kotlindemo.Fragment

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.kotlindemo.ApplicationClass
import com.example.kotlindemo.R
import com.example.kotlindemo.Utils.*
import com.example.kotlindemo.databinding.FragmentChangePasswordBinding
import com.example.kotlindemo.fragment.BaseFragment
import com.example.kotlindemo.mvvm.change_password.ChangePasswordViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChangePasswordFragment : BaseFragment() {

    private lateinit var binding: FragmentChangePasswordBinding
    private lateinit var activity: AppCompatActivity
    private val viewModel : ChangePasswordViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
           }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_change_password, container, false)
        var view: View = binding.root
        binding?.viewModel = viewModel
        activity = ApplicationClass.mInstance!!.getactivity()!!
        initComponents(view)
        return view
    }

    override fun initComponents(rootView: View) {
        setupObserver()
        binding.btnSave.setOnClickListener {
            if(isValid().equals("")){
                doChangePassword()
            }else{
                Utils.snackBar(binding!!.view, isValid(), true, activity)
            }
        }
    }

    private fun doChangePassword(){
        val map : HashMap<String?, String?> = HashMap()
        map[param_email] = ""
        map[param_password] = ""
        map[param_login_type] = "0"

        if(Utils.isOnline(activity,false)){
            viewModel.doChangePassword(map)
        }
    }

    private fun setupObserver() {
        viewModel.loginData.observe(activity, Observer { data ->
            if (data.responsedata?.success == 1) {

            }
        })

        viewModel.showerror.observe(activity, Observer {
            Log.d("Error", "$it")
        })
        viewModel.showloding.observe(activity, Observer {
            if (it)
                Log.d("isLoading", "$it")
            else
                Log.d("isLoading", "$it")
        })
    }

    fun isValid():String{
        return if(TextUtils.isEmpty(binding?.etCurrentPassword?.text.toString())){
            getString(R.string.str_enter_current_password)
        }else if(TextUtils.isEmpty(binding?.etNewPassowrd?.text.toString())){
            getString(R.string.str_enter__new_password)
        }else if(TextUtils.isEmpty(binding?.etConfirmPassowrd?.text.toString())){
            getString(R.string.str_enter__confirm_password)
        }else if(!binding?.etNewPassowrd?.text.toString().equals(binding?.etConfirmPassowrd?.text.toString())){
            getString(R.string.str_password_not_matched)
        }else{
            ""
        }
    }


}