package com.example.kotlindemo.activities

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.example.kotlindemo.R
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import android.widget.Toast
import androidx.core.app.ActivityCompat

class PermissionActivity : AppCompatActivity() {

    private lateinit var btnAllPermissions : Button
    private lateinit var btnCamera : Button
    private lateinit var btnStorage : Button
    private lateinit var btnCall : Button
    private lateinit var btnSMS : Button

    private var isPermissionDenied : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permission)
        initComponents()
        setOnClicklistener()
    }

    private fun initComponents() {
        btnAllPermissions = findViewById(R.id.btnAll)
        btnCamera = findViewById(R.id.btnCamera)
        btnStorage = findViewById(R.id.btnStorage)
        btnCall = findViewById(R.id.btnCall)
        btnSMS = findViewById(R.id.btnSMS)

       /* runBlocking {
            doWorld()
        }*/
    }

    private suspend fun doWorld() = coroutineScope {

       val job =  launch {
            repeat(10){ i ->
                Log.e("Coroutine","I'm sleeping $i ...")
                delay(500L)
            }
        }
        delay(2000L)
        Log.e("Coroutine","I'm tired of waiting!")

       // job.cancel()
        job.join()
        Log.e("Coroutine","Now I can quit.")

    }


    private fun setOnClicklistener() {
        btnAllPermissions.setOnClickListener {
            checkPermission()
        }

        btnCamera.setOnClickListener {
           checkOnlyOnePermission("android.permission.CAMERA")
        }

        btnStorage.setOnClickListener {
            checkOnlyOnePermission("android.permission.WRITE_EXTERNAL_STORAGE")
        }


        btnCall.setOnClickListener {
            checkOnlyOnePermission("android.permission.READ_CALL_LOG")
        }

        btnSMS.setOnClickListener {
            checkOnlyOnePermission("android.permission.READ_SMS")
        }
    }

    fun checkAllPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if ((ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) ==
                        PackageManager.PERMISSION_GRANTED) &&
                (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                        PackageManager.PERMISSION_GRANTED) &&
                (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) ==
                        PackageManager.PERMISSION_GRANTED)  &&
                (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) ==
                        PackageManager.PERMISSION_GRANTED) &&
                (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) ==
                        PackageManager.PERMISSION_GRANTED)) {
                Log.e("Permission", "granted")
            }else{
                requestForPermission.launch(
                    arrayOf(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.READ_CALL_LOG,
                        Manifest.permission.READ_SMS,
                    )
                )
            }
        }

    }

    protected fun checkPermission() {
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) +
                    ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) +
                    ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) +
                    ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) +
                    ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) +
                    ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS))
            != PackageManager.PERMISSION_GRANTED) {

            // Do something, when permissions not granted
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this, Manifest.permission.CAMERA
                )
                || ActivityCompat.shouldShowRequestPermissionRationale(
                    this, Manifest.permission.READ_CONTACTS
                )
                || ActivityCompat.shouldShowRequestPermissionRationale(
                    this, Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                || ActivityCompat.shouldShowRequestPermissionRationale(
                    this, Manifest.permission.READ_EXTERNAL_STORAGE
                )
                || ActivityCompat.shouldShowRequestPermissionRationale(
                    this, Manifest.permission.READ_CALL_LOG
                )
                || ActivityCompat.shouldShowRequestPermissionRationale(
                    this, Manifest.permission.READ_SMS
                )) {
                // If we should give explanation of requested permissions

                // Show an alert dialog here with request explanation
                val builder = AlertDialog.Builder(this)
                builder.setMessage(
                    "Camera, Read Contacts and Write External" +
                            " Storage permissions are required to do the task."
                )
                builder.setTitle("Please grant those permissions")
                builder.setPositiveButton(
                    "OK"
                ) { dialogInterface, i ->
                    requestForPermission.launch(
                        arrayOf(
                            Manifest.permission.CAMERA,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.READ_CALL_LOG,
                            Manifest.permission.READ_SMS,
                        )
                    )
                }
                builder.setNeutralButton("Cancel", null)
                val dialog = builder.create()
                dialog.show()
            } else {
                // Directly request for required permissions, without explanation
                requestForPermission.launch(
                    arrayOf(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.READ_CALL_LOG,
                        Manifest.permission.READ_SMS,
                    )
                )
            }
        } else {
            // Do something, when permissions are already granted
            Toast.makeText(this, "Permissions already granted", Toast.LENGTH_SHORT).show()
        }
    }


    val requestForPermission =  registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permission ->

            permission.entries.forEach {
                Log.e("Permission", "${it.key} = ${it.value}")

                if(!it.value){
                    isPermissionDenied = true
                }
            }

        if(isPermissionDenied){
            openSettingDialog()
        }

        }

    val requestForPermissionSingle =  registerForActivityResult(ActivityResultContracts.RequestPermission()) { permission ->
        if (permission){
            Log.e("Permission", "Granted single")
        }else{
            Log.e("Permission", "Granted denied")
            openSettingDialog()
        }
    }


    fun checkOnlyOnePermission(permission:String){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if ((ContextCompat.checkSelfPermission(this, permission) ==
                        PackageManager.PERMISSION_GRANTED)) {
                Log.e("Permission", "camera permission granted")
            }else if(shouldShowRequestPermissionRationale(permission)){
                openSettingDialog()
            }else{
                requestForPermissionSingle.launch(permission)
            }
        }
    }

    private fun openSettingDialog(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Androidly Alert")
        builder.setMessage("We have a message")
        builder.setCancelable(false)

        builder.setPositiveButton("Settings") { dialog, which ->
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            val uri: Uri = Uri.fromParts("package", packageName, null)
            intent.data = uri
            startActivity(intent)
        }

        builder.setNegativeButton("Not Now") { dialog, which ->
            dialog.dismiss()
        }
        builder.show()
    }


}
