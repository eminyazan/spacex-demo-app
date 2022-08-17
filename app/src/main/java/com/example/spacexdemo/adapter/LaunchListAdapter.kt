package com.example.spacexdemo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.spacexdemo.R
import com.example.spacexdemo.databinding.LaunchRowBinding
import com.example.spacexdemo.model.Launch


class LaunchListAdapter(
    private val launches: ArrayList<Launch>,
    private val listener: LaunchClickListener
) :
    RecyclerView.Adapter<LaunchListAdapter.LaunchViewHolder>() {

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
        holder.customView.listener = listener
    }

    override fun getItemCount(): Int {
        return launches.size
    }


    fun updateLaunchList(list: List<Launch>) {
        this.launches.clear()
        this.launches.addAll(list)
        notifyDataSetChanged()
    }

//
//    override fun launchTapped(view: View, launchId: String) {
//        val action = LaunchListFragmentDirections.goDetailFromList(launchIdKey = launchId)
//        Navigation.findNavController(view).navigate(action)
////        val action=LaunchListFragmentDirections.actionLaunchListFragmentToTestFragment()
////        Navigation.findNavController(view).navigate(action)
//    }
//
//
//    override fun launchLongTapped(view: View, launch: Launch): Boolean {
//
//        val position = launches.indexOf(launch)
//
//        val localLaunch = LocalLaunch(
//            name = launch.name,
//            detail = launch.details,
//            largeImage = launch.links.patch.large,
//            smallImage = launch.links.patch.small,
//            date = launch.dateUnix,
//            id = launch.id,
//            position = position,
//        )
//
//
////        if (res == true) {
////            Toast.makeText(
////                view.context,
////                "${localLaunch.name} archived!",
////                Toast.LENGTH_SHORT
////            )
////                .show()
////
////            println("Liste ---> ${viewModel.launchesList.value!!.size}")
////            viewModel.launchesList.value!!.removeAt(position)
////            println("Liste ---> ${viewModel.launchesList.value!!.size}")
////            updateLaunchList(viewModel.launchesList.value!!.toList())
////        }
//
//        return true
//
//    }
}