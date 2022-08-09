package com.example.spacexdemo.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        loader = LoadingDialog(requireContext())

        registerViewModel()
        adapter= LaunchListAdapter(arrayListOf(),viewModel)

        viewModel.getAllLaunches()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_launch_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        swipeRefresh.setOnRefreshListener {
            viewModel.getAllLaunches()

            swipeRefresh.isRefreshing = false
        }

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
        // TODO: remove observe not works with lists it works only first
        viewModel.launchesList.observe(viewLifecycleOwner) {
            it?.let {
                println("Launch list fragment")
                if (it.isNotEmpty()) {
                    loader.cancel()
                    recyclerView.visibility = View.VISIBLE
                    adapter.updateLaunchList(it)
                }
            }
        }
    }





}