package com.example.spacexdemo.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.spacexdemo.adapter.LaunchListAdapter
import com.example.spacexdemo.R
import com.example.spacexdemo.viewmodel.LaunchListViewModel
import kotlinx.android.synthetic.main.fragment_launch_list.*

class LaunchListFragment : Fragment(){

    private lateinit var viewModel: LaunchListViewModel
    private lateinit var loader: LoadingDialog
    private lateinit var adapter : LaunchListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_launch_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loader = LoadingDialog(requireContext())

        registerViewModel()
        adapter= LaunchListAdapter(arrayListOf(),viewModel)
        Toast.makeText(view.context,"List fragment created",Toast.LENGTH_SHORT).show()

        setupRecyclerView()

        swipeRefresh.setOnRefreshListener {
            viewModel.getAllLaunches()

            swipeRefresh.isRefreshing = false
        }

        viewModel.getAllLaunches()

        observeData()
    }


    private fun registerViewModel() {
        viewModel = ViewModelProvider(this)[LaunchListViewModel::class.java]
    }

    private fun setupRecyclerView() {
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
    }

    private fun observeData() {
        viewModel.loading.observe(viewLifecycleOwner) {
            it?.let {
                if (it) {
                    loader.show()
                }
            }
        }
        viewModel.error.observe(viewLifecycleOwner) {
            it?.let {
                if (!it) {
                    errorText.visibility = View.GONE
                    loader.cancel()
                }
            }
        }

        viewModel.launchesList.observe(viewLifecycleOwner) {
            it?.let {

                if (it.isNotEmpty()) {
                    println("Launch list fragment observe data not empty")
                    loader.cancel()
                    recyclerView.visibility = View.VISIBLE
                    adapter.updateLaunchList(it)
                }
            }
        }
    }



}