package com.example.spacexdemo.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.spacexdemo.R
import com.example.spacexdemo.constans.LAUNCH_ID_KEY
import com.example.spacexdemo.databinding.LaunchRowBinding
import com.example.spacexdemo.model.Launch
import com.example.spacexdemo.view.LaunchListFragmentDirections

class LaunchListAdapter(private val launches: ArrayList<Launch>) :
    RecyclerView.Adapter<LaunchListAdapter.LaunchViewHolder>(), LaunchClickListener {

    class LaunchViewHolder(var customView: LaunchRowBinding) :
        RecyclerView.ViewHolder(customView.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LaunchViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding =
            DataBindingUtil.inflate<LaunchRowBinding>(inflater, R.layout.launch_row, parent, false)
        return LaunchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LaunchViewHolder, position: Int) {
        holder.customView.launch = launches[position]
        holder.customView.listener = this
    }

    override fun getItemCount(): Int {
        return launches.size
    }

    fun updateCookList(newCooksList: List<Launch>) {
        launches.clear()
        launches.addAll(newCooksList)
        notifyDataSetChanged()
    }

    override fun launchTapped(view: View, launchId: String) {
        val bundle = Bundle()
        bundle.putString(LAUNCH_ID_KEY, launchId)
        val action = LaunchListFragmentDirections.goToDetail(launchIdKey = launchId)
        Navigation.findNavController(view).navigate(action)
    }


}