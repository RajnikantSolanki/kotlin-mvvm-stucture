package com.example.kotlindemo.adapater

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlindemo.R
import com.example.kotlindemo.activities.MainActivityCustomDrawer
import com.example.kotlindemo.models.drawermodel.Drawer


class DrawerAdapter(private val lists: ArrayList<Drawer>, var drawerItemClickInterface: DrawerItemClickInterface,  var mainActivityCustomDrawer: MainActivityCustomDrawer
) : RecyclerView.Adapter<DrawerAdapter.DataViewHolder>(){



    interface DrawerItemClickInterface {
        fun onDrawerClick(model : Drawer)
    }

    fun setDrawerClick(drawerItemClickInterface : DrawerItemClickInterface) {
        this.drawerItemClickInterface = drawerItemClickInterface
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder =
        DataViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_drawer, parent, false))

    override fun getItemCount(): Int = lists.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(lists[position])
    }

    fun addUsers(list: List<Drawer>) {
        this.lists.apply {
            clear()
            addAll(list)
        }

    }

   inner class DataViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

      //  private var img : ImageView = itemView.findViewById(R.id.img)

        fun bind(data: Drawer){

            itemView.setOnClickListener {
                    drawerItemClickInterface.onDrawerClick(data)
                }
            }

    }
}