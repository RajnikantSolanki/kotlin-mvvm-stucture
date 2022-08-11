package com.example.kotlindemo.Fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.cmexpertise.beautyapp.roomdatabase.DatabaseViewModel
import com.example.kotlindemo.ApplicationClass
import com.example.kotlindemo.R
import com.example.kotlindemo.Utils.Utils
import com.example.kotlindemo.adapater.ListAdapter
import com.example.kotlindemo.databinding.FragmentListBinding
import com.example.kotlindemo.fragment.BaseFragment
import com.example.kotlindemo.model.storeListmodel.StoreResponse
import com.example.kotlindemo.mvvm.list.StoreListViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel



class ListFragment : BaseFragment() {

    private lateinit var binding: FragmentListBinding
    private lateinit var activity: AppCompatActivity
    private val storeListViewModel : StoreListViewModel by viewModel()
    private lateinit var adapter: ListAdapter

    //roomdatabase
    private lateinit var databaseViewModel: DatabaseViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list, container, false)
        var view: View = binding.root
        binding?.viewModel=storeListViewModel
        activity = ApplicationClass.mInstance!!.getactivity()!!
        initComponents(view)
        return view
    }

    override fun initComponents(rootView: View) {
       // roomDatabaseInit()
        //getList()
        //setUpUi()
        //setUpObserver()
    }

    @SuppressLint("UseRequireInsteadOfGet")
    private fun roomDatabaseInit() {
        databaseViewModel = this.getActivity()?.let {
            ViewModelProviders.of(it).get(DatabaseViewModel::class.java)
        }!!
    }

    private fun setUpObserver() {
        storeListViewModel.showloding.observe(activity, Observer {
            if(!it)
                binding.progressBar.visibility=View.GONE
        })
        storeListViewModel.storeData.observe(activity, Observer {data->
            for (data in data.responsedata!!.data!!) {
                databaseViewModel.insert(data)
            }
            data.responsedata!!.data?.let { retrieveList(it) }
        })
    }

    private fun setUpUi() {
        binding.rvList.layoutManager= LinearLayoutManager(activity)
        adapter= ListAdapter(arrayListOf())
        binding.rvList.adapter = adapter
    }

    private fun getList() {
        if (Utils.isOnline(activity, true)) {
            storeListViewModel.getList("19,6,5,4,3,2,1","17","0")
        }else{
            binding.progressBar.visibility=View.GONE
            databaseViewModel.allStoreList!!.observe(activity, Observer { words ->
                // Update the cached copy of the words in the adapter.
                words?.let {
                    retrieveList(it)
                }
            })
        }
    }

    private fun retrieveList(list: List<StoreResponse>) {
        adapter.apply {
            addUsers(list)
            notifyDataSetChanged()
        }
    }

}
