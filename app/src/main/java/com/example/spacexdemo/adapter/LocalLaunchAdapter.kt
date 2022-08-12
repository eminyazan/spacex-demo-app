package com.example.spacexdemo.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.spacexdemo.R
import com.example.spacexdemo.constans.LAUNCH_ID_KEY
import com.example.spacexdemo.databinding.LocalLaunchRowBinding
import com.example.spacexdemo.model.LocalLaunch
import com.example.spacexdemo.view.ArchiveFragmentDirections
import com.example.spacexdemo.view.LaunchDetailFragment
import com.example.spacexdemo.view.LaunchListFragmentDirections
import com.example.spacexdemo.viewmodel.ArchiveViewModel

class LocalLaunchAdapter(
    private var localLaunches: ArrayList<LocalLaunch>,
    private var viewModel: ArchiveViewModel
) :
    RecyclerView.Adapter<LocalLaunchAdapter.LocalLaunchViewHolder>(), LaunchClickListener {

    private lateinit var binding: LocalLaunchRowBinding

    inner class LocalLaunchViewHolder(var customView: LocalLaunchRowBinding) :
        RecyclerView.ViewHolder(customView.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocalLaunchViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = DataBindingUtil.inflate(inflater, R.layout.local_launch_row, parent, false)
        return LocalLaunchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LocalLaunchViewHolder, position: Int) {
        holder.customView.launch = localLaunches[position]
        holder.customView.listener = this
    }

    override fun getItemCount(): Int {
        return localLaunches.size
    }

    override fun launchTapped(view: View, launchId: String) {
        val action= ArchiveFragmentDirections.goDetailFromArchive(launchIdKey = launchId)
        Navigation.findNavController(view).navigate(action)
    }

    override fun launchLongTapped(view: View, localLaunch: LocalLaunch): Boolean {
        val position = viewModel.localLaunchesList.value?.indexOf(localLaunch)
        val res = viewModel.deleteLocalLaunch(localLaunch)
        if (res) {

            val list = viewModel.localLaunchesList.value
            if (position != null) {
                list?.removeAt(position)
            }
            if (list != null) {
                updateLocalLaunchList(list)
            }
            Toast.makeText(
                view.context,
                "${localLaunch.name} removed from archive!",
                Toast.LENGTH_SHORT,
            ).show()
            if (position != null) {
                notifyItemRemoved(position)
            }
        }

        return true
    }

    fun updateLocalLaunchList(localLaunches: List<LocalLaunch>) {
        this.localLaunches.clear()
        this.localLaunches.addAll(localLaunches)
        notifyDataSetChanged()
    }
}