package com.example.kotlindemo.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlindemo.R
import com.example.kotlindemo.adapater.DrawerAdapter
import com.example.kotlindemo.models.drawermodel.Drawer

class MainActivityCustomDrawer : AppCompatActivity(), DrawerAdapter.DrawerItemClickInterface {

    private var recyclerview : RecyclerView ?= null
    private var drawer : DrawerLayout ?= null

    private lateinit var adapter: DrawerAdapter

    private var list: ArrayList<Drawer> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_custom_drawer)
        initComponents()

    }

    private fun initComponents() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

         drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
         recyclerview = findViewById<RecyclerView>(R.id.rvDrawerList)
        val toggle = ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer?.addDrawerListener(toggle)
        toggle.syncState()

        setDrawerList()
        setUpUi()
    }

    private fun setDrawerList() {
        for (i in 0..10){
            list?.add(Drawer())
        }
    }

    override fun onBackPressed() {
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_main_drawer, menu)
        return true
    }

    private fun setUpUi() {
        recyclerview?.layoutManager= LinearLayoutManager(this)
        adapter= DrawerAdapter(list,this,this)
        recyclerview?.adapter = adapter
        adapter.setDrawerClick(this)
    }

    override fun onDrawerClick(model: Drawer) {
        Toast.makeText(this,"fff",Toast.LENGTH_SHORT).show()
        drawer?.closeDrawer(GravityCompat.START)
    }

}