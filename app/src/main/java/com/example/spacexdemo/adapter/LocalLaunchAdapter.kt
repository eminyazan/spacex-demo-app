package com.example.spacexdemo.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.spacexdemo.R
import com.example.spacexdemo.constans.LAUNCH_ID_KEY
import com.example.spacexdemo.databinding.LocalLaunchRowBinding
import com.example.spacexdemo.model.LocalLaunch
import com.example.spacexdemo.view.LaunchDetailActivity

class LocalLaunchAdapter(private var localLaunches: ArrayList<LocalLaunch>):
    RecyclerView.Adapter<LocalLaunchAdapter.LocalLaunchViewHolder>(),LaunchClickListener {

    private lateinit var binding: LocalLaunchRowBinding

    inner class LocalLaunchViewHolder(var customView:LocalLaunchRowBinding):RecyclerView.ViewHolder(customView.root){

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
        val intent = Intent(view.context, LaunchDetailActivity::class.java)
        intent.putExtra(LAUNCH_ID_KEY, launchId)
        startActivity(view.context, intent, null)
    }

    override fun launchLongTapped(view: View, localLaunch: LocalLaunch): Boolean {
        val position = localLaunches.indexOf(localLaunch)

        notifyItemRemoved(position)

        // TODO: remove from list and go to list page
        return true

    }

    fun updateCookList(newCooksList: List<LocalLaunch>) {
        localLaunches.clear()
        localLaunches.addAll(newCooksList)
        notifyDataSetChanged()
    }
}