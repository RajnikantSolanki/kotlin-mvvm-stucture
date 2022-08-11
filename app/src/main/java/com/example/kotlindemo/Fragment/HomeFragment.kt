package com.example.kotlindemo.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentTransaction
import com.example.kotlindemo.ApplicationClass
import com.example.kotlindemo.R
import com.example.kotlindemo.databinding.FragmentHomeBinding
import com.example.kotlindemo.fragment.BaseFragment

import com.google.android.material.tabs.TabLayout

class HomeFragment : BaseFragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var activity: AppCompatActivity

    private lateinit var fragmentManger: androidx.fragment.app.FragmentManager


    private lateinit var storeListFragment: Fragment
    private lateinit var profileFragment: Fragment
    private lateinit var firstFragment: Fragment
    private lateinit var secondFragment: Fragment
    private lateinit var activeFragment: Fragment

    private val tabIcons = intArrayOf(
        R.drawable.menu_map_selector,
        R.drawable.menu_subscription_selector,
        R.drawable.menu_map_selector,
        R.drawable.menu_subscription_selector,
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        var view: View = binding.root
        activity = ApplicationClass.mInstance!!.getactivity()!!
        initComponents(view)
        return view

    }

    override fun initComponents(rootView: View) {
        setTabText()
        storeListFragment = ListFragment()
        profileFragment = ProfileFragment()
        firstFragment = FirstFragment()
        secondFragment = SecondFragment()
        activeFragment = storeListFragment

        fragmentManger = getActivity()!!.supportFragmentManager


        fragmentManger.beginTransaction().add(R.id.fameLayout, storeListFragment, "1").commit()
        fragmentManger.beginTransaction().add(R.id.fameLayout, profileFragment, "2").hide(profileFragment).commit()
        fragmentManger.beginTransaction().add(R.id.fameLayout, firstFragment, "3").hide(firstFragment).commit()
        fragmentManger.beginTransaction().add(R.id.fameLayout, secondFragment, "4").hide(secondFragment).commit()

        binding.fragmentMainTlHome.addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener {

            override fun onTabSelected(postion: TabLayout.Tab?) {

                if (postion!!.position == 0) {
                    var fargmentTransction: FragmentTransaction = fragmentManger.beginTransaction()
                    fargmentTransction.setCustomAnimations(
                        R.animator.slide_fragment_horizontal_right_in,
                        R.animator.slide_fragment_horizontal_left_out,
                        R.animator.slide_fragment_horizontal_left_in,
                        R.animator.slide_fragment_horizontal_right_out
                    )

                    fargmentTransction.hide(activeFragment).show(storeListFragment).commit()
                    activeFragment = storeListFragment
                } else if (postion.position == 1) {
                    var fargmentTransction: FragmentTransaction = fragmentManger.beginTransaction()
                    fargmentTransction.setCustomAnimations(
                        R.animator.slide_fragment_horizontal_right_in,
                        R.animator.slide_fragment_horizontal_left_out,
                        R.animator.slide_fragment_horizontal_left_in,
                        R.animator.slide_fragment_horizontal_right_out
                    )

                    fargmentTransction.hide(activeFragment).show(profileFragment).commit()
                    activeFragment = profileFragment
                }else if (postion.position == 2) {
                    var fargmentTransction: FragmentTransaction = fragmentManger.beginTransaction()
                    fargmentTransction.setCustomAnimations(
                        R.animator.slide_fragment_horizontal_right_in,
                        R.animator.slide_fragment_horizontal_left_out,
                        R.animator.slide_fragment_horizontal_left_in,
                        R.animator.slide_fragment_horizontal_right_out
                    )

                    fargmentTransction.hide(activeFragment).show(firstFragment).commit()
                    activeFragment = firstFragment
                }else if (postion.position == 3) {
                    var fargmentTransction: FragmentTransaction = fragmentManger.beginTransaction()
                    fargmentTransction.setCustomAnimations(
                        R.animator.slide_fragment_horizontal_right_in,
                        R.animator.slide_fragment_horizontal_left_out,
                        R.animator.slide_fragment_horizontal_left_in,
                        R.animator.slide_fragment_horizontal_right_out
                    )

                    fargmentTransction.hide(activeFragment).show(secondFragment).commit()
                    activeFragment = secondFragment
                }
            }
            override fun onTabReselected(p0: TabLayout.Tab?) {
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {
            }
        })

    }

    private fun setTabText() {
        binding.fragmentMainTlHome.addTab(binding.fragmentMainTlHome.newTab().setText("StoreList").setIcon(tabIcons[0]))
        binding.fragmentMainTlHome.addTab(binding.fragmentMainTlHome.newTab().setText("Profile").setIcon(tabIcons[1]))
        binding.fragmentMainTlHome.addTab(binding.fragmentMainTlHome.newTab().setText("Home").setIcon(tabIcons[2]))
        binding.fragmentMainTlHome.addTab(binding.fragmentMainTlHome.newTab().setText("User").setIcon(tabIcons[3]))
    }

}
