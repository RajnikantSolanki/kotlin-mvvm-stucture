package com.example.kotlindemo.activities

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Base64
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.kotlindemo.ApplicationClass
import com.example.kotlindemo.R
import com.example.kotlindemo.Utils.*
import com.example.kotlindemo.databinding.ActivityLoginBinding
import com.example.kotlindemo.mvvm.login.LoginViewModel
import com.facebook.*
import com.google.android.gms.auth.api.signin.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.google.android.gms.tasks.Task
import java.lang.Exception
import com.facebook.login.LoginResult
import com.facebook.login.LoginManager
import org.json.JSONException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*
import kotlin.collections.HashMap


class LoginActivity : AppCompatActivity() {

    private var activityLoginBinding: ActivityLoginBinding? = null
    private val loginViewModel : LoginViewModel by viewModel()

    private var mGoogleSignInClient : GoogleSignInClient ?= null
    private var callbackManager : CallbackManager ?= null


    private var googleBtn : Button ?= null
    private var facebookBtn : Button ?= null

    private var profilePic : String ?= null
    private var socilaEmail : String ?= null

    private var manager: CallbackManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityLoginBinding = DataBindingUtil.setContentView(this@LoginActivity, R.layout.activity_login)
        activityLoginBinding?.viewModel = loginViewModel
        initComponents()
    }

    private fun initComponents() {


        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

      //  callbackManager = CallbackManager.Factory.create();
        manager = CallbackManager.Factory.create()

        googleBtn = findViewById<Button>(R.id.btnGoogle)
        facebookBtn = findViewById<Button>(R.id.btnFacebook)

        setupObserver()

        activityLoginBinding?.btnLogin?.setOnClickListener {

            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            intent.putExtra("SelectOption", false)
            startActivity(intent)
            finish()
/*            if(isEmailAndPasswordValid().equals("")){
                doLogin()
            }else{
                Utils.snackBar(activityLoginBinding!!.view, isEmailAndPasswordValid(), true, this)
            }*/
        }
        googleBtn?.setOnClickListener {
            signIn()
        }
        facebookBtn?.setOnClickListener {
            facebookLogin()
        }

        activityLoginBinding?.btnRegister?.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        activityLoginBinding?.tvForgotPassword?.setOnClickListener {
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }

        hashkeys()
    }

    private fun setupObserver() {
        loginViewModel.loginData.observe(this, Observer { data ->
            if (data.responsedata?.success == 1) {
                ApplicationClass.mInstance?.savePreferenceDataBoolean(
                    resources.getString(R.string.PREF_LOGIN),
                    true
                )
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                intent.putExtra("SelectOption", false)
                startActivity(intent)
                finish()
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

    private fun facebookLogin() {
        LoginManager.getInstance()
            .logInWithReadPermissions(this, Arrays.asList("public_profile", "email"))
        LoginManager.getInstance().registerCallback(manager,
            object : FacebookCallback<LoginResult?> {
                override fun onSuccess(loginResult: LoginResult?) {
                    displayMessage(loginResult)
                }
                override fun onCancel() {
                    Toast.makeText(this@LoginActivity, "Login Cancel", Toast.LENGTH_LONG).show()
                }
                override fun onError(exception: FacebookException) {
                    Log.d("FbError", "" + exception.message)
                    Toast.makeText(this@LoginActivity, exception.message, Toast.LENGTH_LONG).show()
                }
            })

    }

    private fun displayMessage(loginResult: LoginResult?) {
        val profile = Profile.getCurrentProfile()
        if (profile != null && profile.getProfilePictureUri(200, 200).toString() != null) {
            profilePic = profile.getProfilePictureUri(200, 200).toString()
        }
        val graphRequest = GraphRequest.newMeRequest(
            loginResult?.accessToken
        ) { `object`, response ->
            try {
                val name = `object`.getString("name")
                if (`object`.has("email") && `object`.getString("email") != null) {
                    socilaEmail = `object`.getString("email")
                }

                Log.d("socilaEmail--->", "" + socilaEmail)
                var firstName = ""
                var lastName: String? = ""
                var fbId: String? = ""
                if (profile != null && profile.firstName != null && !profile.firstName.isEmpty()) {
                    firstName = profile.firstName
                } else {
                    if (name != null && !name.isEmpty()) {
                        val fullName = name.split(" ").toTypedArray()
                        firstName = fullName[0]
                    }
                }
                Log.d("socilaEmail--->", "" + firstName)
                if (profile != null && profile.lastName != null && !profile.lastName.isEmpty()) {
                    lastName = profile.lastName
                } else {
                    if (name != null && !name.isEmpty()) {
                        val fullName = name.split(" ").toTypedArray()
                        lastName = fullName[1]
                    }
                }
                fbId = if (profile != null && profile.id != null && !profile.id.isEmpty()) {
                    profile.id
                } else {
                    `object`.getString("id")
                }

            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
        val bundle = Bundle()
        bundle.putString("fields", "id,name,email,gender,picture,birthday")
        graphRequest.parameters = bundle
        graphRequest.executeAsync()
    }

    private fun signIn() {
        val signInIntent = mGoogleSignInClient!!.signInIntent
        resultLauncher.launch(signInIntent)
    }

    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            if (result.resultCode == Activity.RESULT_OK) {
                val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                handleSignInResult(task)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        manager!!.onActivityResult(requestCode, resultCode, data)
    }

    private fun handleSignInResult(signInResult: Task<GoogleSignInAccount>) {
        try {
            if (signInResult != null) {
                Log.d("GOOGLE", "SIGN IN" + signInResult.isSuccessful)
                if (signInResult.isSuccessful) {
                    val signInAccount = signInResult.result
                    if (signInAccount != null) {
                        var imageUri = ""
                        if(signInAccount.photoUrl != null){
                             imageUri = signInAccount.photoUrl.toString()
                        }
                        val fullName = signInAccount.displayName.split(" ").toTypedArray()
                        val firstName = fullName[0]
                        val lastName = fullName[1]
                        Log.e("Google", "firstName :$firstName")
                        Log.e("Google", "lastName :$lastName")
                        Log.e("Google", "email :" + signInAccount.email)
                        Log.e("Google", "id :" + signInAccount.id)

                        Toast.makeText(this, "" + getString(R.string.login_successfully), Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "" + getString(R.string.login_usuccessfully), Toast.LENGTH_SHORT).show()
            }
        }catch (e:Exception){
            Log.w("Google", "signInResult:failed code=" + e.printStackTrace())
        }
    }

    private fun doLogin(){
        val map : HashMap<String?, String?> = HashMap()
        map[param_email] = activityLoginBinding?.etEmail?.text.toString()
        map[param_password] = activityLoginBinding?.etPassowrd?.text.toString()
        map[param_login_type] = "0"
        map[param_device_id] = Utils.getDeviceID(this)
        map[param_device_type] = "a"
        map[param_device_token] = ""

        if(Utils.isOnline(this,false)){
            loginViewModel.doLogin(map)
        }
    }

    fun isEmailAndPasswordValid():String{
        return if(TextUtils.isEmpty(activityLoginBinding?.etEmail?.text.toString())){
            getString(R.string.str_enter_email)
        }else if(TextUtils.isEmpty(activityLoginBinding?.etEmail?.text.toString())){
            getString(R.string.str_enter_password)
        }else{
            ""
        }
    }

    fun hashkeys() {
        try {
            val info = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
            for (signature in info.signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                val sign = Base64.encodeToString(md.digest(), Base64.DEFAULT)
                Log.d("--- MY KEY HASH:", sign)
                println("sssssssssssssss$sign")
            }
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }
    }


}
