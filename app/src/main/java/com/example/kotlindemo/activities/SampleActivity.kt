package com.example.kotlindemo.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.example.kotlindemo.R

class SampleActivity : AppCompatActivity() {

    private var btnEmail: Button? = null
    private var btnMobile: Button? = null

    val fruits = listOf("banana", "avocado", "apple", "kiwifruit")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sample)
        initComponets()
    }

    private fun initComponets() {
        btnEmail = findViewById(R.id.btnLoginWithEmail)
        btnMobile = findViewById(R.id.btnLoginWithMobile)


        btnEmail?.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        btnMobile?.setOnClickListener {
            val intent = Intent(this, LoginWithMobileActivity::class.java)
            startActivity(intent)
        }


        fruits.filter { it.endsWith("a") }
            .forEach {
                Log.e("TAG", "" + it)
            }

    }


}

