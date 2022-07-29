package com.example.spacexdemo.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.spacexdemo.R
import com.example.spacexdemo.databinding.LaunchRowBinding
import com.example.spacexdemo.model.Launch

class LaunchListAdapter(private val launches: ArrayList<Launch>) :
    RecyclerView.Adapter<LaunchListAdapter.LaunchViewHolder>() {

    class LaunchViewHolder(val customView: LaunchRowBinding) :
        RecyclerView.ViewHolder(customView.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LaunchViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding =
            DataBindingUtil.inflate<LaunchRowBinding>(inflater, R.layout.launch_row, parent, false)
        return LaunchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LaunchViewHolder, position: Int) {
        holder.customView.apply {
            this.launch = launches[position]
        }
    }

    override fun getItemCount(): Int {
        return launches.size
    }

    fun updateCookList(newCooksList: List<Launch>) {
        launches.clear()
        launches.addAll(newCooksList)
        notifyDataSetChanged()
    }
}