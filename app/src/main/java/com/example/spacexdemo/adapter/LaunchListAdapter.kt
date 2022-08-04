package com.example.spacexdemo.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.spacexdemo.R
import com.example.spacexdemo.constans.LAUNCH_ID_KEY
import com.example.spacexdemo.databinding.LaunchRowBinding
import com.example.spacexdemo.model.Launch
import com.example.spacexdemo.view.LaunchDetailActivity


class LaunchListAdapter(private val launches: ArrayList<Launch>) :
    RecyclerView.Adapter<LaunchListAdapter.LaunchViewHolder>(), LaunchClickListener {
    private lateinit var binding: LaunchRowBinding

    inner class LaunchViewHolder(var customView: LaunchRowBinding) :
        RecyclerView.ViewHolder(customView.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LaunchViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding =
            DataBindingUtil.inflate(inflater, R.layout.launch_row, parent, false)
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
        val intent = Intent(view.context, LaunchDetailActivity::class.java)
        intent.putExtra(LAUNCH_ID_KEY, launchId)
        startActivity(view.context, intent, null)
    }

    override fun launchLongTapped(view: View,launch: Launch): Boolean {
        val position =launches.indexOf(launch)
        launches.removeAt(position)
        notifyItemRemoved(position)
        Toast.makeText(view.context, "${launch.name} archived ", Toast.LENGTH_SHORT).show()
        return true
    }


}