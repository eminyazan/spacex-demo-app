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
import com.example.spacexdemo.repo.BaseRepo
import com.example.spacexdemo.repo.BaseViewModelFactory
import com.example.spacexdemo.service.BaseHTTPService
import com.example.spacexdemo.viewmodel.LaunchListViewModel
import kotlinx.android.synthetic.main.fragment_launch_list.*

class LaunchListFragment : Fragment() {

    private lateinit var viewModel: LaunchListViewModel

    private var adapter = LaunchListAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_launch_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val retrofitService = BaseHTTPService.getInstance()
        val mainRepository = BaseRepo(retrofitService)


        viewModel = ViewModelProvider(
            requireActivity(),
            BaseViewModelFactory(mainRepository)
        )[LaunchListViewModel::class.java]

        setupRecyclerView()


        viewModel.getAllLaunches()

        swipeRefresh.setOnRefreshListener {
            viewModel.getAllLaunches()

            swipeRefresh.isRefreshing = false
        }

        observeData()
    }

    private fun setupRecyclerView() {
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
    }

    private fun observeData() {
        println("observe data works")
        viewModel.loading.observe(viewLifecycleOwner) {
            it?.let {
                if (!it) progressBar.visibility = View.GONE
            }
        }
        viewModel.error.observe(viewLifecycleOwner) {
            it?.let {
                if (!it) errorText.visibility = View.GONE
            }
        }
        viewModel.launchesList.observe(viewLifecycleOwner) {
            it?.let {
                println("update ui")
                if (it.isNotEmpty()){
                    recyclerView.visibility = View.VISIBLE
                    adapter.updateCookList(it)
                }
            }
        }
    }


}