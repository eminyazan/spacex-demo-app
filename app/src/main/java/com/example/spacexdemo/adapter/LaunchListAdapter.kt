package com.example.spacexdemo.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.spacexdemo.R
import com.example.spacexdemo.constans.LAUNCH_ID_KEY
import com.example.spacexdemo.databinding.LaunchRowBinding
import com.example.spacexdemo.model.Launch
import com.example.spacexdemo.model.LocalLaunch
import com.example.spacexdemo.view.LaunchListFragmentDirections
import com.example.spacexdemo.viewmodel.LaunchListViewModel
import kotlinx.android.synthetic.main.local_launch_row.view.*

class LaunchListAdapter(
    private val launches: ArrayList<Launch>,
    private val viewModel: LaunchListViewModel,
) :
    RecyclerView.Adapter<LaunchListAdapter.LaunchViewHolder>(), LaunchClickListener {

    private lateinit var binding: LaunchRowBinding

    inner class LaunchViewHolder(var customView: LaunchRowBinding) :
        RecyclerView.ViewHolder(customView.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LaunchViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = DataBindingUtil.inflate(inflater, R.layout.launch_row, parent, false)
        return LaunchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LaunchViewHolder, position: Int) {
        holder.customView.launch = launches[position]
        holder.customView.listener = this
    }

    override fun getItemCount(): Int {
        return launches.size
    }


    fun updateLaunchList(list: List<Launch>) {
        this.launches.clear()
        this.launches.addAll(list)
        notifyDataSetChanged()
    }


    override fun launchTapped(view: View, launchId: String) {
        val action=LaunchListFragmentDirections.actionLaunchListFragmentToLaunchDetailFragment(launchIdKey = launchId)
        Navigation.findNavController(view).navigate(action)
    }

    override fun launchLongTapped(view: View, launch: Launch): Boolean {

        val position = viewModel.launchesList.value?.indexOf(launch)

        val localLaunch = position?.let {
            LocalLaunch(
                name = launch.name,
                detail = launch.details,
                largeImage = launch.links.patch.large,
                smallImage = launch.links.patch.small,
                date = launch.dateUnix,
                id = launch.id,
                position = it,
            )
        }

        val res = localLaunch?.let {
            viewModel.insertLaunch(it)
        }

        if (res == true) {
            Toast.makeText(
                view.context,
                "${localLaunch.name} archived!",
                Toast.LENGTH_SHORT
            )
                .show()

            println("Liste ---> ${viewModel.launchesList.value!!.size}")
            viewModel.launchesList.value!!.removeAt(position)
            println("Liste ---> ${viewModel.launchesList.value!!.size}")
            updateLaunchList(viewModel.launchesList.value!!.toList())
        }

        return true

    }
}