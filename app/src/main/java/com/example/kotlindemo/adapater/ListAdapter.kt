package com.example.kotlindemo.adapater

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kotlindemo.R
import com.example.kotlindemo.model.storeListmodel.StoreResponse


class ListAdapter(private val lists:ArrayList<StoreResponse>) : RecyclerView.Adapter<ListAdapter.DataViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder =
        DataViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_storelist, parent, false))

    override fun getItemCount(): Int = lists.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(lists[position])
    }

    fun addUsers(list: List<StoreResponse>) {
        this.lists.apply {
            clear()
            addAll(list)
        }

    }

    class DataViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

        private var img : ImageView = itemView.findViewById(R.id.img)

        fun bind(data: StoreResponse){
            itemView.apply {
                Glide.with(img.context)
                    .load(data.image)
                    .into(img)
            }
        }
    }
}