package com.example.kotlindemo.Fragment

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.kotlindemo.ApplicationClass
import com.example.kotlindemo.R
import com.example.kotlindemo.databinding.FragmentProfileBinding
import com.example.kotlindemo.fragment.BaseFragment
import com.github.dhaval2404.imagepicker.ImagePicker
import java.io.File


class ProfileFragment : BaseFragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var activity: AppCompatActivity

    private var mProfileFile: File? = null


    companion object {
        private const val PROFILE_IMAGE_REQ_CODE = 101
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        var view: View = binding.root
//        activity = ApplicationClass.mInstance!!.getactivity()!!
        initComponents(view)
        return view
    }

    override fun initComponents(rootView: View) {

        binding.ivImage.setOnClickListener {
            ImagePicker.with(this)
                .cropSquare()
                .setImageProviderInterceptor { imageProvider -> // Intercept ImageProvider
                    Log.d("ImagePicker", "Selected ImageProvider: "+imageProvider.name)
                }
                // Image resolution will be less than 512 x 512
                .maxResultSize(512, 512)
                .start(Companion.PROFILE_IMAGE_REQ_CODE)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
       /* if (resultCode == Activity.RESULT_OK) {
            //Log.e("TAG", "Path:${ImagePicker.getFilePath(data)}")
            // File object will not be null for RESULT_OK
            val file = ImagePicker.getFile(data)!!
            when (requestCode) {
                PROFILE_IMAGE_REQ_CODE -> {
                    mProfileFile = file
                    binding.ivImage.setLocalImage(file, true)
                }
            }
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(activity, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(activity, "Task Cancelled", Toast.LENGTH_SHORT).show()
        }*/

        if (resultCode == Activity.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
            val uri: Uri = data?.data!!
            // Use Uri object instead of File to avoid storage permissions
            binding.ivImage.setImageURI(uri)
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(activity, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(activity, "Task Cancelled", Toast.LENGTH_SHORT).show()
        }
    }




}
