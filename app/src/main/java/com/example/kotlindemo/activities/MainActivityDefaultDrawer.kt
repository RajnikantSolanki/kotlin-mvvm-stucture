package com.example.kotlindemo.activities

import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.navigation.NavigationView
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.core.view.GravityCompat
import android.view.Menu
import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import com.example.kotlindemo.ApplicationClass
import com.example.kotlindemo.Fragment.ChangePasswordFragment
import com.example.kotlindemo.Fragment.FirstFragment
import com.example.kotlindemo.Fragment.ProfileFragment
import com.example.kotlindemo.Fragment.SecondFragment
import com.example.kotlindemo.R



class MainActivityDefaultDrawer : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    var drawer : DrawerLayout ?= null

    private var changePasswordFragment: ChangePasswordFragment? = null
    private var firstFragment: FirstFragment? = null
    private var secondFragment: SecondFragment? = null
    private var profileFragment: ProfileFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_default_drawer)
        ApplicationClass.setmInstance(application as ApplicationClass)
        ApplicationClass.mInstance!!.setActity(this)
        initComponents()
    }

    private fun initComponents() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

         drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer?.addDrawerListener(toggle)
        toggle.syncState()
        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)
    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.activity_main_drawer, menu)
        return true
    }

   /* override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        return if (id == com.example.kotlindemo.R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)
    }*/

    override fun onBackPressed() {
        if (drawer!!.isDrawerOpen(GravityCompat.START)) {
            drawer?.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(@NonNull item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val id = item.itemId
        if (id == R.id.nav_camera) {
            changePasswordFragment = ChangePasswordFragment()
            openFragment(changePasswordFragment!!)
        } else if (id == R.id.nav_gallery) {
            firstFragment = FirstFragment()
            openFragment(firstFragment!!)
        } else if (id == R.id.nav_slideshow) {
            secondFragment = SecondFragment()
            openFragment(secondFragment!!)
        } else if (id == R.id.nav_manage) {
            profileFragment = ProfileFragment()
            openFragment(profileFragment!!)
        } else if (id == R.id.nav_share) {
        } else if (id == R.id.nav_send) {
        }
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    private fun openFragment(mFragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.activity_menubar_containers, mFragment, mFragment.javaClass.simpleName)
        transaction.commit()
        drawer?.closeDrawer(GravityCompat.START)
    }


}