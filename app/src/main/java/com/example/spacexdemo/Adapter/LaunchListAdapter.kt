package com.example.spacexdemo.Adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.spacexdemo.model.Launch

class LaunchListAdapter(private val launches:List<Launch>):
    RecyclerView.Adapter<LaunchListAdapter.LaunchViewHolder>() {

    class LaunchViewHolder(private val view:View):RecyclerView.ViewHolder(view){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LaunchViewHolder {
        TODO("Data binding will come")
    }

    override fun onBindViewHolder(holder: LaunchViewHolder, position: Int) {
        TODO("Data binding will come")
    }

    override fun getItemCount(): Int {
        return launches.size
    }
}